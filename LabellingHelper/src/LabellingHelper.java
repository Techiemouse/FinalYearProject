import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;




public class LabellingHelper {

	String theInput="";
	DatabaseAccess database = new DatabaseAccess();
	Connection db = database.startConnection();
	ArrayList<String> searchTerms = new ArrayList<String>(Arrays.asList(
			"investigation","crime","arrested","police"));
	public void optionMenu() throws SQLException{
		
		int min=1;
		int max=15913;
		int randomID = min + (int)(Math.random() * ((max - min) + 1));
		Scanner keyboard = new Scanner(System.in);
		//int randomIDg=15976;
	
		if (!theInput.equals("0")){
		System.out.println("enter an input: ");
		theInput = keyboard.nextLine();
		}
		
		if (theInput.equals("0")){
			for (int i = 0; i < searchTerms.size(); i++) {
				database.getArticleID(db, searchTerms.get(i),randomID);
			}
				System.out.println("enter a domain input: ");
				theInput = keyboard.nextLine();
				try {
					int numberInput = Integer.parseInt(theInput);
				    while ((numberInput>=1)&&(numberInput<=8)){
				    	
				    	System.out.println(" got domain: "+numberInput+ " for article " +randomID);
				    	database.insertDomain(randomID, numberInput, db);
				    	 System.out.println ("other domain? ");
				    	 theInput = keyboard.nextLine();
				    	 numberInput = Integer.parseInt(theInput);
					}
				   optionMenu();
				    
				}
				catch(NumberFormatException nFE) {
				    //System.out.println("Not an Integer");
				    optionMenu();
				}
				
				
	}
			else if (theInput.equals("#")){
				System.out.println("Loop over. Goodbye!");
				//get input and add in domain table and junction table
			}
		
		
			
	    
		
	}
	
	public void printArray(ArrayList<String> array) {

		
		System.out.println("ID: " + array.get(0));
		System.out.println("Title: " + array.get(1));
		System.out.println("Text: " + array.get(2));
		
	}
	
}
