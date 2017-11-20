package mainInterface;

import java.sql.SQLException;

import unitDatabase.Database;

public class MainProgram {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Database DB = new Database();
		DB.open();
		DB.readMeta(" ");
	}

}
