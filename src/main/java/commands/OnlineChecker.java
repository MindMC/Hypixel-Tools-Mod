package commands;

import java.io.IOException;

import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class OnlineChecker extends CommandBase {
	
	public static boolean onlineChecking = false;
	public static String lastPlayer = "";

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "online";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Usage: /online [name]";
	}
	
	public int getRequiredPermissionLevel(){
		return 0;
	}
	
	public boolean canSenderUseCommand( ICommandSender sender ){
		return true;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		if( args.length == 0 ){
			try {
				PrintMessageHT.showRedMessage("Usage: /online [name]");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if( args.length == 1 ) {
			lastPlayer = args[0];
			onlineChecking = true;
		
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/msg " + args[0]);
		}
	}

}
