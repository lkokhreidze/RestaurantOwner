/**
 * @(#) Client.java
 */

package game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Client extends Person {

	private int bill;

	private int phoneNumber;

	private int taxCode;

	java.util.List<Order> orders;

	private Table table;

    public Client(String name){
    	this.name = name;
        orders = new ArrayList<Order>();
    	bill = 0;
    }

    public int getAverageCaloriesConsumed(){
    	return Dish.getCalories() * orders.size()/Dish.getCalories();
    } 
    
    public int getAverageVolumeConsumed(){
    	return Beverage.getVolume() * orders.size()/Beverage.getVolume();
    } 

}
