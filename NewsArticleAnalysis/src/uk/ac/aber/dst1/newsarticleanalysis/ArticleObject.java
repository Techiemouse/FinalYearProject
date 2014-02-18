package uk.ac.aber.dst1.newsarticleanalysis;

import java.util.ArrayList;
import java.util.Date;

public class ArticleObject {
	private Date issueDate;
	private String articleText;
	private String articleID;
	private String articleTitle;
	private String publicationTitle;
	ArrayList<String> verbList = new ArrayList<String>();
	ArrayList<String> wordList = new ArrayList<String>();
	ArrayList<String> searchTerms = new ArrayList<String>();
	private String domain;
	
	
	public ArticleObject(Date theDate, String theTitle, String theText, String thePublication, ArrayList<String> theWords ){
		issueDate = theDate;
		articleTitle = theTitle;
		articleText = theText;
		publicationTitle = thePublication;
		wordList=theWords;
		
	}
	
	
	
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public String getArticleText() {
		return articleText;
	}
	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}
	public String getArticleID() {
		return articleID;
	}
	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getPublicationTitle() {
		return publicationTitle;
	}
	public void setPublicationTitle(String publicationTitle) {
		this.publicationTitle = publicationTitle;
	}
	
	//split a string and get the verblist by calling the get verbs method (another class)
	public ArrayList<String> getVerbList() {
		return verbList;
	}
	public void setVerbList(ArrayList<String> verbList) {
		this.verbList = verbList;
	}
	public ArrayList<String> getWordList() {
		return wordList;
	}
	public void setWordList(ArrayList<String> wordList) {
		this.wordList = wordList;
	}
	public ArrayList<String> getSearchTerms() {
		return searchTerms;
	}
	public void setSearchTerms(ArrayList<String> searchTerms) {
		this.searchTerms = searchTerms;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}



	@Override
	public String toString() {
		return "ArticleObject [issueDate=" + issueDate + ", articleText="
				+ articleText + ", articleID=" + articleID + ", articleTitle="
				+ articleTitle + ", publicationTitle=" + publicationTitle
				+ ", wordList=" + wordList + "]";
	}
	
	
	
	
}
