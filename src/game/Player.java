/**
 * @(#) Player.java
 */

package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import lombok.Data;

@Data
public class Player {
	private String name;

	private int moneyEarned;

	private int rank;

	private java.util.Collection<Waiter> waiters;

	private java.util.Collection<Employee> employees;

	private Restaurant restaurant;

	private PlayerStatistics statistics;

	private MenuItem menu;
	
	private String path;
	
	public void enterName() throws IOException  {
        System.out.print("Enter Name: ");
        name = ConsoleReader.readLine();
        System.out.println(MessageFormat.format("Hello {0} you became a new restaurant owner", name));
        System.out.println(MessageFormat.format("Please provide absolute file path for creating statistics data (Example: C:/Stats.txt). Statistics will be available after the end of the game.", name));
        path = ConsoleReader.readLine();
        restaurant = new Restaurant(path);
	}

}
