package methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import hypixeltoolsmod.HypixelToolsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import storage.Cache;
import storage.FPruneInfo;
import storage.FriendLog;

public class FriendPruner {
	
	public static boolean threadInterrupted = false;
	public long timeOnLastStart = System.currentTimeMillis();
	public int friendsRemoved = 0;
	public int msDelay = 200;
	public String[] args;
	public ArrayList<FriendLog> list = new ArrayList<FriendLog>();
	public UUID playerUUID = Minecraft.getMinecraft().thePlayer.getUniqueID();
	//public UUID playerUUID = UUID.fromString("9f6851f3-e12f-471a-b9cb-256d15f4ad97");
	
	public FriendPruner( String[] s ) {
		args = s;
	}
	
	public void start(){
		if( HypixelToolsMod.API_KEY == null ) {
			PrintMessageHT.apiPrompt();
			return;
		}
		try {
			startRemovingFriends();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public void removeFriend(String name){
        System.out.println("Removing Friend " + name + " #" + friendsRemoved);
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/f remove " + name);
    }
	
	public void startRemovingFriends() throws IOException {
		JsonObject friendJson = getJSON( getFriendURL( APIUtil.stripDashes( playerUUID ) ) );
		JsonObject playerJson = getJSON( getPlayerURL( APIUtil.stripDashes( playerUUID ) ) );
		
		PrintMessageHT.showMsg( "Friend pruning is starting. This might take a while depending on the size of your friend list.");
		
		if( friendJson == null || playerJson == null ) { return; }
		
		final JsonArray array = friendJson.get("records").getAsJsonArray();
		
		new Thread( new Runnable() {

			@Override
			public void run() {
				
				threadInterrupted = false;
				
				System.out.println( "thread running" );
				
					for( JsonElement element: array ) {
						
						if( threadInterrupted ){
							return;
						}
						
						if( friendsRemoved == 0 || friendsRemoved % 100 != 0 || timeOnLastStart + 60000 < System.currentTimeMillis() ) {
							
						}
						
						String uuid = element.getAsJsonObject().get("uuidReceiver").getAsString();
						String[] playerInfo;
						String finalUUID;
						
						if( uuid.equals( APIUtil.stripDashes( playerUUID ) ) ) {
							playerInfo = getPlayerData( element.getAsJsonObject().get("uuidSender").getAsString() );
							finalUUID = element.getAsJsonObject().get("uuidSender").getAsString();
						}
						else {
							playerInfo = getPlayerData( element.getAsJsonObject().get("uuidReceiver").getAsString() );
							finalUUID = element.getAsJsonObject().get("uuidReceiver").getAsString();
						}
						
						boolean exception = false;
						for ( int i = 0; i < HypixelToolsMod.exceptions.size(); i++ ) {
							if( finalUUID.equals( HypixelToolsMod.exceptions.get(i) ) ) {
								exception = true;
							}
						}
						
						if( System.currentTimeMillis() - Float.parseFloat( playerInfo[0] ) > TimeConverter.getUnixTime( args[1], args[2] ) ) {
							if( args[0].equals("list") ) {
								list.add( new FriendLog( APIUtil.withDashes( finalUUID ), playerInfo[1], true, exception ) );
								System.out.println("Friends Checked: " + friendsRemoved + " Name: " + playerInfo[1] + " Removed: true Exception: " + exception + " Type: " + args[0] );
							}
							else if( args[0].equals("remove") ) {
								list.add( new FriendLog( APIUtil.withDashes( finalUUID ), playerInfo[1], true, exception ) );
								if( exception == false ) {
									removeFriend( playerInfo[1] );
								}
								System.out.println("Friends Checked: " + friendsRemoved + " Name: " + playerInfo[1] + " Removed: true Exception: " + exception + " Type: " + args[0] );
							}
						}
						else if( System.currentTimeMillis() - Float.parseFloat( playerInfo[0] ) <= TimeConverter.getUnixTime( args[1], args[2] ) ) {
							if( args[0].equals("list") ) {
								list.add( new FriendLog( APIUtil.withDashes( finalUUID ), playerInfo[1], false, exception ) );
								System.out.println("Friends Checked: " + friendsRemoved + " Name: " + playerInfo[1] + " Removed: false Exception: " + exception + " Type: " + args[0] );
							}
							else if( args[0].equals("remove") ) {
								list.add( new FriendLog( APIUtil.withDashes( finalUUID ), playerInfo[1], false, exception ) );
								System.out.println("Friends Checked: " + friendsRemoved + " Name: " + playerInfo[1] + " Removed: false Exception: " + exception + " Type: " + args[0] );
							}
						}
						
						try {
							Thread.sleep(msDelay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
					}
					
				}
					
					if( !threadInterrupted ) {
						System.out.println("generating logs");
						String file = "";
						FPruneInfo info = new FPruneInfo( args[0], args[2] + " " + args[1], TimeConverter.getUnixTime( args[1], args[2] ) );
						try {
							file = Cache.writeFriendPruningLog(list, args[0], args[2] + " " + args[1] );
							Cache.writeFriendLogObj(list, info);
						} catch( Exception e ) {
							e.printStackTrace();
						}
						String key = "HE5AwUR5z";
						String openFile = "nyWjFDGurz";
						
						String out = "[\"\",{\"text\":\"Friend Pruning Log File Generated [Click to Open]\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/dummyfileopen " + key + " " + openFile + " " + file + "\"}}]";
						
						
						IChatComponent chat_out = IChatComponent.Serializer.jsonToComponent(out);
						Minecraft.getMinecraft().thePlayer.addChatMessage(chat_out);
					}
				}
			
		}).start();
	}
	
	public String[] getPlayerData( String UUID ) {
		JsonObject json = null;
		try {
			json = getJSON( getPlayerURL( UUID ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( json == null ){
			return null;
		}
		
		String lastLogin = "";
		String name = "";
		lastLogin = json.get("player").getAsJsonObject().get("lastLogin").getAsString();
		name = json.get("player").getAsJsonObject().get("playername").getAsString();
		
		return new String[] { lastLogin, name };
	}
	
	public URL getFriendURL( String UUID ) {
		String url = "https://api.hypixel.net/friends";
		url += "?key=" + HypixelToolsMod.API_KEY + "&uuid=" + UUID;
		
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public URL getPlayerURL( String UUID ) {
		String url = "https://api.hypixel.net/player";
		url += "?key=" + HypixelToolsMod.API_KEY + "&uuid=" + UUID;
		
		try {
			return new URL( url );
		} catch( MalformedURLException e ) {
			e.printStackTrace();
		}
		return null;
	}

	public JsonObject getJSON(URL webLink) throws IOException{
		URL link = webLink;
		HttpsURLConnection con = (HttpsURLConnection)webLink.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String jsonText = readURL(rd);
		JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
		rd.close();
		if(this.friendsRemoved%100==0){
		    this.timeOnLastStart = System.currentTimeMillis();
		}
		this.friendsRemoved++;
		
		if(!json.get("success").getAsBoolean()) {
			if( json.get("throttle").getAsBoolean() ) {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return getJSON( link );
			}
			else {
				PrintMessageHT.showRedMessage("Your API key is not valid, or something went terribly wrong!");
				PrintMessageHT.showRedMessage( "Please run /api or /apiset [api key], and then try to run /fprune again." );
			    return null;
			}
		}
		
		    return json;
	}
	
	private String readURL(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int i;
        while ((i = reader.read()) != -1) {
            sb.append((char) i);
        }
        return sb.toString();
    }
	

}
