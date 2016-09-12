package listeners;

import java.io.IOException;

import commands.AutoGG;
import commands.OnlineChecker;
import commands.ToggleGuildChat;
import commands.TogglePartyChat;
import hypixeltoolsmod.HypixelToolsMod;
import methods.PrintMessageHT;
import methods.Util;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import storage.APIReaderWriter;

public class ClientChatReceievedEventHandler {
	
	public static boolean cancelChatA = false;
	
	Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	
	public void onEvent( ClientChatReceivedEvent event ){
		String msg = event.message.getUnformattedText();
		if( HypixelToolsMod.hypixel ){
			//Reply Mod Code
			if( HypixelToolsMod.replyModOn ){			
				if( msg.startsWith( "From " ) ) //If the string begins with From and the mod is on reply with the current message
					Minecraft.getMinecraft().thePlayer.sendChatMessage( HypixelToolsMod.currentMessage );
			}
			if( msg.startsWith( "Your new API key is " ) ) {
				HypixelToolsMod.API_KEY = msg.replaceAll( "Your new API key is ", "" );

				try {
					PrintMessageHT.showMsg( "Api key set to " + HypixelToolsMod.API_KEY );
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					APIReaderWriter.writeFile( HypixelToolsMod.API_KEY );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			boolean containsAlias = false;
			for( int i = 0; i < HypixelToolsMod.aliases.size(); i++ ) {
				if( msg.toLowerCase().contains( HypixelToolsMod.aliases.get(i).toLowerCase() ) ) {
					containsAlias = true;
				}
			}
			
			if( msg.startsWith( "Guild > " ) && !ToggleGuildChat.guildChatState ) {
				if( containsAlias == false )
					event.setCanceled(true);
			}
			if( msg.startsWith( "Party > " ) && !TogglePartyChat.partyChatState ) {
				if( containsAlias == false )
					event.setCanceled(true);
			}
			
			if( msg.startsWith( " " ) && msg.contains( "Winner - ") && AutoGG.autoGGOn ) {
				mc.thePlayer.sendChatMessage( "/chat a" );
				
				Util.waitTime( 200 );
				
				mc.thePlayer.sendChatMessage( "gg" );
			}
			
			if( OnlineChecker.onlineChecking ) {
				try {
					if( msg.startsWith( "That player isn't online!" ) ) {
						PrintMessageHT.showMsg( OnlineChecker.lastPlayer + " is offline." );
						event.setCanceled(true);
					}
					else if( msg.startsWith( "You can only message people who are your friends list!" ) ) {
						PrintMessageHT.showMsg( OnlineChecker.lastPlayer + " is online." );
						event.setCanceled(true);
					}
					else if( msg.startsWith( "Opened a chat conversation with " ) ) {
						PrintMessageHT.showMsg( OnlineChecker.lastPlayer + " is online." );
						cancelChatA = true;
						event.setCanceled(true);
						Minecraft.getMinecraft().thePlayer.sendChatMessage( "/chat a" );
					}
					else if( msg.startsWith( "Can't find a player with the name of " ) ) {
						PrintMessageHT.showRedMessage( OnlineChecker.lastPlayer + " is not a valid player." );
						event.setCanceled(true);
					}
					OnlineChecker.onlineChecking = false;
				}
				catch ( Exception e ) {
					e.printStackTrace();
				}
			}
			
			if( msg.startsWith( "You are now in the ALL channel" ) && cancelChatA ) {
				cancelChatA = false;
				event.setCanceled(true);
			}
		}
	}
}
