package uk.ac.aber.dst1.newsarticleanalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MLTable {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/articledatabase";

	// Database credentials
	static final String USER = "Diana";
	static final String PASS = "ppiytirK";

	public Connection startConnection() {
		Connection conn = null;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		return conn;
	}

	public void closeConnection(Connection conn) {

		try {
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		System.out.println("Goodbye!");

	}

	public ArrayList<String> getNouns(Connection conn) throws SQLException {

		ArrayList<String> nounList = new ArrayList<String>();
		Statement stmt = null;
		String newNoun = "";
		String getNouns = "SELECT COUNT(*), noun.name FROM articledatabase.articlenouns AS av JOIN articledatabase.articledomains AS ad ON ad.article_id=av.article_id JOIN articledatabase.noun ON av.noun_id=noun.id GROUP BY av.noun_id HAVING COUNT(*)>= 5";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getNouns);

		while (getResult.next()) {

			newNoun = getResult.getString("noun.name");
			nounList.add(newNoun);

		}

		System.out.println("list of nouns has: " + nounList.size());
		return nounList;

	}

	public ArrayList<String> getVerbs(Connection conn) throws SQLException {

		ArrayList<String> verbList = new ArrayList<String>();
		Statement stmt = null;
		String newVerb = "";
		String getVerbs = "SELECT COUNT(*), verb.name FROM articledatabase.articleverbs AS av JOIN articledatabase.articledomains AS ad ON ad.article_id=av.article_id JOIN articledatabase.verb ON av.verb_id=verb.id GROUP BY av.verb_id HAVING COUNT(*)>= 5";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getVerbs);

		while (getResult.next()) {

			newVerb = getResult.getString("verb.name");
			verbList.add(newVerb);

		}

		System.out.println("list of verbs has: " + verbList.size());
		return verbList;

	}

	public ArrayList<String> getSearchTerms(Connection conn)
			throws SQLException {

		ArrayList<String> searchTermList = new ArrayList<String>();
		Statement stmt = null;
		String newSearchTerm = "";
		String getSearchTerm = "SELECT st.name FROM articledatabase.articlesearchterms AS ast JOIN articledatabase.articledomains AS ad ON ad.article_id=ast.article_id JOIN articledatabase.searchterm AS st ON ast.searchterm_id=st.id GROUP BY ast.searchterm_id";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getSearchTerm);

		while (getResult.next()) {

			newSearchTerm = getResult.getString("st.name");
			searchTermList.add(newSearchTerm);

		}

		System.out.println("list search terms has: " + searchTermList.size());
		return searchTermList;

	}

	public ArrayList<String> getDomains(Connection conn) throws SQLException {

		ArrayList<String> domains = new ArrayList<String>();
		Statement stmt = null;
		String newDomain = "";
		String getDomain = "SELECT d.name FROM articledatabase.domain AS d JOIN articledatabase.articledomains AS ad ON ad.domain_id=d.id GROUP BY d.id;";
		stmt = conn.createStatement();
		ResultSet getResult = stmt.executeQuery(getDomain);

		while (getResult.next()) {

			newDomain = getResult.getString("d.name");
			domains.add(newDomain);

		}

		System.out.println("list domains has: " + domains.size());
		return domains;

	}

	public void createTrainingTable() throws SQLException {
		Connection conn = startConnection();
		ArrayList<String> verbList = new ArrayList<String>();
		ArrayList<String> nounList = new ArrayList<String>();
		ArrayList<String> domainList = new ArrayList<String>();
		ArrayList<String> searchTermList = new ArrayList<String>();
		searchTermList = getSearchTerms(conn);
		nounList = getNouns(conn);
		verbList = getVerbs(conn);
		domainList = getDomains(conn);
		Statement pstmtv = null;
		Statement verbstatement = null;
		Statement pstmtn = null;
		Statement nounstatement = null;
		String nounString = getNounString(conn, nounList);
		String verbString = getVerbString(conn, verbList);
		String domainString = getDomainString(conn, domainList);
		String searchTerms = getSearchTermString(conn, searchTermList);

		verbstatement = conn.createStatement();
		String mlvtraining = createStatement("mlverbtraining", verbString,
				searchTerms, domainString, conn);
		verbstatement.executeUpdate(mlvtraining);

		pstmtv = conn.createStatement();
		String mlvtest = createStatement("mlverbtest", verbString, searchTerms,
				domainString, conn);
		pstmtv.executeUpdate(mlvtest);

		nounstatement = conn.createStatement();
		String mlntraining = createNounStatement("mlnountraining", nounString,
				searchTerms, domainString, conn);
		nounstatement.executeUpdate(mlntraining);

		pstmtn = conn.createStatement();
		String mlntest = createNounStatement("mlnountest", nounString,
				searchTerms, domainString, conn);
		pstmtn.executeUpdate(mlntest);

		ArrayList<Integer> articlesID = new ArrayList<Integer>();
		articlesID = getIDList(conn);

		//insertArticleInfo(conn, articlesID);
		System.out.println(" updated other info ");
		 ArrayList<Integer> trainArticlesID= new ArrayList<Integer>();
		 trainArticlesID=getMLarticleList(conn, "mlverbtraining");
		 ArrayList<Integer> testArticlesID= new ArrayList<Integer>();
		 testArticlesID=getMLarticleList(conn, "mlverbtest");
		 System.out.println("got the mlarticles ");
		//insertVerbOccurrence(verbList, trainArticlesID, conn, "mlverbtraining");
		// insertVerbOccurrence(verbList, testArticlesID, conn, "mlverbtest");
		 //insertNounOccurrence(nounList, trainArticlesID, conn, "mlnountraining");
		// insertNounOccurrence(nounList, testArticlesID, conn, "mlnountest");
		 /* insertDomainOccurrence(domainList, trainArticlesID, conn, "mlnountraining");
		 insertDomainOccurrence(domainList, testArticlesID, conn, "mlnountest");
		 System.out.println(" ++++done domains in noun ");
		 insertDomainOccurrence(domainList, trainArticlesID, conn, "mlverbtraining");
		 insertDomainOccurrence(domainList, testArticlesID, conn, "mlverbtest");
		 System.out.println(" ---done domains in verb ");
		 insertSearchTermOccurrence(searchTermList, trainArticlesID, conn, "mlnountraining");
		 insertSearchTermOccurrence(searchTermList, testArticlesID, conn, "mlnountest");
		 System.out.println(" +++++done searcht in noun ");
		 insertSearchTermOccurrence(searchTermList, trainArticlesID, conn, "mlverbtraining");
		 insertSearchTermOccurrence(searchTermList, testArticlesID, conn, "mlverbtest");
		 System.out.println(" ---done searcht in verb ");*/
		// insertLabelledDomain(conn, trainArticlesID, "mlnountraining");
		 insertLabelledDomain(conn, testArticlesID, "mlnountest");
		 System.out.println(" ---done labeled domain noun ml ");
		// insertLabelledDomain(conn, trainArticlesID, "mlverbtraining");
		 insertLabelledDomain(conn, testArticlesID, "mlverbtest");
		 System.out.println(" ---done labeled domain verb ml ");
		closeConnection(conn);

	}

	public String createStatement(String tablename, String wordList,
			String searchTerms, String domainList, Connection conn)
			throws SQLException {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("CREATE TABLE IF NOT EXISTS " + tablename
				+ " ( id INT NOT NULL AUTO_INCREMENT, article_id INT, ");
		stringBuilder.append(wordList);
		stringBuilder.append(searchTerms);

		stringBuilder.append(" verb_count SMALLINT NOT NULL DEFAULT 0, ");
		stringBuilder.append(" word_count SMALLINT NOT NULL DEFAULT 0, ");
		stringBuilder.append(" page VARCHAR(20), ");
		stringBuilder.append(domainList);
		stringBuilder.append(" domain VARCHAR(50), ");
		stringBuilder.append("PRIMARY KEY (id))");

		String finalString = stringBuilder.toString();

		return finalString;

	}

	public String createNounStatement(String tablename, String wordList,
			String searchTerms, String domainList, Connection conn)
			throws SQLException {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("CREATE TABLE IF NOT EXISTS " + tablename
				+ " ( id INT NOT NULL AUTO_INCREMENT, article_id INT, ");
		stringBuilder.append(wordList);
		stringBuilder.append(searchTerms);
		stringBuilder.append(" noun_count SMALLINT NOT NULL DEFAULT 0, ");
		stringBuilder.append(" word_count SMALLINT NOT NULL DEFAULT 0, ");
		stringBuilder.append(" page VARCHAR(20), ");
		stringBuilder.append(domainList);
		stringBuilder.append(" domain VARCHAR(50), ");
		stringBuilder.append("PRIMARY KEY (id))");

		String finalString = stringBuilder.toString();

		return finalString;

	}

	public String getNounString(Connection conn, ArrayList<String> nounList)
			throws SQLException {

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < nounList.size(); i++) {
			stringBuilder.append("`" + nounList.get(i) + "`"
					+ " SMALLINT NOT NULL DEFAULT 0, ");
		}
		return stringBuilder.toString();
	}

	public String getDomainString(Connection conn, ArrayList<String> domainList)
			throws SQLException {

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < domainList.size(); i++) {
			String domain=domainList.get(i).replace(" ", "$");
			stringBuilder.append("`"+domain+"_`"
					+ " SMALLINT NOT NULL DEFAULT 0, ");
		}
		return stringBuilder.toString();
	}

	public String getVerbString(Connection conn, ArrayList<String> verbList)
			throws SQLException {

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < verbList.size(); i++) {
			stringBuilder.append("`" + verbList.get(i) + "`"
					+ " SMALLINT NOT NULL DEFAULT 0, ");
		}
		return stringBuilder.toString();
	}

	public String getSearchTermString(Connection conn,
			ArrayList<String> searchTermList) throws SQLException {

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < searchTermList.size(); i++) {
			stringBuilder.append("`" + searchTermList.get(i) + "_`"
					+ " SMALLINT NOT NULL DEFAULT 0, ");
		}
		return stringBuilder.toString();
	}

	public ArrayList<Integer> getIDList(Connection conn) throws SQLException {
		ArrayList<Integer> articlesID = new ArrayList<Integer>();

		Statement retrieve = null;
		int articleID = 0;
		String getArticleId = "SELECT DISTINCT a.id "
				+ "FROM  articledatabase.article AS a "
				+ "JOIN  articledatabase.articledomains AS ad "
				+ "ON a.id=ad.article_id";
		retrieve = conn.createStatement();
		ResultSet getResult = retrieve.executeQuery(getArticleId);

		while (getResult.next()) {

			articleID = getResult.getInt("a.id");
			articlesID.add(articleID);

		}

		return articlesID;

	}

	public void insertArticleInfo(Connection conn, ArrayList<Integer> articlesID)
			throws SQLException {
		int trainingArticles = (articlesID.size() / 4) * 3;
		System.out.println("size of training articles: " + trainingArticles);

		for (int i = 0; i < trainingArticles; i++) {
			updateAndInsert(conn, "training", articlesID.get(i));

		}

		for (int i = trainingArticles; i < articlesID.size(); i++) {

			updateAndInsert(conn, "test", articlesID.get(i));
		}

	}

	public void updateAndInsert(Connection conn, String tablename, int articleID)
			throws SQLException {

		Statement stm = null;
		String query = "SELECT article_id FROM mlverb" + tablename
				+ " WHERE article_id = '" + articleID + "'";
		stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(query);

		if (rs.next()) {
			Statement statement = null;
			int article_id = 0;
			int noun_count = 0;
			int word_count = 0;
			int verb_count = 0;
			String page = "";
			String getTheData = "SELECT DISTINCT a.id, a.noun_count, a.verb_count, a.word_count, a.page "
					+ "FROM  articledatabase.article AS a "
					+ "WHERE a.id= '"
					+ articleID + "'";
			statement = conn.createStatement();
			ResultSet getResult = statement.executeQuery(getTheData);

			while (getResult.next()) {
				Statement nstate = null;
				Statement vstate = null;
				article_id = getResult.getInt("a.id");
				noun_count = getResult.getInt("a.noun_count");
				verb_count = getResult.getInt("a.verb_count");
				word_count = getResult.getInt("a.word_count");
				page = getResult.getString("a.page");
				String forNounTable = "UPDATE mlnoun" + tablename
						+ " SET noun_count ='" + noun_count
						+ "', word_count ='" + word_count + "', page ='" + page
						+ "' WHERE mlnoun" + tablename + ".article_id= '"
						+ article_id + "'";
				nstate = conn.createStatement();
				nstate.executeUpdate(forNounTable);

				String forVerbTable = "UPDATE mlverb" + tablename
						+ " SET verb_count ='" + verb_count
						+ "', word_count ='" + word_count + "', page ='" + page
						+ "' WHERE mlverb" + tablename + ".article_id= '"
						+ article_id + "'";
				vstate = conn.createStatement();
				vstate.executeUpdate(forVerbTable);

			}
		} else {
			Statement stmt = null;
			String forVerbTable = "INSERT INTO mlverb"
					+ tablename
					+ "(article_id, verb_count, word_count, page) "
					+ "SELECT DISTINCT a.id, a.verb_count, a.word_count, a.`page` "
					+ "FROM  articledatabase.article AS a "
					+ "WHERE a.id= '"
					+ articleID + "'";
			stmt = conn.createStatement();
			stmt.executeUpdate(forVerbTable);

			Statement state = null;
			String forNounTable = "INSERT INTO mlnoun"
					+ tablename
					+ "(article_id, noun_count, word_count, page) "
					+ "SELECT DISTINCT a.id, a.noun_count, a.word_count, a.`page` "
					+ "FROM  articledatabase.article AS a "
					+ "WHERE a.id= '"
					+ articleID + "'";
			state = conn.createStatement();
			state.executeUpdate(forNounTable);

		}

	}

	public ArrayList<Integer> getMLarticleList(Connection conn, String tablename)
			throws SQLException {
		ArrayList<Integer> articleList = new ArrayList<Integer>();

		Statement retrieve = null;
		int articleID = 0;
		String getArticleId = "SELECT article_id FROM " + tablename + "";
		retrieve = conn.createStatement();
		ResultSet getResult = retrieve.executeQuery(getArticleId);

		while (getResult.next()) {

			articleID = getResult.getInt("article_id");
			articleList.add(articleID);

		}

		return articleList;
	}

	public void insertVerbOccurrence(ArrayList<String> verbList,
			ArrayList<Integer> articlesID, Connection conn, String tablename)
			throws SQLException {
		int occurrence = 0;
		for (int i = 0; i < verbList.size(); i++) {
			for (int j = 0; j < articlesID.size(); j++) {
				Statement stmt = null;
				String query = "SELECT COUNT(*) AS counts "
						+ "FROM articledatabase.verb AS v "
						+ "JOIN articledatabase.articleverbs AS av "
						+ "ON v.id=av.verb_id "
						+ "WHERE v.name='"
						+ verbList.get(i)
						+ "' AND av.article_id='"
						+ articlesID.get(j) + "'";
				stmt = conn.createStatement();
				ResultSet getResult = stmt.executeQuery(query);
				if (getResult.next()) {
					occurrence = getResult.getInt("counts");
					insertTheOccurrence(conn, occurrence, tablename,
							articlesID.get(j), verbList.get(i));
					// System.out.println("Occurrence of "+verbList.get(i)+" on article: "+articlesID.get(j)+" the occurrence is "+occurrence);
				}
			}
		}
		System.out.println("Inserted occurrence for verbs");

	}

	public void insertNounOccurrence(ArrayList<String> nounList,
			ArrayList<Integer> articlesID, Connection conn, String tablename)
			throws SQLException {
		int occurrence = 0;
		for (int i = 0; i < nounList.size(); i++) {
			for (int j = 0; j < articlesID.size(); j++) {
				Statement stmt = null;
				String query = "SELECT COUNT(*) AS counts "
						+ "FROM articledatabase.noun AS n "
						+ "JOIN articledatabase.articlenouns AS an "
						+ "ON n.id=an.noun_id "
						+ "WHERE n.name='"+nounList.get(i)+"' AND an.article_id="+articlesID.get(j)+"";
				stmt = conn.createStatement();
				ResultSet getResult = stmt.executeQuery(query);
				if (getResult.next()) {
					occurrence = getResult.getInt("counts");
					insertTheOccurrence(conn, occurrence, tablename,
							articlesID.get(j), nounList.get(i));
				}
			}
		}

	}
	
	public void insertDomainOccurrence(ArrayList<String> domainList,
			ArrayList<Integer> articlesID, Connection conn, String tablename) throws SQLException{
		int occurrence = 0;
		for (int i = 0; i < domainList.size(); i++) {
			for (int j = 0; j < articlesID.size(); j++) {
				Statement stmt = null;
				String domain=domainList.get(i).replace("_","").replace("$", " ");
				String query ="SELECT COUNT(*) AS counts "
						+ "FROM articledatabase.domain AS d "
						+ "JOIN articledatabase.articledomains AS ad "
						+ "ON d.id=ad.domain_id WHERE d.name='"+domain+"' "
						+ "AND ad.article_id ='"
						+ articlesID.get(j) + "'";
				stmt = conn.createStatement();
				ResultSet getResult = stmt.executeQuery(query);
				if (getResult.next()) {
					occurrence = getResult.getInt("counts");
					String word=""+domainList.get(i).replace(" ", "$")+"_";
					insertTheOccurrence(conn, occurrence, tablename, articlesID.get(j), word);
				}
			}
		}
	}

		public void insertSearchTermOccurrence(ArrayList<String> searchtermList,
				ArrayList<Integer> articlesID, Connection conn, String tablename) throws SQLException{
			int occurrence = 0;
			for (int i = 0; i < searchtermList.size(); i++) {
				for (int j = 0; j < articlesID.size(); j++) {
					Statement stmt = null;
					String search=searchtermList.get(i).replace("_","");
					String query ="SELECT COUNT(*) AS counts FROM articledatabase.searchterm AS st "
							+ "JOIN articledatabase.articlesearchterms AS ast "
							+ "ON st.id=ast.searchterm_id "
							+ "WHERE st.name='"+search+"' "
							+ "AND ast.article_id ='"
							+ articlesID.get(j) + "'";
					stmt = conn.createStatement();
					ResultSet getResult = stmt.executeQuery(query);
					if (getResult.next()) {
						occurrence = getResult.getInt("counts");
						String searcht=""+searchtermList.get(i).replace(" ","$")+"_";
						insertTheOccurrence(conn, occurrence, tablename,
								articlesID.get(j), searcht);
					}
				}
			}

	
	}
		
		public void insertLabelledDomain(Connection conn, ArrayList<Integer> articlesID, String tablename) throws SQLException{
			for (int i = 0; i < articlesID.size(); i++) {
				Statement stmt = null;
				Statement stateRes = null;
			String query = "SELECT ad.article_id, d.name "
					+ "FROM articledatabase.articledomains AS ad "
					+ "JOIN articledatabase.domain AS d "
					+ "ON ad.domain_id=d.id "
					+ "WHERE ad.article_id="+articlesID.get(i)+" LIMIT 1";
			stmt = conn.createStatement();
			ResultSet getResult = stmt.executeQuery(query);
			if (getResult.next()) {
				String theDomain=getResult.getString("d.name");
			
				String insertValue="UPDATE "+tablename+" "
				 		+ "SET domain='"+theDomain+"' "
				 		+ "WHERE article_id="+articlesID.get(i)+""; 
				 stateRes = conn.createStatement(); 
				 stateRes.executeUpdate(insertValue);
			}
			}
		}
	public void insertTheOccurrence(Connection conn, int occurrence,
			String tablename, int articleID, String word) throws SQLException {
		Statement stateRes = null;
		Statement stmt = null;
		String query = "SELECT article_id FROM " + tablename
				+ " WHERE article_id ='" + articleID + "'";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		if (rs.next()) {
			
			 String insertValue="UPDATE "+tablename+" "
			 		+ "SET `"+word+"`="+occurrence+" "
			 		+ "WHERE article_id="+articleID+""; 
			 stateRes = conn.createStatement(); 
			 stateRes.executeUpdate(insertValue);
			
		} else {
			String insertValue = "INSERT INTO " + tablename + "(`" + word
					+ "`) VALUES (" + occurrence + ") WHERE article_id="
					+ articleID + "";
			stateRes = conn.createStatement();
			stateRes.executeUpdate(insertValue);

		}
	}

}
