/**
 * @(#) Menu.java
 */

package game;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Menu {
	private java.util.List<Dish> dishes;

	private java.util.List<Beverage> beverages;
	
	private String[] dishNames = {"one","two","three","four","five"};
	
	public Menu(Chef chef, Barman barman){
		dishes = new ArrayList<Dish>();
		beverages = new ArrayList<Beverage>();
		createMenu(chef, barman);
	}
	
	private void createMenu(Chef chef, Barman barman){
		for(int i=0; i<5; i++){
			dishes.add(new Dish(dishNames[i],chef));
			beverages.add(new Beverage(dishNames[i],barman));
		}			
	}
}
