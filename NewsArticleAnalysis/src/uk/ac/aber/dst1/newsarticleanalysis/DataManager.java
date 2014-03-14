package uk.ac.aber.dst1.newsarticleanalysis;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
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
	// ArrayList<ArticleObject> array = new ArrayList<ArticleObject>();
	DatabaseSetup database = new DatabaseSetup();
	Connection db = database.startConnection();
	public Document buildDom(String input) throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(input));
		Document doc = builder.parse(is);

		return doc;
	}

	public int getNumFound(Document doc) {
		int numFound;
		// getting the result tag to find how many results the query finds
		NodeList nodes = doc.getElementsByTagName("result");
		Element result = (Element) nodes.item(0);

		// System.out.println("the number is: "+result.getAttribute("numFound"));
		numFound = Integer.parseInt(result.getAttribute("numFound"));
		return numFound;

	}

	public void parsingTheXML(Document doc, String searchTerm) {

		if (doc.hasChildNodes()) {

			// getting the list of only the tags with doc in
			database.createPublicationTable(db);
			database.createArticleTable(db);			
			for (int i = 0; i < doc.getElementsByTagName("doc").getLength(); i++) {
				// database.startConnection();
				try {
					database.addPublication(
							printNote(doc.getElementsByTagName("doc").item(i)),
							db);
				} catch (DOMException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					database.addArticleObject(
							printNote(doc.getElementsByTagName("doc").item(i)),
							db, searchTerm);
				} catch (DOMException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
			database.closeConnection(db);
			// printArray(array);
		}

	}

	private ArticleObject printNote(Node tempNode) throws DOMException, ParseException {
		TaggerVerbs tagg = new TaggerVerbs();

		ArticleObject artOb = new ArticleObject();
		// make sure it's element node.
		if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

			// get node name and value
			NodeList articleNodes = tempNode.getChildNodes();
			// System.out.println("count child =" + articleNodes.getLength());

			for (int count = 0; count < articleNodes.getLength(); count++) {
				// System.out.println("article node: "+count);
				if (articleNodes.item(count).hasAttributes()) {

					Element elem = (Element) articleNodes.item(count);
					if (elem.getAttribute("name").equals("PID")) {

						artOb.setPID(elem.getTextContent());
					} else if (elem.getAttribute("name").equals("Region")) {

						artOb.setRegion(elem.getTextContent());
					} else if (elem.getAttribute("name").equals("ArticleID")) {
						artOb.setArticleID(elem.getTextContent());
					} else if (elem.getAttribute("name").equals("ArticleTitle")) {

						artOb.setArticleTitle(replaceCharact(elem.getTextContent()));
					} else if (elem.getAttribute("name").equals(
							"ArticleAbstract")) {

						artOb.setArticleAbstract(replaceCharact(elem.getTextContent()));
					} else if (elem.getAttribute("name").equals(
							"ArticleWordCount")) {

						artOb.setTextWordCount(Integer.parseInt(elem
								.getTextContent()));
					} else if (elem.getAttribute("name").equals("ArticleText")) {

						String article = replaceCharact(elem.getTextContent());

						// POS tagger the article text, extract the verb list
						// and add it to the object;
						artOb.setVerbList(tagg.getVerbs(tagg.taggIT(article)));
						artOb.setArticleText(article);
						artOb.setVerbCount(tagg.getVerbs(tagg.taggIT(article))
								.size());

					} else if (elem.getAttribute("name").equals("PageLabel")) {
						String page = elem.getTextContent();
						if (page.contains("[")){
							page.replace("[", "");
							page.replace("]", "");
							artOb.setPage(Integer.parseInt(page));
						}
						else{
						artOb.setPage(Integer.parseInt(page));
						}
					}

					else if (elem.getAttribute("name").equals(
							"PublicationTitle")) {
						artOb.setPublicationTitle(elem.getTextContent());

					} else if (elem.getAttribute("name").equals("IssueDate")) {
						
							artOb.setIssueDate(this.transformToDate(elem.getTextContent()));
						
					}
					else if (elem.getAttribute("name").equals(
							"PublicationPID")) {
						artOb.setPublicationPID(elem.getTextContent());

					}

				}

			}

			//
			// System.out.println(artOb.toString());
			// array.add(artOb);
			// System.out.println("Node Name =" + tempNode.getNodeName() +
			// " end");
			// System.out.println("count: "+count);
			// return artOb;

		}
		return artOb;

	}

	public String replaceCharact(String badText) {
        String quote="\"";
		return badText.replace("\u00c2", "").replace("—", "").replace("\"", "\\\\\"").replace("'", "`");

	}

	/*
	 * public ArrayList<String> getWordList(String text) { ArrayList<String>
	 * wordsList = new ArrayList<String>(); for (String word :
	 * text.split("\\W")) { wordsList.add(word); }
	 * 
	 * return wordsList; }
	 */

	public String transformToDate(String date) throws ParseException {
		// String beforeT = date.split("T")[0];
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date theTempDate = sdf.parse(date);
		//java.util.Date theDate = new java.util.Date();
	SimpleDateFormat second = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		date = second.format(theTempDate);
		return date;

	}

	public void printArray(ArrayList<ArticleObject> array) {

		// for(int i = 0; i < array.size(); i++) {
		System.out.println("done: " + array.size());
		// }
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
