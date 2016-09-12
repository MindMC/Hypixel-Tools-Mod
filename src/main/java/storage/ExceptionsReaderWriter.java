package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import hypixeltoolsmod.HypixelToolsMod;
import net.minecraft.client.Minecraft;

public class ExceptionsReaderWriter {
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	public static File f = new File( mc.mcDataDir +  "/HypixelToolsMod/exceptions.txt" );
	
	static BufferedWriter out = null;
	static BufferedReader in = null;
	
	public static void writeFile( String exception ) throws IOException {
		f.delete();
		f.createNewFile();
		HypixelToolsMod.exceptions.add( exception );
		
		out = new BufferedWriter ( new FileWriter( f ) );
		
		for( int i = 0; i < HypixelToolsMod.exceptions.size(); i++ ) {
			out.write( HypixelToolsMod.exceptions.get(i) + ":" );
		}

		out.flush();
		out.close();
	}
	
	public static void refreshFile() throws IOException {
		f.delete();
		f.createNewFile();
		
		out = new BufferedWriter( new FileWriter( f ) );
		
		for( int i = 0; i < HypixelToolsMod.exceptions.size(); i++ ) {
			out.write( HypixelToolsMod.exceptions.get(i) + ":" );
		}

		out.flush();
		out.close();
	}
	
	public static ArrayList<String> readFile() throws IOException {
		if( f.exists() ) {
			in = new BufferedReader( new FileReader( f ) );
			
			String input;
			String totalString = "";
			ArrayList<String> arrayList = new ArrayList<String>();
			
			while( ( input = in.readLine() ) != null ) {
				totalString += input;
			}
			
			String[] list = totalString.split(":");
			
			for( int i = 0; i < list.length; i++ ) {
				arrayList.add( list[i] );
			}
			return arrayList;
		}
		else {
			return null;
		}
	}
}

