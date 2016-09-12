package commands;

import methods.PrintMessageHT;
import methods.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class AllChatShortcut extends CommandBase{
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "ac";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /ac [message]";
	}
	
	public int getRequiredPermissionLevel(){
		return 0;
	}
	
	public boolean canSenderUseCommand( ICommandSender sender ){
		return true;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try{
			if( args.length == 0 ) {
				PrintMessageHT.showRedMessage( "Usage: /ac [message]" );
			}
			else {
				String message = "";
				for ( int i = 0; i < args.length; i++ ) {
					message += args[i] + " ";
				}
				
				mc.thePlayer.sendChatMessage( "/chat a" );
				
				Util.waitTime( 200 );
				
				mc.thePlayer.sendChatMessage( message );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}
