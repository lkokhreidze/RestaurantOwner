/**
 * @(#) Baverage.java
 */

package game;

import lombok.Data;

@Data
public class Beverage extends MenuItem {
	
	private int volume;
	
	private Barman barman;
	
	public Beverage(String name){
		this.name = name;
	}

}
