import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
//ºg¿…
public class CreateTextFile {
	private static Formatter output; // outputs text to a file

	// open file clients.txt
	public static void openFile(String fileName) {
		try {
			output = new Formatter(fileName); // open the file
		} catch (SecurityException securityException) {
			System.err.println("Write permission denied. Terminating.");
			System.exit(1); // terminate the program
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file. Terminating.");
			System.exit(1); // terminate the program
		}
	}

	// add records to file
	public static void addRecords(PostSerializable record) {

		try {
			output.format("%b%n%s%n%s", record.getIsLike(), record.getEditTime().toString(),  record.getContent());

		} catch (FormatterClosedException formatterClosedException) {
			System.err.println("Error writing to file. Terminating.");
		} catch (NoSuchElementException elementException) {
			System.err.println("ºg§J•¢±—");
		}

	}

	// close file
	public static void closeFile() {
		if (output != null) {
			output.close();
		}
	}
}
