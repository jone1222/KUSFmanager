package unitClass;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import unitDatabase.Database;

public class Reservation {
	public static SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat SDF_TIME = new SimpleDateFormat("hh:mm");

	private int reservID;
	private int roomID;
	private Date date;
	private Date sTime;
	private Date eTime;
	
	public Reservation(int reservID,int rid, String date, String sTime, String eTime) throws ParseException{
		this.reservID = reservID;
		this.roomID = rid;
		this.date = SDF_DATE.parse(date);
		this.sTime = SDF_TIME.parse(sTime);
		this.eTime = SDF_TIME.parse(eTime);
	}
	public int getreservID() {
		return this.reservID;
	}
	public Room getRoom() {
		Database DB = new Database();
		DB.open();
		Room room = null;
		try {
			room = DB.findRoomById(this.roomID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return room;
	}
	public int getroomID() {
		return this.roomID;
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
