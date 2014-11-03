/**
 * @(#) Baverage.java
 */

package game;

import lombok.Data;

@Data
public class Beverage extends MenuItem {
	private static int highQualityBeverageCost = 3;
	private static int lowQualityBeverageCost = 1;
	private static int volume = 100;	
	private Barman barman;
	
	public Beverage(String name, Barman barman){
		this.name = name;
		this.barman = barman;
	}

	public static int getHighQualityBeverageCost() {
		return highQualityBeverageCost;
	}

	public static int getlowQualityBeverageCost() {
		return lowQualityBeverageCost;
	}

	public static int getVolume() {
		return volume;
	}

}
