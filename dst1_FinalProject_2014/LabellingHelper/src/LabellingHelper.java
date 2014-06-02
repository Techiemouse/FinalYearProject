import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * @author Diana Silvia Teodorescu
 *
 */
public class LabellingHelper {

	String theInput = "";
	DatabaseAccess database = new DatabaseAccess();
	Connection db = database.startConnection();
	ArrayList<String> searchTerms = new ArrayList<String>(Arrays.asList(
			"investigation", "crime", "arrested", "police"));
	
	/**
	 * Method that starts the menu that offers the user the option to tag
	 * random articles with the corresponding domain
	 * 
	 * @throws SQLException
	 */
	public void optionMenu() throws SQLException {
		/*
		 * min and max are the limits of the article table
		 * if more articles are added the max parameter will need to change correspondingly 
		 */
		int min = 1;
		int max = 16095;
		int randomID = min + (int) (Math.random() * ((max - min) + 1));
		Scanner keyboard = new Scanner(System.in);

		if (!theInput.equals("0")) {
			System.out.println("enter 0 to get article (or # to exit): ");
			theInput = keyboard.nextLine();
		}

		if (theInput.equals("0")) {
			//this loop makes sure the articles are taken from all search terms
			for (int i = 0; i < searchTerms.size(); i++) {
				database.getArticleID(db, searchTerms.get(i), randomID);
			}
			System.out.println("enter a domain input: ");
			theInput = keyboard.nextLine();
			try {
				int numberInput = Integer.parseInt(theInput);
				/*
				 * if the input is between 1 and 8 the domain will be labelled
				 * with that domain. the numbers represent the domain IDs from
				 * the domain table in the database
				 */
				while ((numberInput >= 1) && (numberInput <= 8)) {

					System.out.println(" got domain: " + numberInput
							+ " for article " + randomID);
					// get input and add in articledomain junction table
					database.insertDomain(randomID, numberInput, db);
					System.out.println("other domain? ");
					theInput = keyboard.nextLine();
					numberInput = Integer.parseInt(theInput);
				}
				optionMenu();

			} catch (NumberFormatException nFE) {

				optionMenu();
			}

		} else if (theInput.equals("#")) {
			System.out.println("Loop over. Goodbye!");
			
		}

	}

}
