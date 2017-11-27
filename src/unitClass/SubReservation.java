package unitClass;

import java.text.ParseException;

public class SubReservation extends Reservation{
	private int waitNum;
	
	public SubReservation(int reservId, int rid, String date, String sTime, String eTime, int waitNum) throws ParseException{
		super(reservId,rid,date,sTime,eTime);
		this.waitNum = waitNum;
	}
}
