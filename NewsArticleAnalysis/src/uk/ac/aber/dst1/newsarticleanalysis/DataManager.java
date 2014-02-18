package uk.ac.aber.dst1.newsarticleanalysis;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataManager {
	
	
	
	public void parsingXML(String input) throws ParserConfigurationException, SAXException, IOException, ParseException{

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();		
		DocumentBuilder builder = null;
		
		//System.out.println("input string is  "+input);
			
		builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(input));
					
		Document doc = builder.parse(is);

		System.out.println("root tag: "+doc.getDocumentElement().getNodeName());					
		System.out.println("number of elements is: "+doc.getElementsByTagName("doc").getLength());
			
		NodeList stringList = doc.getElementsByTagName("str");	
		for (int i=0; i<stringList.getLength(); i++){
				
			Node lNode = stringList.item(i);
				
			if (lNode.getNodeType() == Node.ELEMENT_NODE){
					
				Element elem = (Element) lNode;
					
				//System.out.println("attribute: " + elem.getAttribute("name"));
				if (elem.getAttribute("name").equals("ArticleTitle")){
					
					System.out.println("title: " + elem.getTextContent());
				}
				else if (elem.getAttribute("name").equals("ArticleText")){
					
					String article = replaceCharact(elem.getTextContent());						
					System.out.println("article is:"+ article);
							
					this.getWordList(article);
	
				}
				else if (elem.getAttribute("name").equals("IssueDate")){
							
				}
								
		}
				
			//LOOP THROUGH EACH article
			//{
					
			//if(articleid.getchars.get(articleid.getchars().size()-1) == 1){
			//add to new spot in array
			//   }
			//	else{
			//add to the previous spot in array
			//}
			//}
				
		}
		NodeList dateList = doc.getElementsByTagName("date");
		for (int i=0; i<dateList.getLength(); i++){
			Node dNode = dateList.item(i);
					
			if (dNode.getNodeType() == Node.ELEMENT_NODE){
							
				Element elem = (Element) dNode;
							
				//System.out.println("attribute: " + elem.getAttribute("name"));
				if (elem.getAttribute("name").equals("IssueDate")){
					
					System.out.println("date: " + elem.getTextContent());
					String artDate = elem.getTextContent();
					System.out.println("the date: "+transformToDate(artDate)); // test
				}
			}
				
			
		}
	}
			
	
	public String replaceCharact(String badText){
			
		return badText.replace("\u00c2", "").replace("—", "");
						
	}
		
	public ArrayList<String> getWordList(String text){
		ArrayList<String> wordsList = new ArrayList<String>();
		for(String word : text.split("\\W")) {
			wordsList.add(word);
		}
				
		return wordsList;
	}
		
	public Date transformToDate(String date) throws ParseException{
		//String beforeT = date.split("T")[0];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date theDate = sdf.parse(date);
					
		return theDate;
				
	}
	
}
