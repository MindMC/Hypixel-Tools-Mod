package commands;

import hypixeltoolsmod.HypixelToolsMod;
import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import storage.APIReaderWriter;

public class APIKeySet extends CommandBase{
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "apiset";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /apiset [Api Key]";
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
				PrintMessageHT.showRedMessage( "Usage: /apiset [Api Key]" );
			}
			else {
				String message = "";
				for ( int i = 0; i < args.length; i++ ) {
					message += args[i];
				}
				
				HypixelToolsMod.API_KEY = message;
				
				PrintMessageHT.showMsg( "Api key set to " + HypixelToolsMod.API_KEY );
				
				APIReaderWriter.writeFile( message );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}

