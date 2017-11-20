package unitClass;

import java.util.ArrayList;

public class Room {
	
	private int rid;
	private String rName;
	private int capacity;
	private ArrayList<Reservation> reservations;
	private ArrayList<Item> items;
	
	public Room(int rid) {
		this.rid = rid;
		reservations = new ArrayList<>();
		items = new ArrayList<>();
	}
	
	public int getRid() {
		return rid;
	}
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
	public ArrayList<Item> getItems() {
		return items;
	}

	public String getrName() {
		return rName;
	}

	public int getCapacity() {
		return capacity;
	}

}
