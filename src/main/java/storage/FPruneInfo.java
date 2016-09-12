package storage;

import java.io.Serializable;

public class FPruneInfo implements Serializable{
	
	private String type;
	private String timeLimit;
	private long timeLimitUnix;
	
	public FPruneInfo( String t, String tL, long tLU ) {
		type = t;
		timeLimit = tL;
		timeLimitUnix = tLU;
	}
	
	public void setType( String t ) { type = t; }
	public void setTimeLimit( String t ) { timeLimit = t; }
	public void setTimeLimitUnix( long t ) { timeLimitUnix = t; }
	
	public String getType() { return type; }
	public String getTimeLimit() { return timeLimit; }
	public long getTimeLimitUnix() { return timeLimitUnix; }

}
