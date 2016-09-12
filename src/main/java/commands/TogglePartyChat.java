package commands;

import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class TogglePartyChat extends CommandBase{
	
	public static boolean partyChatState = true;
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "togglepchat";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /togglepchat";
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
			partyChatState = !partyChatState;
			if( partyChatState ) {
				PrintMessageHT.showMsg( "All party chat messages shown." );
			}
			else {
				PrintMessageHT.showMsg( "Party chat messages only shown if they contain an alias." );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}


