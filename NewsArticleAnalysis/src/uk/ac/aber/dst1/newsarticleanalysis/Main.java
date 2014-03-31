package uk.ac.aber.dst1.newsarticleanalysis;

/**
 * @author Diana Silvia Teodorescu
 *
 */
public class Main {
/**
 * Main method that calls the connection
 * @param args
 * @throws Exception
 */
	public static void main(String[] args) throws Exception {
		String searchTerm = "breaking and entering";
		int rows = 8;
		URLConnectionReader urlConnect = new URLConnectionReader();
		// urlConnect.searchArticles();
		urlConnect.getArticle(searchTerm, rows);
	}
}
