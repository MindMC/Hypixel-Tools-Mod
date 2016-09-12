package commands;

import java.util.List;
import java.util.UUID;

import hypixeltoolsmod.HypixelToolsMod;
import methods.APIUtil;
import methods.PrintMessageHT;
import methods.UUIDFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import storage.ExceptionsReaderWriter;

public class AddException extends CommandBase{
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "exception";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /exception <add [name], delete [name], list>";
	}
	
	public int getRequiredPermissionLevel(){
		return 0;
	}
	
	public boolean canSenderUseCommand( ICommandSender sender ){
		return true;
	}
	
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos){
		
		String[] exceptionsList = null;
		
		if( HypixelToolsMod.exceptionsName.isEmpty() == false ) {
			exceptionsList = new String[HypixelToolsMod.exceptionsName.size()];
			for( int i = 0; i < exceptionsList.length; i++  ) {
				exceptionsList[i] = HypixelToolsMod.exceptionsName.get(i);
			}
		}
		
		if( HypixelToolsMod.exceptionsName.isEmpty() == true ) {
			exceptionsList = new String[] { " " };
		}
		
		try{
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] { "add", "delete", "list" }) : (args.length == 2 && args[0].equals("delete") ? getListOfStringsMatchingLastWord(args, exceptionsList) : null);
		}
		catch ( Exception e ){
			e.printStackTrace();
		}
		
		//If shit goes wrong
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try{
			if( args.length == 0 ) {
				PrintMessageHT.showRedMessage( "Usage: /exception <add [name], delete [name], list>" );
			}
			else if ( args[0].equals( "list" ) ) {
				PrintMessageHT.line();
				
				for( int i = 0; i < HypixelToolsMod.exceptionsName.size(); i++ ){ 
					PrintMessageHT.showMsg( HypixelToolsMod.exceptionsName.get(i) + " | " + APIUtil.withDashes( HypixelToolsMod.exceptions.get(i) ) );
				}
				
				PrintMessageHT.line();
			}
			else if( args[0].equals( "add" ) ) {
				String exception = args[1];
				UUID uuid = UUIDFetcher.getUUIDOf( exception );
				String returnedUUID = APIUtil.stripDashes(uuid);
				
				ExceptionsReaderWriter.writeFile( returnedUUID );
				HypixelToolsMod.exceptionsName.add( exception );
				
				PrintMessageHT.showMsg( "Exception " + exception + " with uuid " + uuid.toString() + " added" );
			}
			else if( args[0].equals( "delete" ) ) {
				String exception = args[1];
				UUID uuid = UUIDFetcher.getUUIDOf( exception );
				String returnedUUID = APIUtil.stripDashes(uuid);
				
				HypixelToolsMod.exceptions.remove( uuid );
				ExceptionsReaderWriter.refreshFile();
				
				PrintMessageHT.showMsg( "Exception " + exception + " with uuid " + uuid.toString() + " removed" );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}


