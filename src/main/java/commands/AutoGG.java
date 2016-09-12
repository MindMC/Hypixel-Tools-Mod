package commands;

import java.io.IOException;

import methods.PrintMessageHT;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class AutoGG extends CommandBase {
	
	public static boolean autoGGOn = true;

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "autogg";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/autogg";
	}
	
	public int getRequiredPermissionLevel(){
		return 0;
	}
	
	public boolean canSenderUseCommand( ICommandSender sender ){
		return true;
	}


	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {

		autoGGOn = !autoGGOn;
		
		if( autoGGOn ) {
			try {
				PrintMessageHT.showMsg("AutoGG [ON]");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				PrintMessageHT.showMsg("AutoGG [OFF]");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
