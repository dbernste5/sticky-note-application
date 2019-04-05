package stickies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User
{
	private static int lastUserID = 1; //will be auto-incremented, generate from file
	//instance variables
	private int userID; 
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	
	//reads the last customer ID from a file
	public static void initializeUserID(String filename) throws FileNotFoundException
	{
		File file = new File(filename);
		Scanner fileRead = new Scanner(file);

		lastUserID = fileRead.nextInt();
		fileRead.close();
	}

	//write the last customer ID to the file
	public static void saveLastUserID(String filename) throws FileNotFoundException
	{
		File file = new File(filename);
		PrintWriter fileWrite = new PrintWriter(filename);

		fileWrite.println(lastUserID);
		fileWrite.close();
	}

	
	//constructor
	public User(String username, String password, String firstname, String lastname, String email, String phone )
	{
		//validate username, password, email? just in GUI?
				
		//generate userID
		userID= ++lastUserID;
		this.username=username;
		this.password=password;
		this.firstName=firstname;
		this.lastName=lastname;
		this.email=email;
		this.phone=phone;
	}
	
	public User()
	{
		this.userID = ++lastUserID;
	}
	public static int getLastUserID() {
		return lastUserID;
	}

	public static void setLastUserID(int lastUserID) {
		User.lastUserID = lastUserID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//overload, phone is optional
	public User(String username, String password, String firstname, String lastname, String email )
	{
		this(username,  password,firstname, lastname, email, null);
	}

	public boolean validateEmail(String email)
	{
		String emailValidator = "^[a-zA-Z0-9_+&*-] + (?:\\.[a-zA-Z0-9_+&*-]+ )*@(?:[a-zA-Z0-9-]+\\.) + [a-zA-Z]{2, 7}$";
		return email.matches(emailValidator);
	}
	
	//getters
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getPhoneNum()
	{
		return phone;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public int getUserID()
	{
		return userID;
	}

	
	//equals
	@Override
	public boolean equals(Object o)
	{
		if(o==null)
		{
			return false;
		}
		
		if(this==o)
		{
			return true;
		}
		
		if(o instanceof User)
		{
			User u = (User)o;
			return this.userID==u.userID;
		}
		else
		{
			return false;
		}
	}
}
