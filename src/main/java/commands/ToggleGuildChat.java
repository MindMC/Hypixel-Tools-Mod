package commands;

import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ToggleGuildChat extends CommandBase{
	
	public static boolean guildChatState = true;
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "togglegchat";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /togglegchat";
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
			guildChatState = !guildChatState;
			if( guildChatState ) {
				PrintMessageHT.showMsg( "All guild chat messages shown." );
			}
			else {
				PrintMessageHT.showMsg( "Guild chat messages only shown if they contain an alias." );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}

