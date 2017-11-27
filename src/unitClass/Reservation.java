package unitClass;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
	private ArrayList<User> users;
	private Room room;
	private Date date;
	private Date sTime;
	private Date eTime;
	
	public Reservation(ArrayList<User> users, Room room, Date date, Date sTime, Date eTime){
		this.users = users;
		this.room = room;
		this.date = date;
		this.sTime = sTime;
		this.eTime = eTime;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	public Room getRoom() {
		return room;
	}
	public Date getDate() {
		return date;
	}
	public Date getsTime() {
		return sTime;
	}
	public Date geteTime() {
		return eTime;
	}
	
	
}
