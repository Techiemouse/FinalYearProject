package uk.ac.aber.dst1.newsarticleanalysis;

/**
 * @author Diana Silvia Teodorescu
 *@version 1.5
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

	/**
	 * The method builds the DOM document
	 * 
	 * @param input - variable that is given by the XML result
	 * @return doc - The method returns the DOM document built from the string
	 *         of the XML
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
	 * The method can be used to get how many results a specific query has in
	 * total.
	 * 
	 * @param doc The variable is the DOM document that contains the nodes of
	 *            the initial XML result
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
	 * Method that calls the methods that create and populate the database for
	 * each article in the XML response
	 * 
	 * @param doc is the DOM document that contains the result 
	 * of a query to the database
	 * @param searchTerm - this is the search term from the query the XML results from
	 * @throws SQLException
	 */
	public void parsingTheXML(Document doc, String searchTerm)
			throws SQLException, ParseException {
		DatabaseSetup database = new DatabaseSetup();

		Connection db = database.startConnection();

		if (doc.hasChildNodes()) {

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
			// getting the list of only the tags called doc
			for (int i = 0; i < doc.getElementsByTagName("doc").getLength(); i++) {
				ArticleObject theArticle = new ArticleObject();

				ArticleObject thePublication = new ArticleObject();
				// getting the information that will go in the publication table
				// and creating an object with it
				thePublication = printNote(doc.getElementsByTagName("doc").item(i), "publication");
				// getting the information that will go in the article table
				// and creating an object with it
				theArticle = printNote(doc.getElementsByTagName("doc").item(i),"article");
				database.addPublication(thePublication, db);

				int articleID = database.addArticleObject(theArticle, db, searchTerm);
				int searchTermID = database.addSearchTerm(searchTerm, db);
				database.addArticleSearchTerms(articleID, searchTermID, db);
				ArrayList<Integer> nounIDs = database.addNoun(theArticle, db);
				ArrayList<Integer> verbIDs = database.addVerb(theArticle, db);
				database.addArticleNouns(theArticle, articleID, nounIDs, db);
				database.addArticleVerbs(theArticle, articleID, verbIDs, db);

			}

		}
		database.closeConnection(db);
	}

	private ArticleObject printNote(Node tempNode, String option)
			throws DOMException {
		TaggerWords tagg = new TaggerWords();

		ArticleObject artOb = new ArticleObject();

		// make sure the node it's an element node.
		if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

			// get node name and attributes
			NodeList articleNodes = tempNode.getChildNodes();
			for (int count = 0; count < articleNodes.getLength(); count++) {

				if (articleNodes.item(count).hasAttributes()) {

					Element elem = (Element) articleNodes.item(count);

					/****** If adding Article *****/
					if (option == "article") {

						if (elem.getAttribute("name").equals("PID")) {

							artOb.setPID(elem.getTextContent());
						} 
						else if (elem.getAttribute("name")
								.equals("ArticleID")) {
							artOb.setArticleID(elem.getTextContent());
						} 
						else if (elem.getAttribute("name").equals("ArticleTitle")) {

							artOb.setArticleTitle(replaceCharact(elem.getTextContent()));
						} else if (elem.getAttribute("name").equals("ArticleAbstract")) {

							artOb.setArticleAbstract(replaceCharact(elem.getTextContent()));
						} else if (elem.getAttribute("name").equals("ArticleWordCount")) {
							//transform the value in the response into an integer
							artOb.setTextWordCount(Integer.parseInt(elem.getTextContent()));
						} else if (elem.getAttribute("name").equals("ArticleText")) {
							//replace unwanted characters in the text
							String article = replaceCharact(elem.getTextContent());
							artOb.setArticleText(article);

							ArrayList<String> vLemmas =new ArrayList<String>();
							ArrayList<String> nLemmas =new ArrayList<String>();

							vLemmas = tagg.lemmAttributes(tagg.taggIT(article, "verb"));

							//split a string and get the verblist by calling the get verbs method
							artOb.setVerbList(vLemmas);
							artOb.setVerbCount(vLemmas.size());

							nLemmas = tagg.lemmAttributes(tagg.taggIT(article, "noun"));

							artOb.setNounList(nLemmas);
							artOb.setNounCount(nLemmas.size());



						} else if (elem.getAttribute("name").equals("PageLabel")) {
							String page = elem.getTextContent();
							//changes done to the content of the content in the page attribute
							//this was needed because some pages had brackets surrounding them
							if (page.contains("[")) {
								page = page.replace("[", "");
								page = page.replace("]", "");

								artOb.setPage(page);
							} else {

								artOb.setPage(page);

							}
						}else if (elem.getAttribute("name")
								.equals("IssueDate")) {

							try {
								artOb.setIssueDate(this.transformToDate(elem.getTextContent()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
						} else if (elem.getAttribute("name").equals("PublicationTitle")) {
							artOb.setPublicationTitle(replaceCharact(elem.getTextContent()));

						}  else if (elem.getAttribute("name").equals("PublicationPID")) {
							artOb.setPublicationPID(elem.getTextContent());

						}
					}

				}

			}


		}
		return artOb;

	}
	/**
	 * The method replaces specific characters in a String
	 * @param badText - is the text that will have to be modified
	 * @return - returns the text without the unwanted characters
	 */
	public String replaceCharact(String badText) {

		return badText.replace("\u00c2", "").replace("—", "")
				.replace("\"", "\\\\\"").replace("'", "`");

	}


	/**
	 * The function takes the date and transforms it into the format needed for the MySQL datetime type.
	 * @param date The string version of the date taken from the XML response
	 * @return the date as String after formating it for the MySQL type
	 * @throws ParseException
	 */
	public String transformToDate(String date) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date theTempDate = sdf.parse(date);

		SimpleDateFormat second = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		date = second.format(theTempDate);
		return date;

	}
	/**
	 * Function  that prints out the array
	 * @param array
	 */
	public void printArray(ArrayList<Integer> array) {

		for(int i = 0; i < array.size(); i++) {
			System.out.println("done: " + array.get(i)+"/n");
		}
	}



}
