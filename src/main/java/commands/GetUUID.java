package commands;

import methods.PrintMessageHT;
import methods.UUIDFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;

public class GetUUID extends CommandBase{
	
	Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getCommandName() {
		return "uuid";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage: /uuid [name]";
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
				PrintMessageHT.showRedMessage( "Usage: /uuid [name]" );
			}
			else {
				String uuid = UUIDFetcher.getUUIDOf(args[0]).toString();
				String out = "[\"\",{\"text\":\"Click to copy: [" + uuid + "]\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + uuid + "\"}}]";
				
				IChatComponent chat_out = IChatComponent.Serializer.jsonToComponent(out);
				Minecraft.getMinecraft().thePlayer.addChatMessage(chat_out);
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

}
