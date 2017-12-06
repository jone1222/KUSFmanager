package unitClass;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import unitDatabase.Database;

public class Reservation {
	public static SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat SDF_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private int reservID;
	private int roomID;
	private Date date;
	private Date sTime;
	private Date eTime;
	
	public Reservation(int reservID,int rid, String date, String sTime, String eTime){
		this.reservID = reservID;
		this.roomID = rid;
		try {
			this.date = SDF_DATE.parse(date);
			this.sTime = SDF_ALL.parse(date+" "+sTime);
			this.eTime = SDF_ALL.parse(date+" "+eTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		DB.close();
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
	
	public String toString() {
		Database DB = new Database();
		DB.open();

		ArrayList<User> users = new ArrayList<>();
		try {
			users = DB.getUsersOfReservation(this.reservID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String str = "";
		str += "일시 : "+this.SDF_DATE.format(this.date)+"\n\n";
		str += "시작 시간  : "+this.SDF_TIME.format(this.sTime)+"\n\n";
		str += "종료 시간 : "+this.SDF_TIME.format(this.eTime)+"\n\n";
		if(!users.isEmpty()) {
			 str += "대표자 : "+users.get(0).getname()+"\n\n";
			 for(int i = 1 ; i < users.size(); i++) {
				 str += "구성원 :   ";
				 str += users.get(i).getname()+"  ";
			 }
		}
		DB.close();
		return str;
	}
	
}
