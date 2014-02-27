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

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataManager {
	ArrayList<ArticleObject> array = new ArrayList<ArticleObject>();
	
	
//	public void parsingXML(String input) throws ParserConfigurationException, SAXException, IOException, ParseException{
//
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();		
//		DocumentBuilder builder = null;
//		
//		//System.out.println("input string is  "+input);
//			
//		builder = factory.newDocumentBuilder();
//		InputSource is = new InputSource(new StringReader(input));
//					
//		Document doc = builder.parse(is);
//
//		System.out.println("root tag: "+doc.getDocumentElement().getNodeName());					
//		System.out.println("number of elements is: "+doc.getElementsByTagName("doc").getLength());
//		System.out.println("child nodes: "+doc.getChildNodes().getLength());	
//		NodeList stringList = doc.getElementsByTagName("str");	
//		
//		ArrayList<ArticleObject> array = new ArrayList<ArticleObject>();
//		
//		NodeList dateList = doc.getElementsByTagName("date");
//		for (int i=0; i<dateList.getLength(); i++){
//			ArticleObject artOb = new ArticleObject();
//			
//			Node dNode = dateList.item(i);
//					
//			if (dNode.getNodeType() == Node.ELEMENT_NODE){
//							
//				Element elem = (Element) dNode;
//							
//				//System.out.println("attribute: " + elem.getAttribute("name"));
//				if (elem.getAttribute("name").equals("IssueDate")){
//					
//					//System.out.println("date: " + elem.getTextContent());
//					String artDate = elem.getTextContent();
//					//System.out.println("the date: "+transformToDate(artDate));
//					artOb.setIssueDate(transformToDate(artDate));
//					
//				}
//			}
//				
//			array.add(artOb);
//		}
//		int count = 0;
//		for (int i=0; i<stringList.getLength(); i++){
//			
//			Node lNode = stringList.item(i);
//				
//			if (lNode.getNodeType() == Node.ELEMENT_NODE){
//				
//					
//				Element elem = (Element) lNode;
//					
//				//System.out.println("attribute: " + elem.getAttribute("name"));
//				if (elem.getAttribute("name").equals("ArticleTitle")){
//					
//					//System.out.println("title: " + elem.getTextContent());
//					array.get(count).setArticleTitle(elem.getTextContent());
//				}
//				else if (elem.getAttribute("name").equals("ArticleText")){
//					
//					String article = replaceCharact(elem.getTextContent());						
//					//System.out.println("article is:"+ article);
//							
//					//System.out.println("size of array: "+this.getWordList(article).size());
//					array.get(count).setArticleText(article);
//					array.get(count).setWordList(this.getWordList(article));
//	
//				}
//				else if (elem.getAttribute("name").equals("PublicationTitle")){
//					array.get(count).setPublicationTitle(elem.getTextContent());	
//					count++;
//				}
//								
//		}
				
			
				
		
			
//		}
//		System.out.println(array.get(1).toString());
//		
//	}
			
	public void parsingtheXML(String input) throws ParserConfigurationException, SAXException, IOException{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();		
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(input));					
		Document doc = builder.parse(is);

		//System.out.println("root tag: "+doc.getDocumentElement().getNodeName());					
		//System.out.println("number of elements is: "+doc.getElementsByTagName("doc").getLength());
		if (doc.hasChildNodes()){
			 //getting the list of only the tags with doc in
			for (int i=0; i<doc.getElementsByTagName("doc").getLength();i++){
			printNote(doc.getElementsByTagName("doc").item(i));
			//array.add(printNote(doc.getElementsByTagName("doc").item(i)));
			}
			printArray(array);
		}
	
		
	}
	
	private void printNote(Node tempNode) {
		
		ArticleObject artOb = new ArticleObject();
		// make sure it's element node.
		if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
	 
			// get node name and value	

		
		System.out.println("Node Value =" + tempNode.getTextContent());
		
		
		NodeList articleNodes= tempNode.getChildNodes();
		System.out.println("count child =" + articleNodes.getLength());
		
		for (int count =0; count<articleNodes.getLength(); count++){
			
			if (articleNodes.item(count).hasAttributes()) {
	 
				// get attributes names and values
				NamedNodeMap nodeMap = articleNodes.item(count).getAttributes();
				
				//for (int i = 0; i < nodeMap.getLength(); i++) {
					
					Node node = nodeMap.item(count);
					//System.out.println("attr name : " + node.getNodeName());
					//System.out.println("attr value : " + node.getNodeValue());
	 
					Element elem = (Element) articleNodes.item(count);
					
					//System.out.println("attribute: " + elem.getAttribute("name"));
					if (elem.getAttribute("name").equals("ArticleTitle")){
						
						//System.out.println("title: " + elem.getTextContent());
						artOb.setArticleTitle(elem.getTextContent());
					}
					else if (elem.getAttribute("name").equals("ArticleText")){
						
						String article = replaceCharact(elem.getTextContent());						
						//System.out.println("article is:"+ article);
								
						//System.out.println("size of array: "+this.getWordList(article).size());
						artOb.setArticleText(article);
						artOb.setWordList(this.getWordList(article));
		
					}
					else if (elem.getAttribute("name").equals("PublicationTitle")){
						artOb.setPublicationTitle(elem.getTextContent());	
						
					}
					else if (elem.getAttribute("name").equals("IssueDate")){
						try {
							artOb.setIssueDate(transformToDate(elem.getTextContent()));
						} catch (DOMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (elem.getAttribute("name").equals("ArticleID")){
						artOb.setArticleID(elem.getTextContent());
					}
					else if (elem.getAttribute("name").equals("Page")){
						artOb.setPage(Integer.parseInt(elem.getTextContent()));
					}
					
					//add to array of article objects
					
					//System.out.println(array.get(i).toString());
			
			}
			
			
			}
	 
		//
		//System.out.println(artOb.toString());	
			array.add(artOb);
			System.out.println("Node Name =" + tempNode.getNodeName() + " end");
			//System.out.println("count: "+count);		
			
			
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
	public void printArray(ArrayList<ArticleObject> array){
		
		for(int i = 0; i < array.size(); i++) {   
		    System.out.println(array.get(i));
		}  
	}
	
	public void appentArticles(){
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
	
	public void checkIfSameArticle(){
	
		
	
	}
}
