package giap.hcmute.vn.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class DBMySqlConnection {
 
    private static final String hostName = "localhost";
    private static final String dbName = "schooldb";
    private static final String userName = "root";
    private static final String password = "hoanggiap1597";
    // jdbc:mysql://hostname:port/dbname
    private static final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
    private static Connection con;
 
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
    	try {
            // 1. Load Driver
            // Class.forName("com.mysql.jdbc.Driver");
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
     
            // 2. Open connection
            con = (Connection) DriverManager.getConnection(connectionURL, userName, password);
     
            
            System.out.println("Connected to MySQL db");
    	} catch (SQLException ex){
    		Logger.getLogger(DBMySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
    	}
    	return (con);
    }
    public static void main(String[] args) {
		try {
			// connnect to database 'testdb'
			Connection conn = DBMySqlConnection.getConnection();
			if (conn == null) {
				System.out.print("Sth wrong!");
			} else {
				System.out.print("Connecting OK");
			}

			// crate statement
			Statement stmt = conn.createStatement();
			// insert ‘GiaoVien'
			stmt.executeUpdate("INSERT INTO student(name, age, grade) "
			+ "VALUES ('Nam', 20, 'D'), ('Tai', 19, 'B')");
			// get data from table ‘GiaoVien'
			ResultSet rs = stmt.executeQuery("SELECT * FROM student");
			// show data
			while (rs.next()) {
			System.out.println(rs.getInt("id") + " " + rs.getString(2)
			+ " " + rs.getString(3));
			}
			conn.close(); // close connection

		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}