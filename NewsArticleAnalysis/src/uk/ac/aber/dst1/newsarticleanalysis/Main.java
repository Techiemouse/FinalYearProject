package uk.ac.aber.dst1.newsarticleanalysis;

public class Main {
	
	 public static void main(String[] args) throws Exception {
		 String searchTerm = "crime";
			int rows=500;
		URLConnectionReader urlConnect = new URLConnectionReader();	 
		//urlConnect.searchArticles();
		urlConnect.getArticle(searchTerm, rows);
	 }
}
