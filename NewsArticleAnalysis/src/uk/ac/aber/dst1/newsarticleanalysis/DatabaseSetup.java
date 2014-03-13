package uk.ac.aber.dst1.newsarticleanalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
	ArticleObject artObj =new ArticleObject();
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/initialarticlesdata";

	   //  Database credentials
	   static final String USER = "Diana";
	   static final String PASS = "ppiytirK";
	   
	   public Connection startConnection() {
	   Connection conn = null;
	  
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to a selected database...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      System.out.println("Connected database successfully...");
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating table in given database...");
	      
	      
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	      
	   return conn;
	}//end main

public void closeConnection(Connection conn){
	
           try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
  System.out.println("Goodbye!");
	
}

public void createArticleTable(Connection conn){
	 Statement stmt = null;

	try {
		stmt = conn.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	String createArticleTable = "CREATE TABLE ARTICLE " +
            "(article_id VARCHAR(255) not NULL, " +
            " title VARCHAR(255), " + 
            " text VARCHAR(255), " +           
            " verb_list VARCHAR(255), " + 
            " verb_count VARCHAR(255), " +
            " word_count VARCHAR(255), " +
            " abstract VARCHAR(255), " + 
            " issue_date DATE, " +            
            " region VARCHAR(255), " + 
            " search_terms VARCHAR(255), " + 
            " pid VARCHAR(255), " + 
            " page INTEGER, " + 
            " PRIMARY KEY ( article_id ))"; 
try {
	stmt.executeUpdate(createArticleTable);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	
}

public void addArticleObject(ArticleObject artObj, Connection conn){
	Statement stmt = null;

	try {
		stmt = conn.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   String addObject = "INSERT INTO article (article_id,title, text, issue_date, verb_list, verb_count, word_list, search_terms, pid, page)" +
   	        "VALUES ("+artObj.getArticleID()+","+artObj.getArticleTitle()+","+artObj.getArticleText()+")";
	   try {
		stmt.executeUpdate(addObject);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}
