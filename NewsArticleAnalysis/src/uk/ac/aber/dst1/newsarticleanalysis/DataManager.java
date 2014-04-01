package uk.ac.aber.dst1.newsarticleanalysis;

/**
 * @author Diana Silvia Teodorescu
 *
 */

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataManager {
	DatabaseSetup database = new DatabaseSetup();
	
/**
 * 
 * @param input the variable is given by the XML result
 * @return doc - The method returns the DOM document built from the string of the XML
 * @throws ParserConfigurationException
 * @throws SAXException
 * @throws IOException
 */
	public Document buildDom(String input) throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(input));
		Document doc = builder.parse(is);

		return doc;
	}
/**
 * 
 * @param doc The variable is the DOM document that contains the nodes of the initial XML result
 * @return the number of results found for that query
 */
	public int getNumFound(Document doc) {
		int numFound;
		// getting the result tag to find how many results the query finds
		NodeList nodes = doc.getElementsByTagName("result");
		Element result = (Element) nodes.item(0);

		numFound = Integer.parseInt(result.getAttribute("numFound"));
		return numFound;

	}
/**
 * 
 * @param doc 
 * @param searchTerm
 * @throws SQLException 
 */
	public void parsingTheXML(Document doc, String searchTerm, Connection db) throws SQLException, ParseException {

		if (doc.hasChildNodes()) {

			// getting the list of only the tags with doc in
			database.createPublicationTable(db);
			database.createArticleTable(db);
			database.createDomainTable(db);
			database.createArticleDomains(db);
			database.createSearchTermTable(db);
			database.createArticleSearchTerms(db);
			database.createNounTable(db);
			database.createArticleNouns(db);			
			database.createVerbTable(db);
			database.createArticleVerbs(db);
			database.addDomain(db);
			
			for (int i = 0; i < doc.getElementsByTagName("doc").getLength(); i++) {
				ArticleObject theArticle = new ArticleObject();
				ArticleObject thePublication = new ArticleObject();
				
					thePublication = printNote(doc.getElementsByTagName("doc").item(i),"publication");				
					theArticle = printNote(doc.getElementsByTagName("doc").item(i),"article");								
					database.addPublication(thePublication, db);							
					int articleID = database.addArticleObject(theArticle, db, searchTerm);					
					int searchTermID = database.addSearchTerm(searchTerm, db);
					database.addArticleSearchTerms(articleID, searchTermID, db);
					ArrayList<Integer> nounIDs = database.addNoun(theArticle, db);
					ArrayList<Integer> verbIDs = database.addVerb(theArticle, db);
					database.addArticleNouns(theArticle, articleID, nounIDs, db);
					database.addArticleVerbs(theArticle, articleID, verbIDs, db);
				System.out.println("Article -----"+i+"-------- DONE");
				

			}
			
			// printArray(array);
		}
		//database.closeConnection(db);
	}

	private ArticleObject printNote(Node tempNode, String option)
			throws DOMException, ParseException {
		TaggerVerbs tagg = new TaggerVerbs();

		ArticleObject artOb = new ArticleObject();
		// make sure it's element node.
		if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

			// get node name and value
			NodeList articleNodes = tempNode.getChildNodes();
			for (int count = 0; count < articleNodes.getLength(); count++) {

				if (articleNodes.item(count).hasAttributes()) {

					Element elem = (Element) articleNodes.item(count);
					/****** If adding Article *****/
					if (option == "article") {

						if (elem.getAttribute("name").equals("PID")) {

							artOb.setPID(elem.getTextContent());
						} else if (elem.getAttribute("name")
								.equals("ArticleID")) {
							artOb.setArticleID(elem.getTextContent());
						} else if (elem.getAttribute("name").equals(
								"ArticleTitle")) {

							artOb.setArticleTitle(replaceCharact(elem
									.getTextContent()));
						} else if (elem.getAttribute("name").equals(
								"ArticleAbstract")) {

							artOb.setArticleAbstract(replaceCharact(elem
									.getTextContent()));
						} else if (elem.getAttribute("name").equals(
								"ArticleWordCount")) {

							artOb.setTextWordCount(Integer.parseInt(elem
									.getTextContent()));
						} else if (elem.getAttribute("name").equals(
								"ArticleText")) {

							String article = replaceCharact(elem
									.getTextContent());
							artOb.setArticleText(article);
							//System.out.println("text is set-up "+article);
							ArrayList<String> vLemmas =new ArrayList<String>();
							ArrayList<String> nLemmas =new ArrayList<String>();
							
							vLemmas = tagg.lemmAttributes(tagg
									.taggIT(article, "verb"));
							
								
							artOb.setVerbList(vLemmas);
							artOb.setVerbCount(vLemmas.size());
							//System.out.println("verb count"+vLemmas.size());
							nLemmas = tagg.lemmAttributes(tagg
									.taggIT(article, "noun"));
							
							artOb.setNounList(nLemmas);
							artOb.setNounCount(nLemmas.size());
							//System.out.println("noun count"+nLemmas.size());
							

						} else if (elem.getAttribute("name")
								.equals("PageLabel")) {
							String page = elem.getTextContent();
							if (page.contains("[")) {
								page = page.replace("[", "");
								page = page.replace("]", "");
								artOb.setPage(Integer.parseInt(page));
							} else {
								artOb.setPage(Integer.parseInt(page));
							}
						}

						else if (elem.getAttribute("name").equals(
								"PublicationPID")) {
							artOb.setPublicationPID(elem.getTextContent());

						}
						/****** If adding Publication *****/
					} else if (option == "publication") {
						if (elem.getAttribute("name").equals("Region")) {

							artOb.setRegion(elem.getTextContent());
						} else if (elem.getAttribute("name").equals(
								"PublicationTitle")) {
							artOb.setPublicationTitle(elem.getTextContent());

						} else if (elem.getAttribute("name")
								.equals("IssueDate")) {

							artOb.setIssueDate(this.transformToDate(elem
									.getTextContent()));

						} else if (elem.getAttribute("name").equals(
								"PublicationPID")) {
							artOb.setPublicationPID(elem.getTextContent());

						}
					}

				}

			}


		}
		return artOb;

	}

	public String replaceCharact(String badText) {
		
		return badText.replace("\u00c2", "").replace("—", "")
				.replace("\"", "\\\\\"").replace("'", "`");

	}

	/*
	 * public ArrayList<String> getWordList(String text) { ArrayList<String>
	 * wordsList = new ArrayList<String>(); for (String word :
	 * text.split("\\W")) { wordsList.add(word); }
	 * 
	 * return wordsList; }
	 */

	public String transformToDate(String date) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date theTempDate = sdf.parse(date);
		
		SimpleDateFormat second = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		date = second.format(theTempDate);
		return date;

	}

	public void printArray(ArrayList<Integer> array) {

		for(int i = 0; i < array.size(); i++) {
		//System.out.println("done: " + array.get(i)+"/n");
		}
	}

	public void appentArticles() {
		// LOOP THROUGH EACH article
		// {

		// if(articleid.getchars.get(articleid.getchars().size()-1) == 1){
		// add to new spot in array
		// }
		// else{
		// add to the previous spot in array
		// }
		// }

	}

}
