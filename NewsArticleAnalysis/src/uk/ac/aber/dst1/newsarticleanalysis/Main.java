package uk.ac.aber.dst1.newsarticleanalysis;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Diana Silvia Teodorescu
 *
 */
public class Main {
	static URLConnectionReader urlConnect = new URLConnectionReader();
	
/**
 * Main method that calls the connection
 * @param args
 * @throws Exception
 */

	public static void main(String[] args) throws Exception {
		int rows;
		int start;
		String searchTerm;
		Scanner scan = new Scanner(System.in);
		System.out.println("what is the search term of choice?: ");
		searchTerm =  scan.nextLine();
		System.out.println("How many articles to download(no of rows)?: ");
		try{
		rows = scan.nextInt();
		
		}
		catch(InputMismatchException e){
			System.out.println("Not an Integer try again: ");
			scan.nextLine();
			rows = scan.nextInt();	
		}
		System.out.println("Where should it start from?: ");
		try { 
		start = scan.nextInt();
		
		}
		catch(InputMismatchException e){
		System.out.println("Not an Integer try again: ");
		scan.nextLine();
		start = scan.nextInt();
		}
		
		urlConnect.getArticle(searchTerm, rows, start);
		
		//urlConnect.searchArticles();
	}



}
