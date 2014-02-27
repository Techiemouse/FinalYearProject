package uk.ac.aber.dst1.newsarticleanalysis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionReader extends Authenticator  {
	
	static String xmls="";
	private DataManager dataManager = new DataManager();
	
	
	public void getArticles() throws Exception {
		 // Sets the authenticator that will be used by the networking code
		 // when a proxy or an HTTP server asks for authentication.

		 Authenticator.setDefault(new URLConnectionReader());
	    	
	     URL news = new URL("http://hacathon.llgc.org.uk/solr/select/?q=ArticleTitle:crime");
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
	     System.out.println("end "+xmls);
	     dataManager.parsingtheXML(xmls); 
	     
	     	 in.close();	        
		}
	
	
	protected PasswordAuthentication getPasswordAuthentication(){

		String username = "silvia";
		String password = "Krityipp";
		
		// Return the information (a data holder that is used by Authenticator)
		return new PasswordAuthentication(username, password.toCharArray());
		
		}


	
}