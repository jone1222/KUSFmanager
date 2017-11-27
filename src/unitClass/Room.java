package unitClass;

import java.util.ArrayList;

public class Room {
	
	private int rid;
	private String rName;
	private int capacity;
	
	public Room(int rid,String rName,int capacity) {
		this.rid = rid;
		this.rName = rName;
		this.capacity = capacity;
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

}
