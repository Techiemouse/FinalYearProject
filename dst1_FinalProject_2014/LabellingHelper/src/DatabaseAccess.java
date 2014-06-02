import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DatabaseAccess {


	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/articledatabase";

	// Database credentials
	static final String USER = "Diana";
	static final String PASS = "ppiytirK";


	/**
	 * The function starts the connection with the MySQL database
	 * @return - parameter that saves the connection for use in other methods
	 */
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

			e.printStackTrace();
		}

		return conn;
	}
	/**
	 * The function closes the connection with the MySQL database
	 * @param conn represents the connection that needs to be closed
	 */
	public void closeConnection(Connection conn) {

		try {
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		System.out.println("Goodbye!");

	}

	/**
	 * Method that get's an article from the database, checks if it has been labelled by looking into the articledomains table
	 * @param conn
	 * @param searchTerm
	 * @param randomID
	 * @return
	 * @throws SQLException
	 */
	public int getArticleID(Connection conn, String searchTerm, int randomID) throws SQLException{
		int theID=0;
		Statement statement = null;
		Statement stmt = null;


		String checkIfCompleted = "SELECT a.id FROM article AS a "
				+ "JOIN articledomains AS ad "
				+ "ON a.id=ad.article_id "
				+ "WHERE a.id='"+randomID+"' ";

		stmt = conn.createStatement();
		ResultSet check = stmt.executeQuery(checkIfCompleted);
		if (!check.next()){

			String getID = "SELECT a.id, a.title, a.text, a.abstract FROM article AS a "
					+ "JOIN articlesearchterms AS ast "
					+ "ON a.id=ast.article_id "
					+ "JOIN searchterm AS st "
					+ "ON st.id=ast.searchterm_id "
					+ "WHERE st.name='"+searchTerm+"' "
					+ "HAVING a.id='"+randomID+"'";

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

	/**
	 * Method that inserts information about the labelled article.
	 * @param article_id is the article ID that will be added in the articledomains table
	 * @param input is the ID of the domain that the article_id is tagged as
	 * @param conn the connection to the database
	 * @throws SQLException
	 */
	public void insertDomain (int article_id, int input,Connection conn) throws SQLException{

		PreparedStatement statement = null;						
		String sqlInsert="INSERT INTO articledomains (article_id, domain_id) VALUE(?,?)";
		statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, article_id);
		statement.setInt(2, input);
		statement.executeUpdate();


	}

}
