package uk.ac.aber.dst1.newsarticleanalysis;

import java.util.ArrayList;

/**
 * 
 * @author Diana Silvia Teodorescu
 * @version 1.0
 */

public class ArticleObject {
	private String pID;
	private String region;
	private String articleID;
	private String articleTitle;
	private String articleAbstract;
	private int textWordCount;
	private String articleText;
	private String issueDate;
	ArrayList<String> verbList = new ArrayList<String>();
	ArrayList<String> nounList = new ArrayList<String>();
	private int verbCount;
	private String page;
	private int nounCount;
	private String publicationTitle;
	private String publicationPID;
	private String domain;
	ArrayList<String> searchTerms = new ArrayList<String>();

	public ArticleObject() {

	}

	public ArticleObject(String theTitle, String theText,
			String thePublication, ArrayList<String> theWords) {

		articleTitle = theTitle;
		articleText = theText;
		publicationTitle = thePublication;

	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
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

	public ArrayList<String> getVerbList() {
		return verbList;
	}

	public void setVerbList(ArrayList<String> verbList) {
		this.verbList = verbList;
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

	public String getPublicationPID() {
		return publicationPID;
	}

	public void setPublicationPID(String publicationPID) {
		this.publicationPID = publicationPID;
	}

	public String getArticleAbstract() {
		return articleAbstract;
	}

	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	public String getPID() {
		return pID;
	}

	public void setPID(String pID) {
		this.pID = pID;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getpID() {
		return pID;
	}

	public void setpID(String pID) {
		this.pID = pID;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getTextWordCount() {
		return textWordCount;
	}

	public void setTextWordCount(int textWordCount) {
		this.textWordCount = textWordCount;
	}

	public int getVerbCount() {
		return verbCount;
	}

	public void setVerbCount(int verbCount) {
		this.verbCount = verbCount;
	}

	public ArrayList<String> getNounList() {
		return nounList;
	}

	public void setNounList(ArrayList<String> nounList) {
		this.nounList = nounList;
	}

	public int getNounCount() {
		return nounCount;
	}

	public void setNounCount(int nounCount) {
		this.nounCount = nounCount;
	}


}
