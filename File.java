// Libraries for I/O File and GUI
import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.*;
import javax.swing.*;

// 'File' class will help the application keep track the Quiz
// by creating, loading, checking it
public class File {
	// A vector of 'File' objects that store questions
	Vector<File> saveQuestions = new Vector<File>();
	// A vector of 'File' objects that load questions
	Vector<File> loadQuestions = new Vector<File>();
	// Data fields for a 'File' object
	String question;
	String choice1;
	String choice2;
	String choice3;
	String choice4;
	String answer;
	// Paths that link to the file
	Path createPath;
	Path checkPath;
	
	// Default constructor
	public File() {}
	
	// Non-default constructor
	// This constructor will store the value of data fields
	public File(String q, String c1, String c2, String c3, String c4, String a) {
		question = q;
		choice1 = c1;
		choice2 = c2;
		choice3 = c3;
		choice4 = c4;
		answer = a;
	}
	
	// 'createFile' method will create a file based on the parameter 'f'
	public boolean createFile(String f) {
		createPath = Paths.get("D:\\" + f);
		try {
			OutputStream createFile = new BufferedOutputStream(Files.newOutputStream(createPath, CREATE_NEW));
			return true;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Can't create since " + f + " is already existent!!", "Error", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}
	
	// 'checkFile' method will check if the file does exist or not based on the parameter 'f'
	// return 'true' if the file exists; otherwise, return 'false'
	public boolean checkFile(String f) {
		checkPath = Paths.get("D:\\" + f);
		if (Files.exists(checkPath)) {
			return true;
		}
		return false;
	}
	
	// 'createQuestions' method will create each question based on the parameters
	// Then store all questions into a vector of 'File' object called 'saveQuestions'
	public void createQuestions(String q, String c1, String c2, String c3, String c4, String a) {
		File newQuestion = new File(q, c1, c2, c3, c4, a);
		saveQuestions.addElement(newQuestion);
	}
	
	// 'deleteQuestions' method will delete a question at the specific index
	public void deleteQuestions(int index) {
		saveQuestions.remove(index);
	}
	
	// 'createQuiz' method will create a Quiz by writing into a file based on the path
	public void createQuiz() {
		try {
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(createPath, APPEND));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			for (int i = 0; i < saveQuestions.size(); i++) {
				writer.write(saveQuestions.get(i).question);
				writer.newLine();
				writer.write(saveQuestions.get(i).choice1 + " " + saveQuestions.get(i).choice2 + " " +
							 saveQuestions.get(i).choice3 + " " + saveQuestions.get(i).choice4 + " " +
							 saveQuestions.get(i).answer);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch(Exception e) {
			System.out.println("Message: " + e);
		}
	}
	
	// 'loadQuiz' method will load a Quiz by reading a file based on the path
	public void loadQuiz(String f) {
		createPath = Paths.get("D:\\" + f);
		String[] loadString = new String[5];
		String q;
		String c1;
		String c2;
		String c3;
		String c4;
		String a;
		String readString;
		InputStream input = null;
		try {
			input = Files.newInputStream(createPath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			readString = reader.readLine();
			while (readString != null) {
				q = readString;
				readString = reader.readLine();
				loadString = readString.split(" ");
				c1 = loadString[0];
				c2 = loadString[1];
				c3 = loadString[2];
				c4 = loadString[3];
				a = loadString[4];
				File openQuestion = new File(q, c1, c2, c3, c4, a);
				loadQuestions.addElement(openQuestion);
				readString = reader.readLine();
			}
			input.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
