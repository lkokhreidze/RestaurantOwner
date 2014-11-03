/**
 * @(#) Client.java
 */

package game;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Client extends Person {
	private int volumeConsumed;

	private int calorieConsumed;

	

	private Restaurant restaurant;

	private int bill;

	private int phoneNumber;

	private int taxCode;

	java.util.List<Order> orders;

	private Table table;

    public Client(){
        orders = new ArrayList<Order>();
    	bill = 0;
    }

	public void makeOrder() {
		
	}

}
