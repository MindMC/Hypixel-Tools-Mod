package listeners;

import java.io.IOException;

import hypixeltoolsmod.HypixelToolsMod;
import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import storage.APIReaderWriter;
import updater.ReplyUpdater;

public class HypixelChecker {
	Minecraft mc = Minecraft.getMinecraft();
	boolean check = false;
	
	@SubscribeEvent
	public void onLoginEvent( FMLNetworkEvent.ClientConnectedToServerEvent e ) {
		
		if( mc.isSingleplayer() ){
			HypixelToolsMod.hypixel = false;
		}
		else if( FMLClientHandler.instance().getClient().getCurrentServerData().serverIP.contains(".hypixel.net" ) ){
			HypixelToolsMod.hypixel = true;
			
			if( !this.check ){
				new Thread( new ReplyUpdater() ).start();
				this.check = true;
			}
			
		}
		else
			HypixelToolsMod.hypixel = false;
		
	}
	
	@SubscribeEvent
	public void onLogoutEvent( FMLNetworkEvent.ClientDisconnectionFromServerEvent e ){
		HypixelToolsMod.hypixel = false;
	}
}
