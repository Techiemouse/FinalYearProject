package uk.ac.aber.dst1.newsarticleanalysis;

/**
 * @author Diana Silvia Teodorescu
 *
 */


import java.sql.Connection;
import java.sql.DriverManager;
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
				+ " article_id INT,"			
				+ " verb_id INT, "
				+ " PRIMARY KEY (article_id, verb_id),"
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
				+ " article_id INT,"			
				+ " noun_id INT, "
				+ " PRIMARY KEY (article_id, noun_id),"			
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


	public void createDomainTable(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String createDomainTable = "CREATE TABLE IF NOT EXISTS domain"
				+ "("
				+ " id INT NOT NULL AUTO_INCREMENT,"			
				+ " name VARCHAR(150), "
				+ " PRIMARY KEY (id)),";
		try {
			stmt.executeUpdate(createDomainTable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void createArticleDomains(Connection conn) {

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String createArticleDomains = "CREATE TABLE IF NOT EXISTS articledomains"
				+ "("
				+ " article_id INT NOT NULL AUTO_INCREMENT,"			
				+ " domain_id VARCHAR(150), "
				+ " PRIMARY KEY (article_id, domain_id),"
				+ " FOREIGN KEY (domain_id) REFERENCES domain(id),"
				+ " FOREIGN KEY (article_id) REFERENCES article(id)"
				+ " )";
		try {
			stmt.executeUpdate(createArticleDomains);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int addArticleObject(ArticleObject artObj, Connection conn,
			String searchT) throws SQLException {
		Statement state = null;
		Statement stmt = null;
		Statement duplicate = null;
		int theLastID=0;
		String query = "SELECT id FROM publication WHERE pid = '"+artObj.getPublicationPID()+"'";
		String checkDuplicate = "SELECT id FROM article WHERE couchdbid = '"+artObj.getArticleID()+"'";
		try {
			stmt = conn.createStatement();
			state= conn.createStatement();
			duplicate = conn.createStatement();
			ResultSet qResult = stmt.executeQuery(query);
			ResultSet checkResult = duplicate.executeQuery(checkDuplicate);

			//System.out.println("adding article "+artObj.getArticleID());
			
		while (qResult.next()){

				int theID = qResult.getInt("id");
			if (checkResult.next()){
				System.out.println("the duplicate id is"+artObj.getArticleID()+" the pub id " +theID);
				String concat = "UPDATE article SET search_term = CONCAT(search_term,' "+ searchT + "') WHERE couchdbid = '"+artObj.getArticleID()+"'";
				state.executeUpdate(concat);
				
			}
			else{
				
		System.out.println("adding article with public " +theID);
		String addObject = "INSERT INTO article (couchdbid, pid, title, text, verb_list, noun_list, verb_count, noun_count, word_count, abstract, search_term, publication_id,  page)"
				+ "VALUES ('"
				+ artObj.getArticleID()
				+ "','"
				+ artObj.getPID()
				+ "','"
				+ artObj.getArticleTitle()
				+ "','"
				+ artObj.getArticleText()
				+ "','"
				+ artObj.attributeList(artObj.getVerbList())
				+ "','"
				+ artObj.attributeList(artObj.getNounList())
				+ "','"
				+ artObj.getVerbCount()
				+ "','"
				+ artObj.getNounCount()
				+ "','"
				+ artObj.getTextWordCount()
				+ "','"
				+ artObj.getArticleAbstract()
				+ "','"
				+ searchT
				+ "','"
				+ theID
				//+ artObj.getPublicationPID()
				+ "','"
				+ artObj.getPage()
				+ "')";
				//+ "ON DUPLICATE KEY UPDATE search_term = CONCAT(search_term,' "
				//+ searchT + "')"
				//+"SELECT LAST_INSERT_ID ";

		state.executeUpdate(addObject);
		
		
			}
		}
		
			
		}
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
		}
		return theLastID;
	
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
				try {
					state.executeUpdate(addPublication);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	public void addVerb(ArticleObject artObj, Connection conn){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> verbList =  artObj.getVerbList();
		for (int i=0; i<verbList.size(); i++ ){
			
			String addVerb = "INSER INTO verb (name)"
					+ "VALUES ('"
					+ artObj.getVerbList().get(i)
					+"')";
			try {
				stmt.executeUpdate(addVerb);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		}
		public void addNoun(ArticleObject artObj, Connection conn){
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<String> nounList =  artObj.getNounList();
			for (int i=0; i<nounList.size(); i++ ){
				
				String addNoun = "INSER INTO noun (name)"
						+ "VALUES ('"
						+ artObj.getNounList().get(i)
						+"')";
				try {
					stmt.executeUpdate(addNoun);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		
		
	}

}
