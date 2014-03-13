package uk.ac.aber.dst1.newsarticleanalysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataManager {
	//ArrayList<ArticleObject> array = new ArrayList<ArticleObject>();
	DatabaseSetup database= new DatabaseSetup();
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

		if (doc.hasChildNodes()){

			 //getting the list of only the tags with doc in
			for (int i=0; i<doc.getElementsByTagName("doc").getLength();i++){
				//database.startConnection();
				database.addArticleObject(printNote(doc.getElementsByTagName("doc").item(i)),database.startConnection(),searchTerm);
				
			
			}
			database.closeConnection(database.startConnection());
		//	printArray(array);
		}
	
		
	}
	
	private ArticleObject printNote(Node tempNode) {
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
					}
					else if (elem.getAttribute("name").equals("ArticleID")) {
						artOb.setArticleID(elem.getTextContent());
					} 
					else if (elem.getAttribute("name").equals("ArticleTitle")) {
	
						artOb.setArticleTitle(elem.getTextContent());
					} 
					else if (elem.getAttribute("name").equals("ArticleAbstract")) {
						
						artOb.setArticleAbstract(elem.getTextContent());
					}
					else if (elem.getAttribute("name").equals("ArticleWordCount")) {
						
						artOb.setTextWordCount(Integer.parseInt(elem.getTextContent()));
					}
					else if (elem.getAttribute("name").equals("ArticleText")) {

						String article = replaceCharact(elem.getTextContent());

						// POS tagger the article text, extract the verb list
						// and add it to the object;
						artOb.setVerbList(tagg.getVerbs(tagg.taggIT(article)));
						artOb.setArticleText(article);
						artOb.setVerbCount(tagg.getVerbs(tagg.taggIT(article)).size());

					} 
					else if (elem.getAttribute("name").equals("PageLable")) {
						artOb.setPage(Integer.parseInt(elem.getTextContent()));
					}

					else if (elem.getAttribute("name").equals(
							"PublicationTitle")) {
						artOb.setPublicationTitle(elem.getTextContent());

					} else if (elem.getAttribute("name").equals("IssueDate")) {
						try {
							artOb.setIssueDate(transformToDate(elem
									.getTextContent()));
						} catch (DOMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} 


				}

			}

			//
			// System.out.println(artOb.toString());
			//array.add(artOb);
			// System.out.println("Node Name =" + tempNode.getNodeName() +
			// " end");
			// System.out.println("count: "+count);
			//return artOb;

		}
		return artOb;

	}

	public String replaceCharact(String badText) {

		return badText.replace("\u00c2", "").replace("—", "");

	}

	/*public ArrayList<String> getWordList(String text) {
		ArrayList<String> wordsList = new ArrayList<String>();
		for (String word : text.split("\\W")) {
			wordsList.add(word);
		}

		return wordsList;
	}*/

	public Date transformToDate(String date) throws ParseException {
		// String beforeT = date.split("T")[0];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date theDate = sdf.parse(date);

		return theDate;

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
