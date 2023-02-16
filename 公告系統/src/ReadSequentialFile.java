import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
//ÅªÀÉ
public class ReadSequentialFile {
	private static ObjectInputStream input;
	private static PostSerializable record;

	// enable user to select file to open
	public static void openFile() {
		try // open file
		{
			input = new ObjectInputStream(Files.newInputStream(Paths.get("Text.ser")));
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
			System.exit(1);
		}
	}

	// read record from file
	public static void readRecords() {
		try {
			record = (PostSerializable) input.readObject();
		} catch (EOFException endOfFileException) {
			System.out.printf("%nNo more records%n");
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println("Invalid object type. Terminating.");
		} catch (IOException ioException) {
			System.err.println("Error reading from file. Terminating.");
		}
	} // end method readRecords

	// close file and terminate application
	public static void closeFile() {
		try {
			if (input != null)
				input.close();
		} catch (IOException ioException) {
			System.err.println("Error closing file. Terminating.");
			System.exit(1);
		}
	}

	public static PostSerializable getPost() {
		return (record != null) ? record : null;
	}
}
