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
		String createArticleTable = "CREATE TABLE article "
				+ "(article_id VARCHAR(255) not NULL, "
				+ " title VARCHAR(255), " + " text VARCHAR(255), "
				+ " verb_list VARCHAR(255), " + " verb_count VARCHAR(255), "
				+ " word_count VARCHAR(255), " + " abstract VARCHAR(255), "
				+ " issue_date DATE, " + " search_term VARCHAR(255), "
				+ " pid VARCHAR(255), " + " page INTEGER, "+ " domain VARCHAR(255), "
				+ " PRIMARY KEY ( article_id ))";
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
		String createPublicationTable = "CREATE TABLE publication"
				+ "(pid VARCHAR(255) not NULL, "
				+ " publication_id VARCHAR(255), " + " title VARCHAR(255), "
				+ " region VARCHAR(255), " + " PRIMARY KEY (pid ))";
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
		String addObject = "INSERT INTO article (article_id, title, text, verb_list, verb_count, word_count, abstract, issue_date, search_term, pid, page)"
				+ "VALUES ("
				+ artObj.getArticleID()
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
				+ artObj.getIssueDate()
				+ ","
				+ searchTerm
				+ ","
				+ artObj.getPID()
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

}
