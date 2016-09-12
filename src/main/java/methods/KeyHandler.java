package methods;

import org.lwjgl.input.Keyboard;

import hypixeltoolsmod.HypixelToolsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class KeyHandler {
	
	public static KeyBinding guild = new KeyBinding( "Toggle Guild Chat", Keyboard.KEY_G, "Hypixel Tools Mod" );
	public static KeyBinding party = new KeyBinding( "Toggle Party Chat", Keyboard.KEY_P, "Hypixel Tools Mod" );
	public static KeyBinding all = new KeyBinding( "Toggle Global Chat", Keyboard.KEY_B, "Hypixel Tools Mod" );
	
	Minecraft mc = Minecraft.getMinecraft();
	
	public KeyHandler() {
		ClientRegistry.registerKeyBinding(guild);
		ClientRegistry.registerKeyBinding(party);
		ClientRegistry.registerKeyBinding(all);
	}
	
}
