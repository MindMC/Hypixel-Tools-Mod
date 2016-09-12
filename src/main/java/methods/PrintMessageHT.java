package methods;

import java.io.IOException;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class PrintMessageHT {
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	public static void showMsg(String msg) throws IOException {
		mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText((Object)EnumChatFormatting.GREEN + "H" + (Object)EnumChatFormatting.BLUE + "T" + (Object)EnumChatFormatting.DARK_GRAY + " > " + (Object)EnumChatFormatting.GRAY + msg));
    }
	
	public static void showRedMessage( String msg ) throws IOException{
		mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText( (Object) EnumChatFormatting.RED + msg));
	}

    public static void rawMsg(String msg) throws IOException {
    	mc.thePlayer.addChatComponentMessage((IChatComponent) new ChatComponentText(msg) );
    }

    public static void line() throws IOException {
    	mc.thePlayer.addChatComponentMessage((IChatComponent) new ChatComponentText((Object)EnumChatFormatting.GREEN + "" + (Object)EnumChatFormatting.BOLD + "----------------------------------"));
    }

	public static void showYellowMsg(String string, String command) {
		// TODO Auto-generated method stub
		mc.thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText((Object)EnumChatFormatting.GREEN + "H" + (Object)EnumChatFormatting.BLUE + "T" + (Object)EnumChatFormatting.DARK_GRAY + " > " + (Object)EnumChatFormatting.GRAY + string + (Object)EnumChatFormatting.GOLD + command ));
	}
	
	public static void apiPrompt() {
		try {
			showMsg( "Please run /api or /apiset [api key]." );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
