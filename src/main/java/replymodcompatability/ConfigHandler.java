package replymodcompatability;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;

public class ConfigHandler implements ConfigReader, ConfigWriter, Serializable{
	public File f = new File( Minecraft.getMinecraft().mcDataDir + "/config/ReplyMod.dat" );
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	
	public TreeMap<String, Config> readConfig(){
		TreeMap<String, Config> config = new TreeMap<String, Config>();
		
		try{
			in = new ObjectInputStream( new BufferedInputStream( new FileInputStream( f ) ) );
			
			while( true ){ //Code will run until EOFException, and then config will be returned;
				Config foo = ( Config ) in.readObject();
				config.put( foo.getCommand(), foo );
			}
		}
		catch( EOFException e ){
			return config; //Once end of file is reached, return contact
		}
		catch( Exception e ){
			System.out.println( "Error occured in reading from config" + e.toString() );
			e.printStackTrace();
		}
		return config;
	}

	public void writeConfig( TreeMap<String, Config> configMap ) { 
		f.delete();
		
		try{
			out = new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream( f ) ) );
			
			for( Map.Entry<String, Config> c : configMap.entrySet() ){ //Loop through the values in the TreeMap of type Config
				out.writeObject(c.getValue()); //Write each Config object to the file.
			}
			
			out.flush(); //Flush outs buffer and close it
			out.close();
		}
		catch( Exception e){
			System.out.println( "Error occured in writing to config: " + e.toString() );
			e.printStackTrace();
		}
	}
	
}
