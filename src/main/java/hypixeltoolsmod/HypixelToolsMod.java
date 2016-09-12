package hypixeltoolsmod;


import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import commands.APIKeySet;
import commands.AddAlias;
import commands.AddException;
import commands.AllChatShortcut;
import commands.AutoGG;
import commands.FileOpenDummyCommand;
import commands.FriendPruneCommand;
import commands.GetUUID;
import commands.GuildChatShortcut;
import commands.OnlineChecker;
import commands.PartyChatShortcut;
import commands.ReplyModCommand;
import commands.ShoutShortcut;
import commands.ToggleGuildChat;
import commands.TogglePartyChat;
import listeners.ClientChatReceievedEventHandler;
import listeners.HypixelChecker;
import listeners.TickHandler;
import methods.JsonTools;
import methods.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import replymodcompatability.Config;
import replymodcompatability.ConfigHandler;
import storage.APIReaderWriter;
import storage.AliasReaderWriter;
import storage.ExceptionsReaderWriter;

@Mod( modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, clientSideOnly = true )
public class HypixelToolsMod {
	
	//Old reply mod compatability
	public static ConfigHandler c = new ConfigHandler();
	public static TreeMap<String, Config> config;
	public static boolean replyModOn = false;
	public static String currentCommand, currentMessage;
	
	public static boolean hypixel = false;
	public static boolean checkUpdate = false;
	public static String API_KEY;
	public static ArrayList<String> aliases = new ArrayList<String>();
	public static ArrayList<String> exceptions = new ArrayList<String>();
	public static ArrayList<String> exceptionsName = new ArrayList<String>();
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static KeyBinding guild;
	public static KeyBinding party;
	public static KeyBinding all;
	
	@EventHandler
	public void preInit( FMLPreInitializationEvent event ) {
		
		File dir = new File ( mc.mcDataDir + "/HypixelToolsMod" );
		
		if( !dir.exists() )
			dir.mkdir();
		
		loadReplyMod();
		loadApiKey();
		loadAliases();
		loadExceptions();
	}
	
	@EventHandler
	public void init( FMLPostInitializationEvent event ) {
		
		registerEventListeners();
		
		KeyHandler keyHandler = new KeyHandler();
	}
 	
	private void loadReplyMod() {
		if ( c.f.exists() )
			config = c.readConfig();
		else 
			config = new TreeMap<String, Config>();
	}
	
	private void loadApiKey() {
		try {
			API_KEY = APIReaderWriter.readFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadAliases() {
		if( AliasReaderWriter.f.exists() ) {
			try {
				aliases = AliasReaderWriter.readFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void loadExceptions() {
		if( ExceptionsReaderWriter.f.exists() ) {
			try {
				exceptions = ExceptionsReaderWriter.readFile();
				
				for ( int i = 0; i < exceptions.size(); i++ ) {
					exceptionsName.add( JsonTools.getNameFromUUID( exceptions.get(i) ) );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	private void registerEventListeners(){
		MinecraftForge.EVENT_BUS.register( new ClientChatReceievedEventHandler() );
		MinecraftForge.EVENT_BUS.register( new HypixelChecker() );
		MinecraftForge.EVENT_BUS.register( new TickHandler() );
		ClientCommandHandler.instance.registerCommand( new ReplyModCommand() );
		ClientCommandHandler.instance.registerCommand( new GuildChatShortcut() );
		ClientCommandHandler.instance.registerCommand( new PartyChatShortcut() );
		ClientCommandHandler.instance.registerCommand( new ShoutShortcut() );
		ClientCommandHandler.instance.registerCommand( new AllChatShortcut() );
		ClientCommandHandler.instance.registerCommand( new APIKeySet() );
		ClientCommandHandler.instance.registerCommand( new ToggleGuildChat() );
		ClientCommandHandler.instance.registerCommand( new TogglePartyChat() );
		ClientCommandHandler.instance.registerCommand( new AddAlias() );
		ClientCommandHandler.instance.registerCommand( new FriendPruneCommand() );
		ClientCommandHandler.instance.registerCommand( new AddException() );
		ClientCommandHandler.instance.registerCommand( new GetUUID() );
		ClientCommandHandler.instance.registerCommand( new FileOpenDummyCommand() );
		ClientCommandHandler.instance.registerCommand( new AutoGG() );
		ClientCommandHandler.instance.registerCommand( new OnlineChecker() );
	}
}
