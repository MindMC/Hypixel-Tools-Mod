package commands;

import java.util.List;

import hypixeltoolsmod.HypixelToolsMod;
import methods.FriendPruner;
import methods.PrintMessageHT;
import methods.ReferenceUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class FriendPruneCommand extends CommandBase{
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "fprune";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /fprune [Type: List/Remove] [Unit of Time] [Number of Units]";
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
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] { "list", "remove" }) : (args.length == 2 && ( args[0].equals("list") | args[0].equals("remove") )? getListOfStringsMatchingLastWord(args, new String[] { "days", "weeks", "months", "years" } ) : null);
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
				PrintMessageHT.showRedMessage( "Usage: /fprune [Type: List/Remove] [Unit of Time] [Number of Units]" );
			}
			else if( args.length != 3 ) {
				PrintMessageHT.showRedMessage( "Command not recognized or mistyped. Use /fprune for help." );
			}
			else if( ReferenceUTIL.isInteger( args[2] ) == false ) {
				PrintMessageHT.showMsg( args[2] + " is not a valid number." );
			}
			else if( ( args[0].equals("list") || args[0].equals("remove") ) && ( ( args[1].equals("days") || args[1].equals("weeks") || args[1].equals("months") || args[1].equals("years") ) ) ) {
				new FriendPruner( args ).start();
			}
			
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}


