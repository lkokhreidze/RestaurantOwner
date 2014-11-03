/**
 * @(#) Client.java
 */

package game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Client extends Person {

	private int bill;

	private int phoneNumber;

	private int taxCode;

	java.util.List<Order> orders;
	
	int[] beverageCount = { 0, 0, 0, 0, 0 };
	
	int[] dishCount = { 0, 0, 0, 0, 0 };

	public Client(String name) {
		this.name = name;
		orders = new ArrayList<Order>();
		bill = 0;
	}

	public int getAverageCaloriesConsumed() {
		return Dish.getCalories() * orders.size() / orders.size();
	}

	public int getAverageVolumeConsumed() {
		return Beverage.getVolume() * orders.size() / orders.size();
	}

	public int[] getOrderedDishes() {

		for (Order order : orders) {
			switch (order.getDish().getName()) {
			case "Chicken":
				dishCount[0]++;
				break;
			case "Fries":
				dishCount[1]++;
				break;
			case "Burger":
				dishCount[2]++;
				break;
			case "Fish":
				dishCount[3]++;
				break;
			case "Steak":
				dishCount[4]++;
				break;
			}
		}
		return dishCount;
	}

	public int[] getOrderedBeverages() {

		for (Order order : orders) {
			switch (order.getBeverage().getName()) {
			case "Coke":
				beverageCount[0]++;
				break;
			case "Juice":
				beverageCount[1]++;
				break;
			case "Vana Tallinn":
				beverageCount[2]++;
				break;
			case "Lemonade":
				beverageCount[3]++;
				break;
			case "Milk":
				beverageCount[4]++;
				break;
			}
		}
		return beverageCount;
	}

	public int totalSpending() {
		int totalSpending = 0;
		for (Order order : orders) {
			totalSpending += order.calculateIncome();
		}
		return totalSpending;
	}

}
