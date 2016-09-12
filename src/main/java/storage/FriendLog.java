package storage;

import java.io.Serializable;
import java.util.UUID;

public class FriendLog implements Serializable{
	
	private UUID uuid;
	private String name;
	private boolean removed;
	private boolean exception; 
	
	public FriendLog( UUID u, String n, boolean r, boolean e ) {
		uuid = u;
		name = n;
		removed = r;
		exception = e;
	}
	
	public void setUUID ( UUID u ) { uuid = u; }
	public void setName ( String n ) { name = n; }
	public void setRemoved ( boolean r ) { removed = r; }
	public void setException ( boolean e ) { exception = e; }
	
	public UUID getUUID() { return uuid; }
	public String getString() { return name; }
	public boolean getRemoved() { return removed; }
	public boolean getException() { return exception; }
	
}








