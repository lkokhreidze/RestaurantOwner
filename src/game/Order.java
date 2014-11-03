/**
 * @(#) Orders.java
 */

package game;

import java.util.Random;

import lombok.Data;

@Data
public class Order {
	private Client client;

	private Waiter waiter;

	private Dish dish;

	private Beverage beverage;

	public Order(Dish dish, Beverage beverage) {
		this.dish = dish;
		this.beverage = beverage;
	}

	public int calculateSatisfactory(Waiter waiter) {
		this.waiter = waiter;
		int clientSatisfaction = 0;
		int rnd = new Random().nextInt(10);
		
		if (waiter.getExpLevel() == ExpLevel.HIGH) 
			if (rnd <= 9)
				clientSatisfaction += 1;
		if(waiter.getExpLevel() == ExpLevel.MEDIUM)
			if(rnd <= 8)
				clientSatisfaction +=1;
		if(waiter.getExpLevel() == ExpLevel.LOW)
			if(rnd <= 6)
				clientSatisfaction +=1;

		return clientSatisfaction;
	}

	public void calculateFeedback() {

	}

	public int calculateIncome() {
		return this.dish.getPrice() + this.beverage.getPrice();
	}

}
