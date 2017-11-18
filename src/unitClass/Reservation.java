package unitClass;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
	private ArrayList<User> users;
	private Room room;
	private Date date;
	private Date sTime;
	private Date eTime;
	
	
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
