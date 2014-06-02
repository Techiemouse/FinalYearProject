package uk.ac.aber.dst1.newsarticleanalysis;

/**
 * @author Diana Silvia Teodorescu
 *
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseSetup {
	ArticleObject artObj = new ArticleObject();
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/articledatabase";
	//static final String DB_URL = "jdbc:mysql://localhost:3306/the_test_schema";
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
			//System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//System.out.println("Connected database successfully...");

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
 * Method that creates the article table
 * @param conn the open connection
 */
	public void createArticleTable(Connection conn) {
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		String createArticleTable = "CREATE TABLE IF NOT EXISTS article" + "("
				+ "id INT NOT NULL AUTO_INCREMENT," + "couchdbid VARCHAR(255), "
				+ " pid VARCHAR(255), " + " title TEXT, "
				+ " text TEXT, " 
				+ " verb_count SMALLINT, "+ " noun_count SMALLINT, " + " word_count SMALLINT, "
				+ " abstract TEXT, " + " search_term VARCHAR(255), "
				+ " publication_id INT, " + " page VARCHAR(20), "+ " thedate DATETIME, "
				+ " PRIMARY KEY (id), "
				+ "UNIQUE KEY (couchdbid),"
				+ "FOREIGN KEY (publication_id) REFERENCES publication(id)"
				+ " )";
		try {
			stmt.executeUpdate(createArticleTable);
			//System.out.println("article table done...");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	/**
	 * Method that creates the publication table
	 * @param conn the open connection
	 */
	public void createPublicationTable(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		String createPublicationTable = "CREATE TABLE IF NOT EXISTS publication"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"
				+ " pid VARCHAR(255), "
				+ " title TEXT, "
				+ " region VARCHAR(255), "
				+ " PRIMARY KEY (id))";
		try {
			stmt.executeUpdate(createPublicationTable);
			System.out.println("publication table done...");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	
	/**
	 * Method that creates the verb table into the database
	 * @param conn the open connection
	 */
	public void createVerbTable(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		String createVerbTable = "CREATE TABLE IF NOT EXISTS verb"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"			
				+ " name VARCHAR(150), "
				+ " PRIMARY KEY (id))";
		try {
			stmt.executeUpdate(createVerbTable);
			System.out.println("verb table done...");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	/**
	 * Method that creates the junction table between verb table and articles
	 * @param conn the open connection
	 */
	public void createArticleVerbs(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		String createVerbTable = "CREATE TABLE IF NOT EXISTS articleverbs"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"	
				+ " article_id INT,"			
				+ " verb_id INT, "
				
				+ " PRIMARY KEY (id),"
				+ " FOREIGN KEY (verb_id) REFERENCES verb(id),"
				+ " FOREIGN KEY (article_id) REFERENCES article(id)"
				+ " )";
		try {
			stmt.executeUpdate(createVerbTable);
			System.out.println("articleverbs table done...");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	/**
	 * Method that creates the noun table in the database
	 * @param conn the open connection
	 */
	public void createNounTable(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		String createNounTable = "CREATE TABLE IF NOT EXISTS noun"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"			
				+ " name VARCHAR(150),"
				+ " PRIMARY KEY (id))";
		try {
			stmt.executeUpdate(createNounTable);
			System.out.println("noun table done...");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	
	/**
	 * Method that creates the junction table between noun table and articles
	 * @param conn the open connection
	 */
	public void createArticleNouns(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		String createArticleNouns = "CREATE TABLE IF NOT EXISTS articlenouns"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"
				+ " article_id INT,"			
				+ " noun_id INT, "
			
				+ " PRIMARY KEY (id),"			
				+ " FOREIGN KEY (noun_id) REFERENCES noun(id),"
				+ " FOREIGN KEY (article_id) REFERENCES article(id)"
				+ " )";
		try {
			stmt.executeUpdate(createArticleNouns);
			System.out.println("articlenouns table done...");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

	/**
	 * Method that creates the domain table in the database
	 * @param conn the open connection
	 */
	public void createDomainTable(Connection conn) throws SQLException {

		Statement stmt = null;
	
			stmt = conn.createStatement();
		
		String createDomainTable = "CREATE TABLE IF NOT EXISTS domain"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"			
				+ " name VARCHAR(150), "
				+ " PRIMARY KEY (id))";
		
			stmt.executeUpdate(createDomainTable);
		

	}
	
	/**
	 * Method that creates the junction table between domain table and articles
	 * @param conn the open connection
	 */
	public void createArticleDomains(Connection conn) throws SQLException {

		Statement stmt = null;

			stmt = conn.createStatement();
		
		String createArticleDomains = "CREATE TABLE IF NOT EXISTS articledomains"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"
				+ " article_id INT,"			
				+ " domain_id INT, "
				+ " PRIMARY KEY (id),"
				+ " FOREIGN KEY (domain_id) REFERENCES domain(id),"
				+ " FOREIGN KEY (article_id) REFERENCES article(id)"
				+ " )";
		
			stmt.executeUpdate(createArticleDomains);
			System.out.println("articledomains table done...");

	}
	
	/**
	 * Method that creates the search term table in the database
	 * @param conn the open connection
	 */
	public void createSearchTermTable(Connection conn) throws SQLException {

		Statement stmt = null;

		stmt = conn.createStatement();
		
		String createSearchTermTable= "CREATE TABLE IF NOT EXISTS searchterm"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"			
				+ " name VARCHAR(150), "
				+ " PRIMARY KEY (id))";

			stmt.executeUpdate(createSearchTermTable);
		

	}
	
	/**
	 * Method that creates the junction table between search term table and articles
	 * @param conn the open connection
	 */
	public void createArticleSearchTerms(Connection conn) throws SQLException {

		Statement stmt = null;
		
		stmt = conn.createStatement();
	
		String createArticleSearchTerms = "CREATE TABLE IF NOT EXISTS articlesearchterms"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"
				+ " article_id INT,"			
				+ " searchterm_id INT, "
				+ " PRIMARY KEY (id),"
				+ " FOREIGN KEY (searchterm_id) REFERENCES searchterm(id),"
				+ " FOREIGN KEY (article_id) REFERENCES article(id)"
				+ " )";
		
			stmt.executeUpdate(createArticleSearchTerms);
	}
	
	/**
	 * Method that insert the information about the articles into the article table
	 * @param artObj - the object that contains the information that needs to be added
	 * @param conn - the open connection to the database
	 * @param searchT - the search term that resulted in finding the specific article
	 * @return returns the ID of the article to be used in the junction tables for the many to many relationship tables
	 * @throws SQLException
	 */
	public int addArticleObject(ArticleObject artObj, Connection conn,
			String searchT) throws SQLException {
		Statement state = null;
		Statement stmt = null;
		Statement duplicate = null;
		int newID=0;
		String query = "SELECT id "
				+ "FROM publication "
				+ "WHERE pid = '"+artObj.getPublicationPID()+"'";
		// the following string is used to check if an article that was previous inserted is found again
		String checkDuplicate = "SELECT id "
				+ "FROM article "
				+ "WHERE couchdbid = '"+artObj.getArticleID()+"' ";
		try {
			stmt = conn.createStatement();
			state= conn.createStatement();
			duplicate = conn.createStatement();
			PreparedStatement statement = null;
			ResultSet qResult = stmt.executeQuery(query);
			ResultSet checkResult = duplicate.executeQuery(checkDuplicate);

			
			
		while (qResult.next()){

				int theID = qResult.getInt("id");
			if (checkResult.next()){
				//if an article previous inserted was found again it means it contains other search words from our list this is why the search_term field will be updated
			  newID= checkResult.getInt("id");
				String concat = "UPDATE article "
						+ "SET search_term = CONCAT(search_term,' "+ searchT + "') "
								+ "WHERE couchdbid = '"+artObj.getArticleID()+"'";
				
				state.executeUpdate(concat);
			
			}
			else{
				String sqlInsert="INSERT INTO article (couchdbid, pid, title, text, verb_count, noun_count, word_count, abstract, search_term, publication_id,  page, thedate)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		
				statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, artObj.getArticleID());
				statement.setString(2, artObj.getPID());
				statement.setString(3, artObj.getArticleTitle());
				statement.setString(4, artObj.getArticleText());
				
				statement.setInt(5, artObj.getVerbCount());
				statement.setInt(6, artObj.getNounCount());
				statement.setInt(7, artObj.getTextWordCount());
				statement.setString(8, artObj.getArticleAbstract());
				statement.setString(9, searchT);
				statement.setInt(10, theID);
				statement.setString(11, artObj.getPage());
				statement.setString(12, artObj.getIssueDate());
				statement.executeUpdate();
				
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
				  newID = rs.getInt(1);
				
				}

		
			}
		}
		
			
		}
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
		}
		return newID;
	
	}
	
	/**
	 * Function required when a mistake was made in the database and the issue dates had to be downloaded and inserted in the right table
	 * @param artObj
	 * @param conn
	 * @param searchT
	 * @throws SQLException
	 */
	public void addArticleDate(ArticleObject artObj, Connection conn,
			String searchT) throws SQLException {
		Statement state = null;
		Statement stmt = null;
		Statement duplicate = null;
		
		// the following string is used to check if an article that was previous inserted is found again
		String checkDuplicate = "SELECT id FROM article "
				+ "WHERE couchdbid = '"+artObj.getArticleID()+"' ";
		try {
			stmt = conn.createStatement();
			state= conn.createStatement();
			duplicate = conn.createStatement();
			
		
			ResultSet checkResult = duplicate.executeQuery(checkDuplicate);

			if (checkResult.next()){
				//if an article previous inserted was found again it means it contains other search words from our list this is why the search_term field will be updated
			
				String concat = "UPDATE article "
						+ "SET thedate = '"+artObj.getIssueDate()+"' "
								+ "WHERE couchdbid = '"+artObj.getArticleID()+"'";
				
				state.executeUpdate(concat);
				
			}
			else{
				System.out.println("No article with that id in tables");
				}
			
		}
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
		}
	
	
	}
/**
 * Method that insert the information about the publication into the publication table
 * @param artObj the information about the publication is held in the object
 * @param conn
 * @throws SQLException
 */
	public void addPublication(ArticleObject artObj, Connection conn) throws SQLException {
		Statement stmt = null;
		Statement state = null;
		String query = "SELECT id FROM publication "
				+ "WHERE pid = '"+artObj.getPublicationPID()+"'";
		
		try {
			stmt = conn.createStatement();
			state = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
	
			if (rs.next()) {
				
			}
			else{
				String addPublication = "INSERT INTO publication (pid, title, region)"
						+ "VALUES ('"
						+ artObj.getPublicationPID()
						+ "','"
						+ artObj.getPublicationTitle()
						+ "','"
						//+ artObj.getIssueDate()
						//+ "','"
						+ artObj.getRegion()
						+ "')"; 
				//+ "ON DUPLICATE KEY UPDATE id=id";
				
					state.executeUpdate(addPublication);
				
			}
		
		}

		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
		}
	}

	/**
	 * Adding the verbs to the verb table and getting their id's to be added into the junction table
	 * @param artObj - the object that contains the verb list that needs to be added
	 * @param conn
	 * @return the array of id's for the records that have been just added
	 * @throws SQLException
	 */
	public ArrayList<Integer> addVerb(ArticleObject artObj, Connection conn) throws SQLException{
		PreparedStatement statement = null;
		Statement stmt = null;
		int newID=0;
		ArrayList<Integer> verbIDs = new ArrayList<Integer>();
	
			stmt = conn.createStatement();

		ArrayList<String> verbList =  artObj.getVerbList();
		
		for (int i=0; i<verbList.size(); i++ ){
			String query = "SELECT id FROM verb WHERE name ='" + artObj.getVerbList().get(i)+"'";
			
			
			ResultSet getResult = stmt.executeQuery(query);
			
			if (getResult.next()){
				
				newID= getResult.getInt("id");
				verbIDs.add(newID);
			
			}
			else{
				String sqlInsert="INSERT INTO verb (name) VALUE(?)";
				statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, artObj.getVerbList().get(i));
				statement.executeUpdate();
				
				ResultSet rs = statement.getGeneratedKeys();	
			
				if (rs.next()) {

					newID = rs.getInt(1);
					
					
				}
				verbIDs.add(newID);



			}
		}

		return verbIDs;

		}
	
	/**
	 * Adding the verbs to the verb table and getting their id's to be added into the junction table
	 * @param artObj - the object that contains the verb list that needs to be added
	 * @param conn
	 * @return the array of id's for the records that have been just added
	 * @throws SQLException
	 */
	
		public ArrayList<Integer> addNoun(ArticleObject artObj, Connection conn) throws SQLException{
			PreparedStatement statement = null;
			Statement stmt = null;
			int newID=0;
			ArrayList<Integer> nounIDs = new ArrayList<Integer>();
			
			
			
				
				stmt = conn.createStatement();
			
			ArrayList<String> nounList =  artObj.getNounList();
			
			for (int i=0; i<nounList.size(); i++ ){
				String query = "SELECT id FROM noun WHERE name ='" + artObj.getNounList().get(i)+"'";
				//System.out.println("the noun "+ artObj.getNounList().get(i));
				
				ResultSet getResult = stmt.executeQuery(query);
				
				if (getResult.next()){
					
					newID= getResult.getInt("id");
					nounIDs.add(newID);
					//System.out.println("duplicate "+ getResult.getInt("id"));
				}
				else{
					String sqlInsert="INSERT INTO noun (name) VALUE(?)";
					statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, artObj.getNounList().get(i));
					statement.executeUpdate();
					
					ResultSet rs = statement.getGeneratedKeys();	
					
					if (rs.next()) {

						newID = rs.getInt(1);
						//System.out.println("the noun ID is " +newID+ " and noun" +artObj.getNounList().get(i));
						
					}
					nounIDs.add(newID);



				}
			}

			return nounIDs;

		}
		
		/**
		 * Method that inserts the noun IDs and the corresponding article ID into the junction table
		 * @param artObj contains the information about the article 
		 * @param articleID the id of the article that contains the specific noun in
		 * @param nounIDs the noun IDs taken from the noun table
		 * @param conn
		 * @throws SQLException
		 */
		public void addArticleNouns(ArticleObject artObj, int articleID, ArrayList<Integer> nounIDs, Connection conn) throws SQLException{
			PreparedStatement statement = null;
			
			Statement stmt = null;
			String queryCount = "SELECT id FROM articlenouns WHERE article_id = '"+articleID+"' HAVING COUNT(article_id)= '"+artObj.getNounCount()+"'";
			stmt = conn.createStatement();
			ResultSet getQueryCount = stmt.executeQuery(queryCount);

			
			if (getQueryCount.next()){
				//int newID= getQueryCount.getInt("id");
			}
			
			else{
			
			String sqlInsert="INSERT INTO articlenouns (article_id, noun_id) VALUE(?,?)";
			statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			
			for (int i=0; i<nounIDs.size(); i++){
			statement.setInt(1, articleID);
			statement.setInt(2, nounIDs.get(i));
		
			statement.executeUpdate();
			}
			}
		}
		
		/**
		 * Method that inserts the verb IDs and the corresponding article ID into the junction table
		 * @param artObj contains the information about the article 
		 * @param articleID the id of the article that contains the specific verb in
		 * @param verbIDs the verb IDs taken from the verb table
		 * @param conn
		 * @throws SQLException
		 */
		public void addArticleVerbs(ArticleObject artObj, int articleID, ArrayList<Integer> verbIDs, Connection conn) throws SQLException{
			PreparedStatement statement = null;
		
			Statement stmt = null;
			String queryCount = "SELECT id FROM articleverbs WHERE article_id = '"+articleID+"' HAVING COUNT(article_id)= '"+artObj.getVerbCount()+"'";
			stmt = conn.createStatement();
			ResultSet getQueryCount = stmt.executeQuery(queryCount);

			
			if (getQueryCount.next()){
			
			}
			
			else{
			for (int i=0; i<verbIDs.size(); i++){
	
			String sqlInsert="INSERT INTO articleverbs (article_id, verb_id) VALUE(?,?)";
			statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, articleID);
			statement.setInt(2, verbIDs.get(i));			
			statement.executeUpdate();
			
			}
			}
		}
			/**
			 * Method that inserts the search term into it's own table
			 * @param searchTerm the value inserted
			 * @param conn
			 * @return -the id that was auto-generated for the search term that was just inserted
			 * @throws SQLException
			 */
		public int addSearchTerm(String searchTerm, Connection conn) throws SQLException{
			PreparedStatement statement = null;
			Statement stmt = null;
			int newID=0;
		
			stmt = conn.createStatement();

				String query = "SELECT id FROM searchterm WHERE name ='" + searchTerm+"'";
			
				ResultSet getResult = stmt.executeQuery(query);
				
				if (getResult.next()){
					
					newID= getResult.getInt("id");
			
				}
				else{
					String sqlInsert="INSERT INTO searchterm (name) VALUE(?)";
					statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, searchTerm);
					statement.executeUpdate();
					
					ResultSet rs = statement.getGeneratedKeys();	
				
					if (rs.next()) {

						newID = rs.getInt(1);
						
						
					}

				}
			
			return newID;

		}
		/**
		 * Method that inserts the search term IDs and the corresponding article ID into the junction table
		 * @param articleID the id of the article that contains the specific search term in
		 * @param searchtermID the id of the search term that was added into the search term table
		 * @param conn
		 * @throws SQLException
		 */
		public void addArticleSearchTerms(int articleID, int searchtermID, Connection conn) throws SQLException{
			PreparedStatement statement = null;
			
			Statement stmt = null;
			String queryCount = "SELECT id FROM articlesearchterms WHERE article_id = '"+articleID+"' AND searchterm_id= '"+searchtermID+"'";
			stmt = conn.createStatement();
			ResultSet getQueryCount = stmt.executeQuery(queryCount);

			
			if (getQueryCount.next()){
				//int newID= getQueryCount.getInt("id");
			}
			
			else{
			String sqlInsert="INSERT INTO articlesearchterms (article_id, searchterm_id) VALUE(?,?)";
			statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, articleID);
			statement.setInt(2, searchtermID);			
			statement.executeUpdate();
			
			
			}
		}
		/**
		 * Method that inserts the list of domains into the domain table one by one
		 * @param conn
		 * @throws SQLException
		 */
		public void addDomain(Connection conn) throws SQLException{
			
			Statement stmt = null;
			PreparedStatement statement = null;
			
			ArrayList<String> domainList = new ArrayList<String>(Arrays.asList(
					"murder", "fraud", "assault", "theft", "no crime","conspiracy","misconduct","property damage"));
			for (int i=0; i<domainList.size(); i++){
			String queryDomain ="SELECT id FROM domain WHERE name= '"+domainList.get(i)+"' ";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(queryDomain);
			if (result.next()){
				//int newID= result.getInt("id");
			}
			
			else{
			String sqlInsert="INSERT INTO domain (name) VALUE(?)";
			
			statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, domainList.get(i));
			statement.executeUpdate();
			}
			}
			
		}

}
