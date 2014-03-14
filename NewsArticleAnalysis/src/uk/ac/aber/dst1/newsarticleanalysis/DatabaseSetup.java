package uk.ac.aber.dst1.newsarticleanalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

			// STEP 4: Execute a query
			System.out.println("Creating table in given database...");

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
		String createArticleTable = "CREATE TABLE IF NOT EXISTS article "
				+ "(id VARCHAR(35) not NULL, " + " pid VARCHAR(20), "
				+ " title VARCHAR(255), " + " text TEXT, "
				+ " verb_list TEXT, " + " verb_count SMALLINT, "
				+ " word_count SMALLINT, " + " abstract VARCHAR(255), "
				+ " search_term VARCHAR(255), "
				+ " publication_pid VARCHAR(20), " + " page TINYINT, "
				+ " domain VARCHAR(255), " + " PRIMARY KEY ( article_id ))"
				+ "FOREIGN KEY (publication_id) REFERENCES publication(id)";
		try {
			stmt.executeUpdate(createArticleTable);
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
				+ "(id VARCHAR(20), "
				+ " title VARCHAR(150), "
				+ " issue_date DATETIME, "
				+ " region VARCHAR(50), "
				+ " PRIMARY KEY (id ))";
		try {
			stmt.executeUpdate(createPublicationTable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addArticleObject(ArticleObject artObj, Connection conn,
			String searchTerm) {
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String addObject = "INSERT INTO article (id, pid, title, text, verb_list, verb_count, word_count, abstract, search_term, publication_id, page)"
				+ "VALUES ("
				+ artObj.getArticleID()
				+ ","
				+ artObj.getpID()
				+ ","
				+ artObj.getArticleTitle()
				+ ","
				+ artObj.getArticleText()
				+ ","
				+ artObj.getVerbList()
				+ ","
				+ artObj.getVerbCount()
				+ ","
				+ artObj.getTextWordCount()
				+ ","
				+ artObj.getArticleAbstract()
				+ ","
				+ searchTerm
				+ ","
				+ artObj.getPublicationPID()
				+ ","
				+ artObj.getPage()
				+ ")"
				+ "ON DUPLICATE KEY UPDATE SET search_term = CONCAT(search_term, "
				+ searchTerm + ")";
		try {
			stmt.executeUpdate(addObject);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addPublication(ArticleObject artObj, Connection conn) {
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String addPublication = "INSERT INTO publication (id, title, issue_date, region)"
				+ "VALUES ("
				+ artObj.getPublicationPID()
				+ ","
				+ artObj.getPublicationTitle()
				+ ","
				+ artObj.getIssueDate()
				+ "," + artObj.getRegion() + ")";
		try {
			stmt.executeUpdate(addPublication);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
