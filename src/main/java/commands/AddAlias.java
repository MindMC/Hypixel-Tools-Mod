package commands;

import java.util.List;

import hypixeltoolsmod.HypixelToolsMod;
import methods.PrintMessageHT;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import storage.AliasReaderWriter;

public class AddAlias extends CommandBase{
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "alias";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /alias <add [name], delete [name], list>";
	}
	
	public int getRequiredPermissionLevel(){
		return 0;
	}
	
	public boolean canSenderUseCommand( ICommandSender sender ){
		return true;
	}
	
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos){
		
		String[] aliasList = null;
		
		if( HypixelToolsMod.aliases.isEmpty() == false ) {
			aliasList = new String[HypixelToolsMod.aliases.size()];
			for( int i = 0; i < aliasList.length; i++  ) {
				aliasList[i] = HypixelToolsMod.aliases.get(i);
			}
		}
		
		if( HypixelToolsMod.aliases.isEmpty() == true ) {
			aliasList = new String[] { mc.thePlayer.getName() };
		}
		
		try{
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] { "add", "delete", "list" }) : (args.length == 2 && args[0].equals("delete") ? getListOfStringsMatchingLastWord(args, aliasList) : null);
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
			if( HypixelToolsMod.aliases.contains( mc.thePlayer.getName() ) == false )
				HypixelToolsMod.aliases.add( mc.thePlayer.getName() );
			if( args.length == 0 ) {
				PrintMessageHT.showRedMessage( "Usage: /alias <add [name], delete [name], list>" );
			}
			else if ( args[0].equals( "list" ) ) {
				PrintMessageHT.line();
				
				for( int i = 0; i < HypixelToolsMod.aliases.size(); i++ ){
					PrintMessageHT.showMsg( HypixelToolsMod.aliases.get(i) );
				}
				
				PrintMessageHT.line();
			}
			else if( args[0].equals( "add" ) ) {
				String alias = "";
				for ( int i = 1; i < args.length; i++ ) {
					if ( i > 1 ) {
						alias = alias + " " + args[i];
					}
					else {
						alias += args[i];
					}
				}
				
				AliasReaderWriter.writeFile( alias );
				
				PrintMessageHT.showMsg( "Alias " + alias + " added" );
			}
			else if( args[0].equals( "delete" ) ) {
				String alias = "";
				for ( int i = 1; i < args.length; i++ ) {
					alias += args[i];
				}
				
				HypixelToolsMod.aliases.remove( alias.toLowerCase() );
				AliasReaderWriter.refreshFile();
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}


