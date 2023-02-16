import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
//ÅªÀÉ
public class ReadTextFile {
	private static Scanner input;
	private static PostSerializable record;
	private static boolean isLike;
	private static String editTimeInString;
	private static StringBuilder builder;

	// open file clients.txt
	public static void openFile(String fileName) {
		try {
			input = new Scanner(new File(fileName));
		} catch (IOException ioException) {
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}

	// read record from file
	public static void readRecords() {
		try {
			builder = new StringBuilder();
			for (int row = 1; input.hasNext(); row++) {
				switch (row) {
				case 1:
					isLike = Boolean.valueOf(input.nextLine());
					break;
				case 2:
					editTimeInString = input.nextLine();
					break;
				default:
					builder.append(input.nextLine() + "\n");
					break;
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			try {
				record = new PostSerializable(builder.toString(), isLike, sdf.parse(editTimeInString));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		} catch (NoSuchElementException elementException) {
			System.err.println("File improperly formed. Terminating.");
		} catch (IllegalStateException stateException) {
			System.err.println("Error reading from file. Terminating.");
		}
	} // end method readRecords

	// close file and terminate application
	public static void closeFile() {
		if (input != null)
			input.close();
	}

	public static PostSerializable getPost() {
		return (record != null) ? record : null;
	}
}
