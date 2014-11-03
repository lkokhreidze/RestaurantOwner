/**
 * @(#) Orders.java
 */

package game;

import java.util.Random;

import lombok.Data;

@Data
public class Order {

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
		Random rnd = new Random();
		int satisfaction = rnd.nextInt(10);
		// Service satisfaction
		if (waiter.getExpLevel() == ExpLevel.HIGH)
			if (satisfaction <= 9)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		if (waiter.getExpLevel() == ExpLevel.MEDIUM)
			if (satisfaction <= 8)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		if (waiter.getExpLevel() == ExpLevel.LOW)
			if (satisfaction <= 6)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		satisfaction = rnd.nextInt(10);
		if(dish.getQuality() == ItemQuality.HIGH){
			satisfaction+=2;
			for(int i=0; i<dish.getPrice()-Dish.gethighQualityDishCost()%3; i++){
				satisfaction--;
			}
		}
		else{
			for(int i=0; i<dish.getPrice()-Dish.getlowQualityDishCost()%3; i++){
				satisfaction--;
			}
		}
		// Dish quality satisfaction
		if (dish.getChef().getExpLevel() == ExpLevel.HIGH)
			if (satisfaction <= 9)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		if (dish.getChef().getExpLevel() == ExpLevel.MEDIUM)
			if (satisfaction <= 8)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		if (dish.getChef().getExpLevel() == ExpLevel.LOW)
			if (satisfaction <= 6)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		satisfaction = rnd.nextInt(10);
		satisfaction = dish.getQuality() == ItemQuality.HIGH ? satisfaction + 2
				: satisfaction;
		// Beverage quality satisfaction
		if(beverage.getQuality() == ItemQuality.HIGH){
			satisfaction+=2;
			for(int i=0; i<beverage.getPrice()-Beverage.getHighQualityBeverageCost()%3; i++){
				satisfaction--;
			}
		}
		else{
			for(int i=0; i<beverage.getPrice()-Beverage.getlowQualityBeverageCost()%3; i++){
				satisfaction--;
			}
		}
		if (beverage.getBarman().getExpLevel() == ExpLevel.HIGH)
			if (satisfaction <= 8)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		if (beverage.getBarman().getExpLevel() == ExpLevel.MEDIUM)
			if (satisfaction <= 6)
				clientSatisfaction++;
			else
				clientSatisfaction--;
		if (beverage.getBarman().getExpLevel() == ExpLevel.LOW)
			if (satisfaction <= 4)
				clientSatisfaction++;
			else
				clientSatisfaction--;

		return clientSatisfaction;
	}

	public void calculateFeedback() {

	}

	public int calculateIncome() {
		return this.dish.getPrice() + this.beverage.getPrice();
	}

}
