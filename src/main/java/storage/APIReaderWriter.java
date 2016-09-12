package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.client.Minecraft;

public class APIReaderWriter {
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	public static File f = new File( mc.mcDataDir + "/HypixelToolsMod/apikey.txt" );
	
	static BufferedWriter out = null;
	static BufferedReader in = null;
	
	public static void writeFile( String api ) throws IOException {
		f.delete();
		f.createNewFile();
		
		out = new BufferedWriter ( new FileWriter( f ) );
		
		out.write( api );
		out.flush();
		out.close();
	}
	
	public static String readFile() throws Exception {
		
		if( f.exists() ) {
			in = new BufferedReader( new FileReader( f ) );
			
			String input;
			String api = "";
			
			while( ( input = in.readLine() ) != null ) {
				api += input;
			}
			
			return api;
		}
		else {
			return null;
		}
	}

}
