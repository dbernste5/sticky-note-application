package stickies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Sticky {

	// private static int lastStickyID = 0; //will be auto-incremented, generate
	// from file
	private int stickyID;
	private String title;
	private String body;
	private String color;
	private LocalDate dateCreated;

	public Sticky(String title, String body, String color) {

		this.title = title;
		this.body = body;
		this.color = color;
		dateCreated = LocalDate.now();

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/*
	 * public static int getLastStickyID() { return lastStickyID; }
	 */
	public int getStickyID() {
		return stickyID;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}
	/*
	 * //reads the last customer ID from a file public static void
	 * initializeCustomerID(String filename) throws FileNotFoundException { File
	 * file = new File(filename); Scanner fileRead = new Scanner(file);
	 * 
	 * lastStickyID = fileRead.nextInt(); fileRead.close(); }
	 * 
	 * //write the last customer ID to the file public static void
	 * saveLastCustomerID(String filename) throws FileNotFoundException { File file
	 * = new File(filename); PrintWriter fileWrite = new PrintWriter(file);
	 * 
	 * fileWrite.println(lastStickyID); fileWrite.close();
	 * 
	 * }
	 * 
	 */

}
