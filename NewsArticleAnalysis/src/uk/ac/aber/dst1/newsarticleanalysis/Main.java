package uk.ac.aber.dst1.newsarticleanalysis;

public class Main {
	
	 public static void main(String[] args) throws Exception {
		 
		//URLConnectionReader urlConnect = new URLConnectionReader();	 
		//urlConnect.getArticles();
		 TaggerVerbs tagg = new TaggerVerbs();
		tagg.taggIT();
	 }
}
