package commands;

import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class PartyChatShortcut extends CommandBase {
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "pc";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Usage: /pc [message]";
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
				PrintMessageHT.showRedMessage( "Usage: /pc [message]" );
			}
			else {
				String message = "";
				for ( int i = 0; i < args.length; i++ ) {
					message += args[i] + " ";
				}
				
				mc.thePlayer.sendChatMessage( "/pchat " + message );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}

	}

}
