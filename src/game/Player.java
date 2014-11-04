/**
 * @(#) Player.java
 */

package game;

import lombok.Data;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/**
 * @(#) Player.java
 */
@Data
public class Player {
	private Restaurant restaurant;
	private String name;
	
	private String path;
	
	public void enterName( ) throws IOException  {
        System.out.print("Enter Name: ");
        name = ConsoleReader.readLine();
        System.out.println(MessageFormat.format("Hello {0} you became a new restaurant owner", name));
        System.out.println(MessageFormat.format("Please provide absolute file path for creating statistics data (Example: C:/temp/Stats.txt). Statistics will be available after the end of the game.", name));
        path = ConsoleReader.readLine();
        restaurant = new Restaurant(name,path);
	}

}
