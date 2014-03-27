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

public class DatabaseSetup {
	ArticleObject artObj = new ArticleObject();
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
	}// end main

	public void closeConnection(Connection conn) {

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Goodbye!");

	}

	public void createArticleTable(Connection conn) {
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String createArticleTable = "CREATE TABLE IF NOT EXISTS article" + "("
				+ "id INT NOT NULL AUTO_INCREMENT," + "couchdbid VARCHAR(35), "
				+ " pid VARCHAR(20), " + " title VARCHAR(255), "
				+ " text TEXT, " + " verb_list TEXT, "+ " noun_list TEXT, "
				+ " verb_count SMALLINT, "+ " noun_count SMALLINT, " + " word_count SMALLINT, "
				+ " abstract VARCHAR(255), " + " search_term VARCHAR(255), "
				+ " publication_id INT, " + " page TINYINT, "
				+ " domain VARCHAR(255), " + " PRIMARY KEY (id), "
				+ "UNIQUE KEY (couchdbid),"
				+ "FOREIGN KEY (publication_id) REFERENCES publication(id)"
				+ " )";
		try {
			stmt.executeUpdate(createArticleTable);
			//System.out.println("article table done...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createPublicationTable(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String createPublicationTable = "CREATE TABLE IF NOT EXISTS publication"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"
				+ " pid VARCHAR(20), "
				+ " title VARCHAR(150), "
				+ " issue_date DATETIME, "
				+ " region VARCHAR(50), "
				+ " PRIMARY KEY (id))";
		try {
			stmt.executeUpdate(createPublicationTable);
			System.out.println("publication table done...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void createVerbTable(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void createArticleVerbs(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createNounTable(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void createArticleNouns(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void createDomainTable(Connection conn) throws SQLException {

		Statement stmt = null;

		
			stmt = conn.createStatement();
		
		String createDomainTable = "CREATE TABLE IF NOT EXISTS domain"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"			
				+ " name VARCHAR(150), "
				+ " PRIMARY KEY (id)),";
		
			stmt.executeUpdate(createDomainTable);
		

	}
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
			System.out.println("articlesearchterms table done...");
	
	}
	public int addArticleObject(ArticleObject artObj, Connection conn,
			String searchT) throws SQLException {
		Statement state = null;
		Statement stmt = null;
		Statement duplicate = null;
		int newID=0;
		String query = "SELECT id FROM publication WHERE pid = '"+artObj.getPublicationPID()+"'";
		// the following string is used to check if an article that was previous inserted is found again
		String checkDuplicate = "SELECT id FROM article WHERE couchdbid = '"+artObj.getArticleID()+"'";
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
				String concat = "UPDATE article SET search_term = CONCAT(search_term,' "+ searchT + "') WHERE couchdbid = '"+artObj.getArticleID()+"'";
				
				state.executeUpdate(concat);
				//System.out.println("++++the duplicate article ID is " +newID+ " and article" +artObj.getArticleID());
			}
			else{
				String sqlInsert="INSERT INTO article (couchdbid, pid, title, text, verb_list, noun_list, verb_count, noun_count, word_count, abstract, search_term, publication_id,  page)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
				statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, artObj.getArticleID());
				statement.setString(2, artObj.getPID());
				statement.setString(3, artObj.getArticleTitle());
				statement.setString(4, artObj.getArticleText());
				statement.setString(5, artObj.attributeList(artObj.getVerbList()));
				statement.setString(6, artObj.attributeList(artObj.getNounList()));
				statement.setInt(7, artObj.getVerbCount());
				statement.setInt(8, artObj.getNounCount());
				statement.setInt(9, artObj.getTextWordCount());
				statement.setString(10, artObj.getArticleAbstract());
				statement.setString(11, searchT);
				statement.setInt(12, theID);
				statement.setInt(13, artObj.getPage());
				statement.executeUpdate();
				
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
				  newID = rs.getInt(1);
				//System.out.println("the article ID is " +newID+ " and article" +artObj.getArticleID());
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

	public void addPublication(ArticleObject artObj, Connection conn) throws SQLException {
		Statement stmt = null;
		Statement state = null;
		String query = "SELECT id FROM publication WHERE pid = '"+artObj.getPublicationPID()+"'";
		
		try {
			stmt = conn.createStatement();
			state = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			//System.out.println("adding public "+artObj.getPublicationPID());
			if (rs.next()) {
				//System.out.println("adding public ");
			}
			else{
				String addPublication = "INSERT INTO publication (pid, title, issue_date, region)"
						+ "VALUES ('"
						+ artObj.getPublicationPID()
						+ "','"
						+ artObj.getPublicationTitle()
						+ "','"
						+ artObj.getIssueDate()
						+ "','"
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
	/*
	 * creates the id then adds to junction table, article creates id and adds to junction table, 
	 * put new verb in table - first need to check that name of verb wasn't there already there using verb.name==new verb
	 * if it is do nothing in the verb table but put into article/verbs only if article id not in the table already in combination with same verb id 
	 * 
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
			//System.out.println("the verb "+ artObj.getVerbList().get(i));
			
			ResultSet getResult = stmt.executeQuery(query);
			
			if (getResult.next()){
				
				newID= getResult.getInt("id");
				verbIDs.add(newID);
				//System.out.println("duplicate "+ getResult.getInt("id"));
			}
			else{
				String sqlInsert="INSERT INTO verb (name) VALUE(?)";
				statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, artObj.getVerbList().get(i));
				statement.executeUpdate();
				
				ResultSet rs = statement.getGeneratedKeys();	
			
				if (rs.next()) {

					newID = rs.getInt(1);
					System.out.println("the noun ID is " +newID+ " and verb" +artObj.getVerbList().get(i));
					
				}
				verbIDs.add(newID);



			}
		}

		return verbIDs;

		}
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
						System.out.println("the noun ID is " +newID+ " and noun" +artObj.getNounList().get(i));
						
					}
					nounIDs.add(newID);



				}
			}

			return nounIDs;

		}
		public void addArticleNouns(ArticleObject artObj, int articleID, ArrayList<Integer> nounIDs, Connection conn) throws SQLException{
			PreparedStatement statement = null;
			int newID=0;
			Statement stmt = null;
			String queryCount = "SELECT id FROM articlenouns WHERE article_id = '"+articleID+"' HAVING COUNT(article_id)= '"+artObj.getNounCount()+"'";
			stmt = conn.createStatement();
			ResultSet getQueryCount = stmt.executeQuery(queryCount);

			
			if (getQueryCount.next()){
				newID= getQueryCount.getInt("id");
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
		
		public void addArticleVerbs(ArticleObject artObj, int articleID, ArrayList<Integer> verbIDs, Connection conn) throws SQLException{
			PreparedStatement statement = null;
			int newID=0;
			Statement stmt = null;
			String queryCount = "SELECT id FROM articleverbs WHERE article_id = '"+articleID+"' HAVING COUNT(article_id)= '"+artObj.getVerbCount()+"'";
			stmt = conn.createStatement();
			ResultSet getQueryCount = stmt.executeQuery(queryCount);

			
			if (getQueryCount.next()){
				newID= getQueryCount.getInt("id");
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
						System.out.println("the noun ID is " +newID+ " and noun" + searchTerm);
						
					}

				}
			
			return newID;

		}
		
		public void addArticleSearchTerms(int articleID, int searchtermID, Connection conn) throws SQLException{
			PreparedStatement statement = null;
			int newID=0;
			Statement stmt = null;
			String queryCount = "SELECT id FROM articlesearchterms WHERE article_id = '"+articleID+"' AND searchterm_id= '"+searchtermID+"'";
			stmt = conn.createStatement();
			ResultSet getQueryCount = stmt.executeQuery(queryCount);

			
			if (getQueryCount.next()){
				newID= getQueryCount.getInt("id");
			}
			
			else{
			String sqlInsert="INSERT INTO articlesearchterms (article_id, searchterm_id) VALUE(?,?)";
			statement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, articleID);
			statement.setInt(2, searchtermID);			
			statement.executeUpdate();
			
			
			}
		}

}
