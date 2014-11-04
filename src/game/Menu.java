/**
 * @(#) Menu.java
 */

package game;

import java.util.List;
import lombok.Data;
import java.util.ArrayList;

/**
 * @(#) Menu.java
 */
@Data
public class Menu {
	private List<Dish> dishes;

	private java.util.List<Beverage> beverages;
	
	private String[] dishNames = {"Chicken","Fries","Burger","Fish","Steak"};
	private String[] beveragehNames = {"Coke","Juice","Vana Tallinn","Lemonade","Milk"};
	
	
	private Beverage beverage;
	
	public Menu( Chef chef, Barman barman ){
		dishes = new ArrayList<Dish>();
		beverages = new ArrayList<Beverage>();
		createMenu(chef, barman);
	}
	
	private void createMenu( Chef chef, Barman barman ){
		for(int i=0; i<5; i++){
			dishes.add(new Dish(dishNames[i],chef));
			beverages.add(new Beverage(beveragehNames[i],barman));
		}			
	}
}
