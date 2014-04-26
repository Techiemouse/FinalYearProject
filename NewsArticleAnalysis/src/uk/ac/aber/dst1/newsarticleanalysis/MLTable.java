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
	public ArrayList<String> getDomains(Connection conn) throws SQLException{
		
		ArrayList<String> domains= new ArrayList<String>();
		Statement stmt = null;
		String newDomain="";
		String getDomain = "SELECT d.name FROM articledatabase.domain AS d JOIN articledatabase.articledomains AS ad ON ad.domain_id=d.id GROUP BY d.id;";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getDomain);
		
		while (getResult.next()){
			
			 newDomain= getResult.getString("d.name");
			 domains.add(newDomain);
			
		}
		
		System.out.println("list domains has: "+ domains.size());
		return domains;
		
	}
	
	public void createTrainingTable() throws SQLException{
		Connection conn =startConnection();
		ArrayList<String> verbList= new ArrayList<String>();
		ArrayList<String> nounList= new ArrayList<String>();
		ArrayList<String> domainList= new ArrayList<String>();
		ArrayList<String> searchTermList= new ArrayList<String>();
		searchTermList=getSearchTerms(conn);
		nounList = getNouns(conn);
		verbList = getVerbs(conn);
		domainList = getDomains(conn);
		PreparedStatement pstmtv = null;
		PreparedStatement verbstatement = null;
		PreparedStatement pstmtn = null;
		PreparedStatement nounstatement = null;
		String nounString = getNounString(conn, nounList);
		String verbString = getVerbString(conn, verbList);
		String domainString = getDomainString(conn, domainList);
		String searchTerms = getSearchTermString(conn, searchTermList);
		String mlvtraining = createStatement("mlverbtraining", verbString, searchTerms, domainString, conn);
		verbstatement = conn.prepareStatement(mlvtraining);		
		verbstatement.executeUpdate();
		
		String mlvtest = createStatement("mlverbtest", verbString, searchTerms, domainString, conn);
		pstmtv = conn.prepareStatement(mlvtest);		
		pstmtv.executeUpdate();
		
		
		String mlntraining = createStatement("mlnountraining", nounString, searchTerms, domainString, conn);
		nounstatement = conn.prepareStatement(mlntraining);		
		nounstatement.executeUpdate();
		
		String mlntest = createStatement("mlnountest", nounString, searchTerms, domainString, conn);
		pstmtn = conn.prepareStatement(mlntest);		
		pstmtn.executeUpdate();
		
		insertArticleInfo(conn);
		
		
		closeConnection(conn);
		
	}
	public String createStatement(String tablename, String wordList, String searchTerms, String domainList, Connection conn) throws SQLException{
	
		StringBuilder stringBuilder = new StringBuilder();
	
		 stringBuilder.append("CREATE TABLE IF NOT EXISTS "+tablename+" ( id INT NOT NULL AUTO_INCREMENT, article_id INT, ");
		 stringBuilder.append(wordList);
		 stringBuilder.append(searchTerms);
		 
		 stringBuilder.append(" verb_count SMALLINT, ");
		 stringBuilder.append(" word_count SMALLINT, ");
		 stringBuilder.append(" page VARCHAR(20), ");
		 stringBuilder.append(domainList);
		 stringBuilder.append(" domain VARCHAR(50), ");
		 stringBuilder.append("PRIMARY KEY (id))");

		String finalString = stringBuilder.toString();
		
		return finalString;
		
		
	}
	public String createNounStatement(String tablename, String wordList, String domainList, Connection conn) throws SQLException{

		
		StringBuilder stringBuilder = new StringBuilder();
		
		 stringBuilder.append("CREATE TABLE IF NOT EXISTS "+tablename+" ( id INT NOT NULL AUTO_INCREMENT, article_id INT, ");
		 stringBuilder.append(wordList);
		 
		 stringBuilder.append(" noun_count SMALLINT, ");
		 stringBuilder.append(" word_count SMALLINT, ");
		 stringBuilder.append(" page VARCHAR(20), ");
		 stringBuilder.append(domainList);
		 stringBuilder.append(" domain VARCHAR(50), ");
		 stringBuilder.append("PRIMARY KEY (id))");

		String finalString = stringBuilder.toString();
		
		return finalString;
		
		
	}
	
	public String getNounString(Connection conn, ArrayList<String> nounList) throws SQLException{
		
		StringBuilder stringBuilder = new StringBuilder();
		for (int i=0; i<nounList.size(); i++){
			 stringBuilder.append("`"+nounList.get(i)+"`"+" SMALLINT DEFAULT 0, ");
		 }
		return stringBuilder.toString();
	}
public String getDomainString(Connection conn, ArrayList<String> domainList) throws SQLException{
		
		StringBuilder stringBuilder = new StringBuilder();
		for (int i=0; i<domainList.size(); i++){
			 stringBuilder.append("`"+domainList.get(i)+"_`"+" SMALLINT DEFAULT 0, ");
		 }
		return stringBuilder.toString();
	}
	public String getVerbString(Connection conn, ArrayList<String> verbList) throws SQLException{
	
		StringBuilder stringBuilder = new StringBuilder();
		for (int i=0; i<verbList.size(); i++){
			 stringBuilder.append("`"+verbList.get(i)+"`"+" SMALLINT DEFAULT 0, ");
		 }
		return stringBuilder.toString();
	}
	public String getSearchTermString(Connection conn, ArrayList<String> searchTermList) throws SQLException{
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (int i=0; i<searchTermList.size(); i++){
			 stringBuilder.append("`"+searchTermList.get(i)+"_`"+" SMALLINT DEFAULT 0, ");
		 }
		return stringBuilder.toString();
	}
	
	public void insertArticleInfo(Connection conn) throws SQLException{
			
		Statement stmt = null;
		String query = "INSERT INTO mlverbtraining(article_id, verb_count, word_count, page) SELECT DISTINCT a.id, a.verb_count, a.word_count, a.`page` FROM  articledatabase.article AS a JOIN  articledatabase.articledomains AS ad ON a.id=ad.article_id";
		stmt = conn.createStatement();
		stmt.executeUpdate(query);
		
		System.out.println("done");
		
		
	}

	public void getVerbCountandInsert(Connection conn, String tablename, String verb, int articleID) throws SQLException{
		int occurrence=0;
	
		Statement stmt = null;
		String query = "SELECT COUNT(*) AS counts FROM articledatabase.verb AS v JOIN articledatabase.articleverbs AS av ON v.id=av.verb_id JOIN articledatabase.articledomains AS ad ON av.article_id=ad.article_id WHERE v.name='"+verb+"' AND ad.article_id='"+articleID+"'";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(query);
		if (getResult.next()){
			occurrence= getResult.getInt("counts");
			insertTheOccurrence(conn, occurrence, tablename, articleID);
		}
		
		/*else{
			insertTheOccurrence(conn, occurrence, tablename, articleID);
			
		}*/
	}
	public void getNounCountandInsert(Connection conn, String tablename, String noun, int articleID) throws SQLException{
		Statement stmt = null;
		int occurrence=0;
		String query = "SELECT COUNT(*) AS counts FROM articledatabase.noun AS n JOIN articledatabase.articlenouns AS an ON n.id=an.noun_id JOIN articledatabase.articledomains AS ad ON an.article_id=ad.article_id WHERE n.name='"+noun+"' AND ad.article_id='"+articleID+"'";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(query);
		if (getResult.next()){
			occurrence= getResult.getInt("counts");
			insertTheOccurrence(conn, occurrence, tablename, articleID);
		}
		
		/*else{
			insertTheOccurrence(conn, occurrence, tablename, articleID);
			
		}*/
	}
		public void insertTheOccurrence(Connection conn, int occurrence, String tablename, int articleID) throws SQLException{
			Statement stateRes = null;
			String insertValue="INSERT '"+occurrence+"' INTO '"+tablename+"' WHERE ad.article_id='"+articleID+"'";
			stateRes= conn.createStatement();
			stateRes.executeUpdate(insertValue);
		}
	
}
