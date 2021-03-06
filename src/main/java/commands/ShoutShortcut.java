package commands;

import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ShoutShortcut extends CommandBase{
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "s";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /s [message]";
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
				PrintMessageHT.showRedMessage( "Usage: /s [message]" );
			}
			else {
				String message = "";
				for ( int i = 0; i < args.length; i++ ) {
					message += args[i];
				}
				
				mc.thePlayer.sendChatMessage( "/shout " + message );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}

