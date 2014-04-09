import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




public class DatabaseAccess {
	
	
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost:3306/articledatabase";

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
		public int getArticleID(Connection conn, String searchTerm, int randomID) throws SQLException{
			int theID=0;
			Statement statement = null;
			Statement stmt = null;
			
			//System.out.println("checking ....."+randomID);	
			String checkIfCompleted = "SELECT a.id FROM article AS a "
					+ "JOIN articledomains AS ad "
					+ "ON a.id=ad.article_id "
					+ "WHERE a.id='"+randomID+"' ";
							//+ "AND ad.domain_id='0'";
			stmt = conn.createStatement();
			ResultSet check = stmt.executeQuery(checkIfCompleted);
			if (!check.next()){
				//System.out.println("No domain for this ID"+randomID);	
			String getID = "SELECT a.id, a.title, a.text, a.abstract FROM article AS a "
					+ "JOIN articlesearchterms AS ast "
					+ "ON a.id=ast.article_id "
					+ "JOIN searchterm AS st "
					+ "ON st.id=ast.searchterm_id "
					+ "WHERE st.name='"+searchTerm+"' "
					+ "HAVING a.id='"+randomID+"'";
					//+ "LIMIT 1";
					//+ "AND searchterm.name ='"+searchTerm+"'";
			statement = conn.createStatement();
			ResultSet getResult = statement.executeQuery(getID);
			while (getResult.next()){
				theID =getResult.getInt("a.id");
				String text =getResult.getString("a.text");
				String title = getResult.getString("a.title");
				String abst = getResult.getString("a.abstract");
			System.out.println("id:*** "+theID+" abstract****"+abst+"***article text: "+text+"***article title: "+title);
			}
			}
			else{
				System.out.println("Domain exists for article "+randomID);
				
			}
			return theID;
		}
		public ArrayList<String> getArticle(Connection conn) throws SQLException{
			Statement statement = null;
			ArrayList<String> articleInfo = new ArrayList<String>();
			
			String getArticle = "SELECT id, text, title FROM article WHERE word_count > 10";
					//+ "id.article=article_id.articlesearchterms AND id.searchterm = searchterm_id.articlesearchterms";
			statement = conn.createStatement();
			ResultSet getResult = statement.executeQuery(getArticle);
			while (getResult.next()){
			int theID = getResult.getInt("id");
			String text =getResult.getString("text");
			String title = getResult.getString("title");
			//String searchTerm = getResult.getString("name");
			
			String id =Integer.toString(theID);
			
			articleInfo.add(id);
			articleInfo.add(title);
			articleInfo.add(text);
			}
			return articleInfo;
		}
		
		public void insertDomain (int article_id, int input,Connection conn) throws SQLException{
			PreparedStatement statement = null;
			
			
			String sqlInsert="INSERT INTO articledomains (article_id, domain_id) VALUE(?,?)";
			statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, article_id);
			statement.setInt(2, input);
			statement.executeUpdate();
			
			
		}
		
}
