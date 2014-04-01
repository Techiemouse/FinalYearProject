import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;




public class LabellingHelper {

	
	DatabaseAccess database = new DatabaseAccess();
	Connection db = database.startConnection();
	
	public void optionMenu() throws SQLException{
		ArrayList<String> articleDetails = new ArrayList<String>();
		for (int i=1; i<54; i++){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter an input: ");
		String theInput = keyboard.nextLine();
	//	while (theInput!=null){
		
			if (theInput.equals("")){
				//articleDetails =database.getArticle(db);
				
				database.getArticleID(db, "crime",i);
			//printArray(articleDetails);
			
			System.out.println(" ---------------------------------- ");
				
			}
			else {
				//get input and add in domain table and junction table
			}
		}
		//}
			
	    
		
	}
	
	public void printArray(ArrayList<String> array) {

		
		System.out.println("ID: " + array.get(0));
		System.out.println("Title: " + array.get(1));
		System.out.println("Text: " + array.get(2));
		
	}
	
}
