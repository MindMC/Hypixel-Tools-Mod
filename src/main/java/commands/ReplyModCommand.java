package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import hypixeltoolsmod.HypixelToolsMod;
import methods.PrintMessageHT;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import replymodcompatability.Config;
import scala.actors.threadpool.Arrays;

public class ReplyModCommand extends CommandBase{
	
	public int getRequiredPermissionLevel(){
		return 0;
	}
	
	public boolean canSenderUseCommand( ICommandSender sender ){
		return true;
	}
	
	public String getCommandUsage( ICommandSender sender ){
		return "Usage: /replymod <add [Command Name] [Message], delete [Command Name], list>";
	}
	
	public List getAliases(){
		return Arrays.asList( new String[] { "rm", "REPLYMOD", "ReplyMod", "replymod" } );
	}

	@Override
	public String getCommandName() {
		return "replymod";
	}
	
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos){
		
		String[] configList = null;
		int i = 0;
		
		if( !HypixelToolsMod.config.isEmpty() ) {
			configList = new String[HypixelToolsMod.config.size()];
		}
		
		if( !HypixelToolsMod.config.isEmpty() ) {
			for( Map.Entry<String, Config> entry : HypixelToolsMod.config.entrySet()  ) {
				configList[i] = entry.getKey();
				i++;
			}
		}
		
		String[] argsZero = ( String[] ) ArrayUtils.addAll( new String[] { "add",  "delete", "list" }, configList );
		
		try{
			return args.length == 1 ? getListOfStringsMatchingLastWord(args, argsZero): (args.length == 2 && args[0].equals("delete") ? getListOfStringsMatchingLastWord(args, configList) : null);
		}
		catch ( Exception e ){
			e.printStackTrace();
		}
		
		//If shit goes wrong
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		try{
			if( args.length == 0){
				//Print out the available commands
				PrintMessageHT.showRedMessage( "Usage: /replymod <add [Command Name] [Message], delete [Command Name], list>" );
			}
			else if ( args.length >= 1 ){
				if( args[0].equalsIgnoreCase( "help" ) ){ //Run the help command
					//Print out the available commands
					PrintMessageHT.showRedMessage("Usage: /replymod <add [Command Name] [Message], delete [Command Name], list>" );
				}
				else if( args[0].equalsIgnoreCase( "list" ) ){ //Run the list command
					//List out the available custom messages
					PrintMessageHT.line();
					//PrintMessageHT.showFormattedMessage( "Avaliable Custom Messages", EnumChatFormatting.DARK_GREEN );
					
					for( Map.Entry<String, Config> c : HypixelToolsMod.config.entrySet() ){ //Loop through command list TreeMap
						//Prints out current command of Config object in the TreeMap
						PrintMessageHT.showYellowMsg( "/replymod ", c.getValue().getCommand() );
					}
					
					PrintMessageHT.line();
				}
				else if( args[0].equalsIgnoreCase( "add" ) ){ //If the user wants to add a new custom command, run this code
					//Attempt to add a slot in the TreeArray with the new command name, and message.
					
					try{
						String foo = ""; 
						for( int i = 2; i < args.length; i++ ){ //Add any of the args containing the message to foo
							foo += args[i] + " ";
						}
						HypixelToolsMod.config.put( args[1].toLowerCase(), new Config( foo, args[1].toLowerCase() ) );
						PrintMessageHT.showMsg( "Command " + args[1].toLowerCase() + " added" );
					}
					catch( Exception e ){ //In case the user does not fill out args[1] and args[2]
						PrintMessageHT.showRedMessage( "Command not recognized/mistyped. Use /replymod for help" );
					}
					
					HypixelToolsMod.c.writeConfig( HypixelToolsMod.config ); //Write the new TreeMap to the config file
				}
				else if( args[0].equalsIgnoreCase( "delete" ) ){ //If the user wants to delete a custom command, run this code.
					//Attempt to delete the custom message from the TreeMap
					try{
						HypixelToolsMod.config.remove( args[1].toLowerCase() ); //Remove the Config object from the TreeMap
						PrintMessageHT.showMsg( "Command " + args[1] + " deleted" );
					}
					catch( Exception e ){ //In case the user does not fill out args[1]
						PrintMessageHT.showRedMessage( "Command not recognized/mistyped. Use /replymod for help" );
					}
					
					HypixelToolsMod.c.writeConfig( HypixelToolsMod.config );
				}
				else if( args[0] != null ){ //If there is a parameter, but it's none of the above, see if its a custom message
					//Attempt to turn on the custom message
					try{
						if( HypixelToolsMod.replyModOn == true && args[0].toLowerCase().equals( HypixelToolsMod.currentCommand )){ //If a custom message is already enabled, and it's the same message as the one entered in args, run this code.
							HypixelToolsMod.replyModOn = false; //Turn off auto reply command
							PrintMessageHT.showMsg( HypixelToolsMod.currentCommand + "[OFF]" ); //Say the message is off
						}
						else{ //Otherwise, run this code.
							HypixelToolsMod.currentMessage = "/r " + HypixelToolsMod.config.get( args[0].toLowerCase() ).getContent(); //Set the currentMessage to /r + the message associated with the command.
							HypixelToolsMod.currentCommand = args[0].toLowerCase(); //Set the current command to the user input
							HypixelToolsMod.replyModOn = true; //Turn on auto reply command
							PrintMessageHT.showMsg( HypixelToolsMod.currentCommand + "[ON]" ); //Say the message is on
						}
					}
					catch( Exception e ){ //If it's not a custom message, then it's none of the available commands at this point
						PrintMessageHT.showRedMessage( "Command not recognized/mistyped. Use /replymod for help" );
					}
				}
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}
}
