package unitDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import unitClass.Item;
import unitClass.Reservation;
import unitClass.Room;
import unitClass.User;
public class Database {
	public static String workingDir = System.getProperty("user.dir");
	public final static String DATABASE = "\\db\\MySQLiteDB";
	
	private String dbFileName;
	private boolean isOpened = false;
	private Connection connection;
	
	static {
		try { 
			Class.forName("org.sqlite.JDBC"); 
		} catch(Exception e) {
			e.printStackTrace(); 
		} 
	}
	public Database() {
		this.dbFileName = "jdbc:sqlite:"+workingDir+DATABASE;
		System.out.println(workingDir+DATABASE);
	}
	public Database(String databaseFileName) { 
		this.dbFileName = databaseFileName;
	}

	public boolean open() {
		try {
			// 읽기 전용 
			SQLiteConfig config = new SQLiteConfig(); 
			config.setOpenMode(SQLiteOpenMode.READWRITE);
			//this.connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\JSK\\MySQLiteDB\\", config.toProperties());
			this.connection = DriverManager.getConnection(dbFileName, config.toProperties());
		} catch(SQLException e) { 
			e.printStackTrace(); return false; 
		} 
		isOpened = true; 
		return true; 
	} 
	public boolean close() { 
		if(this.isOpened == false) { 
			return true; 
		} 
		try { 
			this.connection.close(); 
		} catch(SQLException e) { 
			e.printStackTrace(); 
			return false; 
		} 
		return true; 
	}
	public boolean readMeta(String query) throws SQLException {
		if(this.isOpened == false) {
			return false; 
		} 
		boolean result = false;  
//		PreparedStatement prep = this.connection.prepareStatement(query); 
//		String query = "SELECT * FROM media WHERE FilePath=? AND CheckSum=?;";
//		prep.setString(1, filePath); 
//		prep.setString(2, hashCode); 
//		ResultSet row = prep.executeQuery(); 
//		if(row.next()) { 
//			row.getString(1);	// index 로 가져오기 
//			row.getString("FileSize");	// field 이름으로 가져오기 
//			result = true; 
//		}
		PreparedStatement prep = this.connection.prepareStatement("SELECT name FROM sqlite_master \r\n" + 
				"WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%'\r\n" + 
				"ORDER BY 1");
		
		ResultSet row = prep.executeQuery();
		while(row.next()) {
			System.out.println(row.getString("name"));
		}
		row.close(); 
		prep.close(); 
		return result; 
	}
	public ArrayList<User> getAllUsers() throws SQLException{
		if(this.isOpened == false) {
			return null; 
		}
		ArrayList<User> users = new ArrayList<>();
		String query = "SELECT * FROM User";
		PreparedStatement prep = this.connection.prepareStatement(query);
		
		ResultSet row = prep.executeQuery();
		
		while(row.next()) {
			users.add(new User(row.getString(1),row.getString(2),row.getString(3)));
			System.out.println(row.getString(1));
		}
		
		return users;
	}

	public ArrayList<Item> getRoomItems(String roomName) throws SQLException{
		if(this.isOpened == false) {
			return null; 
		}
		ArrayList<Item> Items = new ArrayList<>();
		String query = "SELECT Item.iName,Item.Image,Item.description,Item.isAvailable FROM ItemInRoom,Room,Item WHERE rName='"+roomName+"' and ItemInRoom.rid=Room.rid and Item.iName = ItemInRoom.iName";
		PreparedStatement prep = this.connection.prepareStatement(query);
		
		ResultSet row = prep.executeQuery();
		
		while(row.next()) {
			Items.add(new Item(row.getString(1),row.getString(2),row.getString(3),row.getBoolean(4)));
			System.out.println(row.getString(1));
		}
		
		return Items;
	}
	public boolean makeReservation( ArrayList<User> users,int rid,String date, String sTime,String eTime,String waitNum ) {
		if(this.isOpened == false) {
			return false;
		}
		try {
			if(!findDuplicateReserv(date,sTime,eTime)) {
				String query = "INSERT INTO Reservation(rid,r_date,sTime,eTime) VALUES("+rid+",'"+date+"','"+sTime+"','"+eTime+"')";
				PreparedStatement prep = this.connection.prepareStatement(query);
				prep.executeUpdate();
				
				//String reservId_query = "SELECT reservId FROM Reservation WHERE rid="+rid+" and r_date='"+date+"' and sTime="
				
				return true;
			}
		}catch(SQLException e) {
			return false;
		}
		return false;
	}
	public boolean findDuplicateReserv(String r_date, String sTime, String eTime) throws SQLException{
		if(this.isOpened == false) {
			return true;
		}
		String find_duplicates = "SELECT * FROM Reservation WHERE r_date='"+r_date+"' and ( (sTime between '"+sTime+"' and '"+eTime+"') or (eTime between '"+sTime+"' and '"+eTime+"') )";
		PreparedStatement prep = this.connection.prepareStatement(find_duplicates);
		
		ResultSet row = prep.executeQuery();
		if(row.next()) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public ArrayList<Reservation> getReserveByUser(User user) throws SQLException,ParseException{
		if(this.isOpened == false) {
			return null; 
		}
		
		ArrayList<Reservation> Reservation_list = new ArrayList<>();
		String reserve_query = "SELECT Reservation.reservId,Reservation.rid,Reservation.r_date,Reservation.sTime,Reservation.eTime,Reservation.waitNum FROM Reservation,ReservedUser WHERE ReservedUser.sid='"+user.getsid()+"'";
		PreparedStatement reserve_prep = this.connection.prepareStatement(reserve_query);
		
		ResultSet row = reserve_prep.executeQuery();
		
		while(row.next()) {
			int rid = row.getInt(1);
			
			String user_query ="SELECT sid FROM ReservedUser WHERE rid='"+rid+"'";
			PreparedStatement user_prep = this.connection.prepareStatement(user_query);
			
			ResultSet user_row = user_prep.executeQuery();
			ArrayList<User> users = new ArrayList<>();
			
			while(user_row.next()) {
				users.add(findUserById(user_row.getString(1)));
			}
			SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm");
			
			Reservation_list.add(
					new Reservation(
							users, findRoomById(row.getInt(2)), sdf_date.parse(row.getString(3)), sdf_time.parse(row.getString(4)), sdf_time.parse(row.getString(5))
							)
					);
		}
		
		return Reservation_list;
	}
	public ArrayList<Reservation> getReserveByDate(int roomID,String date) throws SQLException,ParseException{
		
		ArrayList<Reservation> Reservation_list = new ArrayList<>();
		String reserve_query = "SELECT * FROM Reservation WHERE Reservation.r_date='"+date+"' and rid="+roomID;
		PreparedStatement reserve_prep = this.connection.prepareStatement(reserve_query);
		
		ResultSet row = reserve_prep.executeQuery();
		
		while(row.next()) {
			int reservId = row.getInt(1);
			
			String user_query ="SELECT sid FROM ReservedUser WHERE reservId='"+reservId+"'";
			PreparedStatement user_prep = this.connection.prepareStatement(user_query);
			
			ResultSet user_row = user_prep.executeQuery();
			ArrayList<User> users = new ArrayList<>();
			
			while(user_row.next()) {
				users.add(findUserById(user_row.getString(1)));
			}
			SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm");
			
			Reservation_list.add(
					new Reservation(
							users, findRoomById(roomID), sdf_date.parse(row.getString(3)), sdf_time.parse(row.getString(4)), sdf_time.parse(row.getString(5))
							)
					);
		}
		
		return Reservation_list;
	}
	public User findUserById(String sid) throws SQLException{
		User user = null;
		
		if(this.isOpened == false) {
			return null;
		}
		
		String query = "SELECT name,pw FROM User WHERE sid = '"+sid+"'";
		PreparedStatement prep = this.connection.prepareStatement(query);
		
		ResultSet row = prep.executeQuery();
		
		while(row.next()) {
			user = new User(sid,row.getString(1),row.getString(2));
		}
		
		return user;
	}
	public Room findRoomById(int rid) throws SQLException{
		Room room = null;
		
		if(this.isOpened == false) {
			return null;
		}
		
		String query = "SELECT rName,capacity FROM Room WHERE rid = '"+rid+"'";
		PreparedStatement prep = this.connection.prepareStatement(query);
		
		ResultSet row = prep.executeQuery();
		
		while(row.next()) {
			room = new Room(rid,row.getString(1),row.getInt(2));
		}
		
		return room;
	}


}
