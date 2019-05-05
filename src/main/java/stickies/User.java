package stickies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User
{
	//instance variables

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
		
	//constructor
	public User(String username, String password, String firstname, String lastname, String email, String phone )
	{
		//validate username, password, email? just in GUI?
				
		this.username=username;
		this.password=password;
		this.firstName=firstname;
		this.lastName=lastname;
		this.email=email;
		this.phone=phone;
	}
	
	public User()
	{
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
	
}
