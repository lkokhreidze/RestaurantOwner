package game;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @(#) ConsoleReader.java
 */
public class ConsoleReader {
	public static String readLine( ) throws IOException{		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}
}
