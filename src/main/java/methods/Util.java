package methods;

public class Util {
	
	public static void waitTime( long time ) {
		long startTime = System.currentTimeMillis();
		boolean timeNotReached = true;
		while( timeNotReached ) {
			if( System.currentTimeMillis() - startTime >= time ) {
				timeNotReached = false;
			}
		}
	}

}
