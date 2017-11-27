//package unitDatabase;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.sqlite.SQLiteConfig;
//import org.sqlite.SQLiteOpenMode;
//
//public class Database {
//	public static String workingDir = System.getProperty("user.dir");
//	public final static String DATABASE = "\\db\\MySQLiteDB";
//	
//	private String dbFileName;
//	private boolean isOpened = false;
//	private Connection connection;
//	
//	static {
//		try { 
//			Class.forName("org.sqlite.JDBC"); 
//		} catch(Exception e) {
//			e.printStackTrace(); 
//		} 
//	}
//	public Database() {
//		this.dbFileName = "jdbc:sqlite:"+workingDir+DATABASE;
//		System.out.println(workingDir+DATABASE);
//	}
//	public Database(String databaseFileName) { 
//		this.dbFileName = databaseFileName;
//	}
//
//	public boolean open() {
//		try {
//			// 읽기 전용 
//			SQLiteConfig config = new SQLiteConfig(); 
//			config.setOpenMode(SQLiteOpenMode.READWRITE);
//			//this.connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\JSK\\MySQLiteDB\\", config.toProperties());
//			this.connection = DriverManager.getConnection(dbFileName, config.toProperties());
//		} catch(SQLException e) { 
//			e.printStackTrace(); return false; 
//		} 
//		isOpened = true; 
//		return true; 
//	} 
//	public boolean close() { 
//		if(this.isOpened == false) { 
//			return true; 
//		} 
//		try { 
//			this.connection.close(); 
//		} catch(SQLException e) { 
//			e.printStackTrace(); 
//			return false; 
//		} 
//		return true; 
//	}
//	public boolean readMeta(String query) throws SQLException {
//		if(this.isOpened == false) {
//			return false; 
//		} 
//		boolean result = false;  
////		PreparedStatement prep = this.connection.prepareStatement(query); 
////		String query = "SELECT * FROM media WHERE FilePath=? AND CheckSum=?;";
////		prep.setString(1, filePath); 
////		prep.setString(2, hashCode); 
////		ResultSet row = prep.executeQuery(); 
////		if(row.next()) { 
////			row.getString(1);	// index 로 가져오기 
////			row.getString("FileSize");	// field 이름으로 가져오기 
////			result = true; 
////		}
//		PreparedStatement prep = this.connection.prepareStatement("SELECT name FROM sqlite_master \r\n" + 
//				"WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%'\r\n" + 
//				"ORDER BY 1");
//		
//		ResultSet row = prep.executeQuery();
//		while(row.next()) {
//			System.out.println(row.getString("name"));
//		}
//		row.close(); 
//		prep.close(); 
//		return result; 
//	}
//
//}
