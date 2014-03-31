package uk.ac.aber.dst1.newsarticleanalysis;

/**
 * @author Diana Silvia Teodorescu
 * 
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.Document;

public class URLConnectionReader extends Authenticator {

	static String xmls = "";
	private DataManager dataManager = new DataManager();
	ArrayList<String> searchTerms = new ArrayList<String>(Arrays.asList(
			"investigation", "thief"));

	/**
	 * The function get's each term of the array and makes two API requests: one
	 * to find the number of results found for that word and the other to get
	 * the XML data necessary for that word
	 * 
	 * @throws Exception
	 */
	public void searchArticles() throws Exception {
		int rows;
		for (int i = 0; i < searchTerms.size(); i++) {
			rows = 1;
			getArticle(searchTerms.get(i), rows);
			System.out.println("intial num is:" + rows);
			rows = dataManager.getNumFound(dataManager.buildDom(xmls));
			getArticle(searchTerms.get(i), rows);
			System.out.println(" intial num for: " + searchTerms.get(i)
					+ " is: " + rows);
		}

	}

	/**
	 * 
	 * @param searchTerm
	 *            Allows search of the articles that contain the String variable
	 *            in the text by setting it into the SOLR query
	 * @param rows
	 *            it is the variable that represents the number of rows(results)
	 *            will be shown in the result of the query
	 * @throws Exception
	 */
	public void getArticle(String searchTerm, int rows) throws Exception {
		// Sets the authenticator that will be used by the networking code
		// when a proxy or an HTTP server asks for authentication.

		Authenticator.setDefault(new URLConnectionReader());
      if (searchTerm.contains(" ")){
    	 
    	  searchTerm = searchTerm.replaceAll(" ", "%20");
        
       }
       else{
    	 // searchTerm=searchTerm;
    	   System.out.println("doesn't contain space: "+searchTerm);
        	
        }
		URL news = new URL(
				"http://hacathon.llgc.org.uk/solr/select/?q=ArticleSubject:News%20AND%20ArticleText:"
						+ searchTerm + "&rows=" + rows);
		 
		URLConnection yc = news.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;
		StringBuilder sb = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {

			sb.append(inputLine + "");

		}
		xmls = sb.toString();

		Document doc = dataManager.buildDom(xmls);

		dataManager.parsingTheXML(doc, searchTerm);

		in.close();
        
	}

	/**
	 * Hard coded password for the API connection to the NLW digital archive
	 */
	protected PasswordAuthentication getPasswordAuthentication() {

		String username = "silvia";
		String password = "Krityipp";

		// Return the information (a data holder that is used by Authenticator)
		return new PasswordAuthentication(username, password.toCharArray());

	}

}