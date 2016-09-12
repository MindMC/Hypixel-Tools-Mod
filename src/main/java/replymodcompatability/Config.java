package replymodcompatability;

import java.io.Serializable;

public class Config implements Serializable{
	String content, command;
	
	public Config( String content, String command ) {
		this.content = content;
		this.command = command;
	}
	
	//Setter and getter methods
	public void setContent( String c ){ content = c; }
	public void setCommand( String c ){ command = c; }
	
	public String getContent(){ return content; }
	public String getCommand(){ return command; }
}
