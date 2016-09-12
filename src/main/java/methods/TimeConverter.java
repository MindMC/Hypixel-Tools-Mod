package methods;

import java.io.IOException;

public class TimeConverter {
	
	public static long getUnixTime( String unit, String howMuch ) {
		long ammount = Long.parseLong(howMuch);
		long value = 0;
		
		if( unit.equals( "days" ) ) {
			value = ammount * 86400000L; 
		}
		else if( unit.equals( "weeks" ) ) {
			value = ammount * 604800000L;
		}
		else if( unit.equals( "months" ) ) {
			value = ammount * 2629746000L;
		}
		else if( unit.equals( "years" ) ) {
			value = ammount * 31556908800L ;
		}
		
		if( value > ( System.currentTimeMillis() - 1383938062710L ) ) {
			try {
				PrintMessageHT.showRedMessage("Entered a time longer then existence of API. No friends will be removed.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FriendPruner.threadInterrupted = true;
		}
		else {
			return value;
		}
		return value;
			
	}
}
