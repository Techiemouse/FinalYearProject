import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class DatabaseAccess {
	
	
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost:3306/initialarticlesdata";

		// Database credentials
		static final String USER = "Diana";
		static final String PASS = "ppiytirK";
		
		
		
		public Connection startConnection() {
			Connection conn = null;

			try {
				// STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");

				// STEP 3: Open a connection
				System.out.println("Connecting to a selected database...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				System.out.println("Connected database successfully...");

			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return conn;
		}

		public void closeConnection(Connection conn) {

			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Goodbye!");

		}
}
