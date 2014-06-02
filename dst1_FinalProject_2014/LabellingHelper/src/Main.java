import java.sql.SQLException;


public class Main {
	/**
	 * This is the main method that starts the Labelling Helper application
	 * @param args
	 * @throws SQLException - thown when the SQL server throws an error
	 */
	public static void main(String[] args) throws SQLException {

		LabellingHelper label = new LabellingHelper();
		label.optionMenu();
	}

}
