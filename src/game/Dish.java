/**
 * @(#) Dish.java
 */

package game;

import lombok.Data;

@Data
public class Dish extends MenuItem {
	private static int calories = 250;
	private Chef chef;
	private static int highQualityDishCost = 10;
	private static int lowQualityDishCost = 3;
	
	public Dish(String name, Chef chef){
		this.name = name;
		this.chef = chef;
	}

	public static int gethighQualityDishCost() {
		return highQualityDishCost;
	}
	
	public static int getlowQualityDishCost() {
		return lowQualityDishCost;
	}

	public static int getCalories() {
		return calories;
	}

}
