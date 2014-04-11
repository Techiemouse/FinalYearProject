package uk.ac.aber.dst1.newsarticleanalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MLTable {
	
	ArticleObject artObj = new ArticleObject();
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
			//System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			//System.out.println("Connected database successfully...");

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
	public ArrayList<String> getNouns(Connection conn) throws SQLException{
	
		ArrayList<String> nounList= new ArrayList<String>();
		Statement stmt = null;
		String newNoun="";
		String getNouns = "SELECT COUNT(*), noun.name FROM articledatabase.articlenouns AS av JOIN articledatabase.articledomains AS ad ON ad.article_id=av.article_id JOIN articledatabase.noun ON av.noun_id=noun.id GROUP BY av.noun_id HAVING COUNT(*)>= 5";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getNouns);
		
		while (getResult.next()){
			
			newNoun= getResult.getString("noun.name");
			nounList.add(newNoun);
			//System.out.println("duplicate "+ getResult.getInt("id"));
		}
		
		System.out.println("list of nouns has: "+ nounList.size());
		return nounList;
		
	}
	public ArrayList<String> getVerbs(Connection conn) throws SQLException{
		
		ArrayList<String> verbList= new ArrayList<String>();
		Statement stmt = null;
		String newVerb="";
		String getVerbs = "SELECT COUNT(*), verb.name FROM articledatabase.articleverbs AS av JOIN articledatabase.articledomains AS ad ON ad.article_id=av.article_id JOIN articledatabase.verb ON av.verb_id=verb.id GROUP BY av.verb_id HAVING COUNT(*)>= 5";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getVerbs);
		
		while (getResult.next()){
			
			newVerb= getResult.getString("verb.name");
			verbList.add(newVerb);
			//System.out.println("duplicate "+ getResult.getInt("id"));
		}
		
		System.out.println("list of verbs has: "+ verbList.size());
		return verbList;
		
	}
	public ArrayList<String> getSearchTerms(Connection conn) throws SQLException{
		
		ArrayList<String> searchTermList= new ArrayList<String>();
		Statement stmt = null;
		String newSearchTerm="";
		String getSearchTerm = "SELECT st.name FROM articledatabase.articlesearchterms AS ast JOIN articledatabase.articledomains AS ad ON ad.article_id=ast.article_id JOIN articledatabase.searchterm AS st ON ast.searchterm_id=st.id GROUP BY ast.searchterm_id";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getSearchTerm);
		
		while (getResult.next()){
			
			 newSearchTerm= getResult.getString("st.name");
			searchTermList.add(newSearchTerm);
			
		}
		
		System.out.println("list search terms has: "+ searchTermList.size());
		return searchTermList;
		
	}
	
	public void createTrainingTable() throws SQLException{
		Connection conn =startConnection();
		
		PreparedStatement pstmt = null;
		PreparedStatement statement = null;
		
		String mltraining = createStatement("mltraining", conn);
		statement = conn.prepareStatement(mltraining);		
		statement.executeUpdate();
		
		String mltest = createStatement("mltest", conn);
		pstmt = conn.prepareStatement(mltest);		
		pstmt.executeUpdate();
		
		closeConnection(conn);
		
	}
	public String createStatement(String tablename, Connection conn) throws SQLException{
		ArrayList<String> verbList= new ArrayList<String>();
		ArrayList<String> searchTermList= new ArrayList<String>();
		verbList = getVerbs(conn);
		StringBuilder stringBuilder = new StringBuilder();
		searchTermList=getSearchTerms(conn);
		 stringBuilder.append("CREATE TABLE IF NOT EXISTS "+tablename+" ( id INT NOT NULL AUTO_INCREMENT, article_id INT, ");
		 for (int i=0; i<verbList.size(); i++){
			 stringBuilder.append("`"+verbList.get(i)+"`"+" SMALLINT, ");
		 }
		 for (int i=0; i<searchTermList.size(); i++){
			 stringBuilder.append("`"+searchTermList.get(i)+"`"+" SMALLINT, ");
		 }
		 stringBuilder.append(" verb_count SMALLINT, ");
		 stringBuilder.append(" word_count SMALLINT, ");
		 stringBuilder.append(" page VARCHAR(20), ");
		 stringBuilder.append("PRIMARY KEY (id))");

		String finalString = stringBuilder.toString();
		
		return finalString;
		
		
	}
	public void createTestingTable(Connection conn){
		
	}
}
