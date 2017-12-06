package unitClass;

import java.util.ArrayList;

public class Room {
	
	private int rid;
	private String rName;
	private int capacity;
	private String Description;
	
	public Room(int rid,String rName,int capacity,String Description) {
		this.rid = rid;
		this.rName = rName;
		this.capacity = capacity;
		this.Description = Description;
	}
	
	public int getRid() {
		return rid;
	}
	public String getrName() {
		return rName;
	}

	public int getCapacity() {
		return capacity;
	}
	public String getDescription() {
		return Description;
	}
	public String toString() {
		String str = "Room// Name : "+this.rName+", Capacity : "+this.capacity;
		return str;
	}
}
