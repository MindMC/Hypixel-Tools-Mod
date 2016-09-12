package listeners;

import hypixeltoolsmod.HypixelToolsMod;
import methods.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TickHandler {
	
	Minecraft mc = Minecraft.getMinecraft();
	
	public static long timeSincePressedGuild = 0;
	public static long timeSincePressedParty = 0;
	public static long timeSincePressedAll = 0;
	
	@SubscribeEvent
	public void playerTick( ClientTickEvent event ){
		
		if( KeyHandler.guild.isKeyDown() && HypixelToolsMod.hypixel ) {
			if( ( System.currentTimeMillis() - timeSincePressedGuild ) > 1000 ) {
				mc.thePlayer.sendChatMessage( "/chat g" );
				timeSincePressedGuild = System.currentTimeMillis();
			}
		}
		
		if( KeyHandler.party.isKeyDown() && HypixelToolsMod.hypixel ) {
			if( ( System.currentTimeMillis() - timeSincePressedParty ) > 1000 ) {
				mc.thePlayer.sendChatMessage( "/chat p" );
				timeSincePressedParty = System.currentTimeMillis();
			}
		}
		
		if( KeyHandler.all.isKeyDown() && HypixelToolsMod.hypixel ) {
			if( ( System.currentTimeMillis() - timeSincePressedAll ) > 1000 ) {
				mc.thePlayer.sendChatMessage( "/chat a" );
				timeSincePressedAll = System.currentTimeMillis();
			}
		}
	}

}
