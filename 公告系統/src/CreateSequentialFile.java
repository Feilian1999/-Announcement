import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
//打開檔案，寫檔，關檔
public class CreateSequentialFile {
	private static ObjectOutputStream output; // outputs data to file

	// open file clients.ser
	public static void openFile() {
		try {
			output = new ObjectOutputStream(Files.newOutputStream(Paths.get("Text.ser")));
		} catch (IOException ioException) {
			System.err.println("Error opening file. Terminating.");
			System.exit(1); // terminate the program
		}
	}

	// add records to file
	public static void addRecords(PostSerializable record) {
		try {
			// serialize record object into file
			output.writeObject(record);
		} catch (NoSuchElementException elementException) {
			System.err.println("Invalid input. Please try again.");
		} catch (IOException ioException) {
			System.err.println("Error writing to file. Terminating.");
		}
	}

	// close file and terminate application
	public static void closeFile() {
		try {
			if (output != null)
				output.close();
		} catch (IOException ioException) {
			System.err.println("Error closing file. Terminating.");
		}
	}
}
