package storage;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import net.minecraft.client.Minecraft;

public class Cache implements Serializable {
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	public static String writeFriendPruningLog( ArrayList<FriendLog> log, String type, String timeLimit ) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Calendar calendarobj = Calendar.getInstance();
		String time = df.format( calendarobj.getTime() );
		ArrayList<FriendLog> notRemoved = new ArrayList<FriendLog>();
		
		BufferedWriter out = null;

		File f = new File( mc.mcDataDir + "/HypixelToolsMod/log-" + time + "-" + type + ".txt" );
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			out = new BufferedWriter( new FileWriter( f ) );
			
			if( type.equals("list") ) {
				out.write( "A friend pruning log that shows what friends may have been pruned for exceeding the time limit of " + timeLimit + " witout logging in." );
				out.newLine();
				out.write( "Type: List - in list mode, no friends are actually removed. It is just for reference." );
				out.newLine();
				out.newLine();
				out.newLine();
				out.write( "Friends that would have been removed for exceeding the time limit if the type was remove." );
			}
			else if( type.equals("remove") ) {
				out.write( "A friend pruning log that shows what friends may have been pruned for exceeding the time limit of " + timeLimit + " witout logging in." );
				out.newLine();
				out.write( "Type: Remove - in remove mode, friends are removed for exceeding the time limit." );
				out.newLine();
				out.newLine();
				out.newLine();
				out.write( "Friends that were removed for exceeding the time limit." );
			}
			
			out.newLine();
			out.write( "UUID | Name at time of removal | Exception Made (true/false)");
			
			for( int i = 0; i < log.size(); i++ ) {
				if( log.get(i).getRemoved() == true ) {
					out.newLine();
					out.write( log.get(i).getUUID().toString() + " | " + log.get(i).getString() + StringUtils.repeat(" ", ( 16 - log.get(i).getString().length() ) ) + " | " + log.get(i).getException() );
				}
				else if( log.get(i).getRemoved() == false ) {
					notRemoved.add( log.get(i) );
				}
			}
			
			if( type.equals("list") ) {
				out.newLine();
				out.newLine();
				out.newLine();
				out.write( "Friends that didn't break the time limit, and would not have been removed even if the type was remove." );
				out.newLine();
				out.write( "UUID | Name at time of removal | Exception Made (true/false)" );
			}
			else if( type.equals("remove") ) {
				out.newLine();
				out.newLine();
				out.newLine();
				out.write( "Friends that didn't break the time limit, and were thus not removed." );
				out.newLine();
				out.write( "UUID | Name at time of removal | Exception Made (true/false)" );
			}
			
			for( int i = 0; i < notRemoved.size(); i++ ) {
				out.newLine();
				out.write( notRemoved.get(i).getUUID().toString() + " | " + notRemoved.get(i).getString() + StringUtils.repeat(" ", ( 16 - notRemoved.get(i).getString().length() ) ) + " | " + notRemoved.get(i).getException() );
			}
			
			out.newLine();
			out.newLine();
			out.newLine();
			out.write("Log generated successfully!" );
			out.flush();
			out.close();
			
			File recent = new File( mc.mcDataDir + "/HypixelToolsMod/recentprune.txt" );
			if( recent.exists() ) {
				recent.delete();
			}
			
			FileUtils.copyFile(f, recent);
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return "./HypixelToolsMod/recentprune.txt";
	}
	
	public static void writeFriendLogObj( ArrayList<FriendLog> log, FPruneInfo info ) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Calendar calendarobj = Calendar.getInstance();
		String time = df.format( calendarobj.getTime() );
		
		ObjectOutputStream out = null;

		File f1 = new File( mc.mcDataDir + "./HypixelToolsMod/log-" + time + "-" + info.getType() + ".dat" );
		try {
			f1.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		f1.delete();
		
		try{
			out = new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream( f1 ) ) );
			
			out.writeObject( info );
			
			for( int i = 0; i < log.size(); i++ ) {
				out.writeObject( log.get(i) );
			}
			
			out.flush(); //Flush outs buffer and close it
			out.close();
		}
		catch( Exception e){
			System.out.println( "Error occured in writing to file: " + e.toString() );
			e.printStackTrace();
		}
	}
}
