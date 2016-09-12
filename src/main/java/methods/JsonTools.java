package methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import hypixeltoolsmod.HypixelToolsMod;
import scala.util.parsing.json.JSON;

public class JsonTools {
	
	public static URL getFriendURL( String UUID ) {
		String url = "https://api.hypixel.net/friends";
		url += "?key=" + HypixelToolsMod.API_KEY + "&uuid=" + UUID;
		
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static URL getPlayerURL( String UUID ) {
		String url = "https://api.hypixel.net/player";
		url += "?key=" + HypixelToolsMod.API_KEY + "&uuid=" + UUID;
		
		try {
			return new URL( url );
		} catch( MalformedURLException e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNameFromUUID( String UUID ) {
		String url = "https://api.mojang.com/user/profiles/" + UUID + "/names";
		String longStr = "";
		try {
			longStr = getMojangAPI( new URL( url ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( longStr == null ){
			return null;
		}
		
		if( !longStr.contains( "," ) ) {
			longStr = longStr.replace( "[", "" );
			longStr = longStr.replace( "]", "" );
			longStr = longStr.replace( "{", "" );
			longStr = longStr.replace( "}", "" );
			longStr = longStr.replace( "\"", "" );
			String[] text = longStr.split( ":" );
			return text[1];
		}
		else if( longStr.contains( "," ) ) {
			longStr = longStr.replace( "[", "" );
			longStr = longStr.replace( "]", "" );
			longStr = longStr.replace( "{", "" );
			longStr = longStr.replace( "}", "" );
			longStr = longStr.replace( "\"", "" );
			longStr = longStr.replace( ",", ":" );
			String[] text = longStr.split( ":" );
			return text[ text.length - 3 ];
		}
		
		return null;
	}
 
	public static JsonObject getHypixelJSON(URL webLink) throws IOException{
		HttpsURLConnection con = (HttpsURLConnection)webLink.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/4,76");
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String jsonText = readURL(rd);
		JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
		rd.close();
		
		if(!json.get("success").getAsBoolean()) {
			PrintMessageHT.showRedMessage("Your API key is not valid, or something went terribly wrong!");
			PrintMessageHT.showRedMessage( "Please run /api or /apiset [api key], and then try again." );
		        return null;
		}
		
		    return json;
	}
	
	
	public static JsonObject getMojangJSON(URL webLink) throws IOException{
		HttpsURLConnection con = (HttpsURLConnection)webLink.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/4,76");
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String jsonText = readURL(rd);
		JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
		rd.close();
		
		if(json.has("error")) {
			PrintMessageHT.showRedMessage("Something went wrong with the mojang API!");
			return null;
		}
		
		    return json;
	}
	
	public static String getMojangAPI(URL webLink) throws IOException{
		HttpsURLConnection con = (HttpsURLConnection)webLink.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/4,76");
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String text = readURL(rd);
		rd.close();
		
		if( text.contains("error") && text.contains("errorMessage") && !text.contains("name") ) {
			PrintMessageHT.showRedMessage("Something went wrong with the mojang API!");
			return null;
		}
		
		    return text;
	}
	
	private static String readURL(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int i;
        while ((i = reader.read()) != -1) {
            sb.append((char) i);
        }
        return sb.toString();
    }

}
