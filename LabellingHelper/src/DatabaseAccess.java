import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




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
		
		public ArrayList<String> getArticle(Connection conn) throws SQLException{
			Statement statement = null;
			ArrayList<String> articleInfo = new ArrayList<String>();
			String getArticle = "SELECT id, text, title FROM article WHERE wourd_count > 10";
			statement = conn.createStatement();
			ResultSet getResult = statement.executeQuery(getArticle);
			int theID = getResult.getInt("id");
			String text =getResult.getString("text");
			String title = getResult.getString("title");
			
			String id =Integer.toString(theID);
			
			articleInfo.add(id);
			articleInfo.add(title);
			articleInfo.add(text);
			
			return articleInfo;
		}
		
		public void insertDomain (int input,Connection conn) throws SQLException{
			PreparedStatement statement = null;
			
			
			String sqlInsert="INSERT INTO articledomains (name) VALUE(?)";
			statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, input);
			statement.executeUpdate();
			
			
		}
		
}
