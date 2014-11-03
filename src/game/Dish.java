/**
 * @(#) Dish.java
 */

package game;

import lombok.Data;

@Data
public class Dish extends MenuItem {
	private int calories;
	private Chef chef;
	
	public Dish(String name){
		this.name = name;
	}
}
