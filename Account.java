// Libraries for I/O File
import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.*;

// 'Account' class will help the application keep track user's accounts
// by storing, loading, checking them
public class Account {
	// A vector of 'Account' objects
	Vector<Account> account = new Vector<Account>();
	// Data fields for an 'Account' object
	String username;
	String password;
	String CWID;
	
	// Default constructor of 'Account' class
	public Account() {}
	
	// Non-default constructor of 'Account' class
	// This constructor will store the value for data fields
	public Account(String u, String p, String id) {
		username = u;
		password = p;
		CWID = id;
	}
	
	// 'createAccount' method will store the registered accounts
	// by writing all information to the file named 'users.txt'
	public void createAccount(String userName, String passWord, String ID) {
		try {
			FileOutputStream output = new FileOutputStream("D:\\users.txt", true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			writer.write(userName + " " + passWord + " " + ID);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch(Exception e) {
			System.out.println("Message: " + e);
		}
	}
	
	// 'readAccount' method will read all information related to the account line by line from 'user.txt'
	// then save them into a vector of 'Account' objects called 'account'
	public void readAccount() {
		String[] eachString = new String[3];
		String u;
		String p;
		String id;
		String readString;
		Path file = Paths.get("D:\\users.txt");
		InputStream input = null;
		try {
			input = Files.newInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			readString = reader.readLine();
			while (readString != null) {
				eachString = readString.split(" ");
				u = eachString[0];
				p = eachString[1];
				id = eachString[2];
				Account newAccount = new Account(u, p, id);
				account.addElement(newAccount);
				readString = reader.readLine();
			}
			input.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	// 'getID' method will return an ID number as a string related to account's username and password
	public String getID(String u, String p) {
		String id = null;
		for (int i = 0; i < account.size(); i++) {
			if (account.get(i).username.equals(u) && account.get(i).password.equals(p)) {
				id = account.get(i).CWID;
			}
		}
		return id;
	}
	
	// 'checkAccount' method will check if the account does exist or not
	// return 'true' if the account exists; otherwise, return 'false'
	public boolean checkAccount(String u, String p) {
		for (int i = 0; i < account.size(); i++) {
			if (account.get(i).username.equals(u) && account.get(i).password.equals(p)) {
				return true;
			}
		}
		return false;
	}
}
