package unitDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import unitClass.Item;
import unitClass.Reservation;
import unitClass.Room;
import unitClass.User;
public class Database{
	public static String workingDir = System.getProperty("user.dir");
	public final static String DATABASE = "\\db\\MySQLiteDB";
	public final static int MAXIMUM_RESERVATION = 3;
	
	
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
	public boolean makeReservation( ArrayList<User> users,int rid,String date, String sTime,String eTime,int waitNum ){
		if(this.isOpened == false) {
			return false;
		}
		try {
			if(!findDuplicateReserv(date,sTime,eTime)) {
				if(checkReservationConstraint(users)) {
					//INSERT INTO Reservation
					String query = "INSERT INTO Reservation(rid,r_date,sTime,eTime) VALUES("+rid+",'"+date+"','"+sTime+"','"+eTime+"')";
					PreparedStatement prep = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					prep.executeUpdate();
					
					//INSERT INTO ReservedUser
					try(ResultSet generatedKeys = prep.getGeneratedKeys()) {
						if(generatedKeys.next()) {
							for(int i = 0 ; i < users.size(); i++) {
								String user_query = "INSERT INTO ReservedUser VALUES("+generatedKeys.getInt(1)+",'"+users.get(i).getsid()+"')";
								PreparedStatement user_prep = this.connection.prepareStatement(user_query);
								user_prep.executeUpdate();
							}
						}						
						return true;
					}
					
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean checkReservationConstraint(ArrayList<User> u_list) throws SQLException {
		for(int i = 0 ; i < u_list.size(); i++) {
			if(getReserveByUser(u_list.get(i)).size() >= MAXIMUM_RESERVATION)
				return false;
		}
		return true;
	}
	public boolean checkReservationConstraint(User u) throws SQLException {
		if(getReserveByUser(u).size() >= MAXIMUM_RESERVATION)
			return false;
				
		return true;
	}
	public boolean findDuplicateReserv(String r_date, String sTime, String eTime) throws SQLException{
		if(this.isOpened == false) {
			return true;
		}
		String find_duplicates = "SELECT * FROM Reservation WHERE r_date='"+r_date+"' and ( (sTime > '"+sTime+"' and sTime < '"+eTime+"') or (eTime > '"+sTime+"' and eTime < '"+eTime+"') )";
		PreparedStatement prep = this.connection.prepareStatement(find_duplicates);
		
		ResultSet row = prep.executeQuery();
		if(row.next()) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public ArrayList<Reservation> getReserveByUser(User user) throws SQLException{
		if(this.isOpened == false) {
			return null; 
		}
		
		ArrayList<Reservation> Reservation_list = new ArrayList<>();
		String reserve_query = "SELECT Reservation.reservId,Reservation.rid,Reservation.r_date,Reservation.sTime,Reservation.eTime,Reservation.waitNum FROM Reservation,ReservedUser WHERE ReservedUser.sid='"+user.getsid()+"' GROUP BY Reservation.reservID";
		PreparedStatement reserve_prep = this.connection.prepareStatement(reserve_query);
		
		ResultSet row = reserve_prep.executeQuery();
		
		while(row.next()) {			
			Reservation_list.add(
					new Reservation(
							row.getInt(1),row.getInt(2), row.getString(3),row.getString(4),row.getString(5)
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
			Reservation_list.add(
					new Reservation(
							row.getInt(1),row.getInt(2), row.getString(3),row.getString(4),row.getString(5)
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
		
		String query = "SELECT pw,name FROM User WHERE sid = '"+sid+"'";
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

	public boolean checkOnLogin(String id, String pw) throws SQLException{
		if(this.isOpened == false)
			return false;
		
		String query = "SELECT * FROM User WHERE sid='"+id+"' and pw='"+pw+"'";
		PreparedStatement prep = this.connection.prepareStatement(query);
		ResultSet row = prep.executeQuery();
		
		if(row.next())
			return true;
		
		return false;
	}
	public boolean isExistUser(String id) throws SQLException{
		if(this.isOpened == false)
			return false;
		
		String query = "SELECT * FROM User Where sid ='"+id+"'";
		PreparedStatement prep = this.connection.prepareStatement(query);
		ResultSet row = prep.executeQuery();
		
		if(row.next())
			return true;
		return false;
	}
	
	public ArrayList<User> getUsersOfReservation(int ReservationID) throws SQLException{
		ArrayList<User> user_list = new ArrayList<>();
		
		if(this.isOpened == false)
			return user_list;
		
		String query = "SELECT sid FROM ReservedUser WHERE reservID = ?";
		PreparedStatement prep = this.connection.prepareStatement(query);
		prep.setInt(1, ReservationID);
		
		ResultSet row = prep.executeQuery();
		
		while(row.next()) {
			user_list.add(findUserById(row.getString(1)));
		}
		
		return user_list;
	}

}
