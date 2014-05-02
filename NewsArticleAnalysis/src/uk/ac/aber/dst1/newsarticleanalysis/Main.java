package uk.ac.aber.dst1.newsarticleanalysis;


import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Diana Silvia Teodorescu
 * 
 */
public class Main {
	static URLConnectionReader urlConnect = new URLConnectionReader();
	static MLTable mlTable = new MLTable();
	static MachineLearning machineLearning=new MachineLearning();

	/**
	 * Main method that gives you the options of the system. 
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		int go = 0;
		while (go == 0){
			printInitialMenu();
			Scanner scan = new Scanner(System.in);
			String choice=scan.nextLine();
			switch (choice) {			
			 case "1": downloadMenu();
			   break;
			 case "2": buildMLtables();
			   break;
			 case "3": getMLresults();
			   break;
			 case "0": System.out.println("Thank you! Goodbye");
				 go=1;
			   break;
			 default: System.out.println("Invalid choice");
			 break;
			}
		}
	
	}
	/**
	 * The method prints the option of the menu
	 */
	public static void printInitialMenu(){
		System.out.println("What would you like to do?");
		System.out.println("1 - Search and download more articles");
		System.out.println("2 - Build the ML tabels");
		System.out.println("3 - Get the ML results");
		System.out.println("0 - Quit");
	}
	/**
	 * The method searches, processes and downloads new articles from the server using as user input
	 * the search term, the number of rows required and at what row of the response the download to start
	 * @throws Exception
	 */
	public static void downloadMenu() throws Exception{
		int rows=0;
		int start =0;	
		
		String searchTerm;
		Scanner scan = new Scanner(System.in);
		
		 System.out.println("what is the search term of choice?: ");
		 searchTerm = scan.nextLine();
		 while(rows==0){
		 System.out.println("How many articles to download(no of rows)?: ");
		 try{ 
		 rows = scan.nextInt();
		
		 } catch(InputMismatchException e){
		 System.out.println("Not an Integer try again: "); 
		 scan = new Scanner(System.in);
		 }
		 }

		 while(true){
		 System.out.println("Where should it start from?: "); 
		 try { 
			start = scan.nextInt();
		 break;
		 } catch(InputMismatchException e){
		 System.out.println("Not an Integer try again: "); 
		 scan = new Scanner(System.in);
		 }
		 }
		 System.out.println("you are downloading, "+searchTerm+" this many "+rows+ " starting : "+start);
		 urlConnect.getArticle(searchTerm, rows, start);
		

		 
	}

	/**
	 * The function will build the Machine Learning tables, this function is very time consuming 
	 * as it does all the tables at once
	 * @throws SQLException
	 */
	public static void buildMLtables() throws SQLException{
		System.out.println("We are building tables");
		mlTable.createTrainingTable();
	}
	/**
	 * The function prints out on the command line the Machine Learning results
	 */
	public static void getMLresults(){
		System.out.println("We are doing ml results");
		//machineLearning.getMLResults();
	}

}
