package uk.ac.aber.dst1.newsarticleanalysis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class URLConnectionReader extends Authenticator  {
	
	static String xmls="";
	private DataManager dataManager = new DataManager();
	ArrayList<String> searchTerms = new ArrayList<String>(Arrays.asList("investigation","thief"));
	
	
	public void searchArticles() throws Exception{
		int rows;
		for (int i=0; i<searchTerms.size();i++){
			rows=1;
		getArticle(searchTerms.get(i),rows);
		System.out.println("intial num is:"+rows);
		rows = dataManager.getNumFound(dataManager.buildDom(xmls));
		getArticle(searchTerms.get(i),rows);
		System.out.println(" intial num for: "+searchTerms.get(i)+" is: "+rows);
		}
		
		
	}
	public void getArticle(String searchTerm, int rows) throws Exception {
		 // Sets the authenticator that will be used by the networking code
		 // when a proxy or an HTTP server asks for authentication.

		 Authenticator.setDefault(new URLConnectionReader());
	     
	     URL news = new URL("http://hacathon.llgc.org.uk/solr/select/?q=ArticleSubject:News%20AND%20ArticleTitle:"+searchTerm+"&rows="+rows);
	     URLConnection yc = news.openConnection();
	        	        
	     BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	     String inputLine; 
	     StringBuilder sb = new StringBuilder();
	     while ((inputLine = in.readLine()) != null){
	        
	    	 sb.append(inputLine+"");
	              
	     }
	     xmls=sb.toString();
	     dataManager.buildDom(xmls);
	    
	     dataManager.parsingTheXML(dataManager.buildDom(xmls)); 
	     
	     	 in.close();	        
		}
	
	
	protected PasswordAuthentication getPasswordAuthentication(){

		String username = "silvia";
		String password = "Krityipp";
		
		// Return the information (a data holder that is used by Authenticator)
		return new PasswordAuthentication(username, password.toCharArray());
		
		}


	
}