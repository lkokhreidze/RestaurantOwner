/**
 * @(#) Waiter.java
 */

package game;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Waiter extends Employee {
	
	private java.util.Collection<Table> tables;

	private Player player;

	private java.util.Collection<Order> order;
	
	public Waiter (String name){
		this.name = name;
		tables = new ArrayList<Table>();
		order = new ArrayList<Order>(); 
	}


	public void serveClient() {

	}
	public boolean trainEmployee(int budget) {
		if (this.expLevel != ExpLevel.HIGH && budget - this.trainW >= 0) {
			switch (this.expLevel) {
			case LOW:
				this.expLevel = ExpLevel.MEDIUM;
				return true;
			case MEDIUM:
				this.expLevel = ExpLevel.MEDIUM;
				return true;
			}
		}
		return false;
	}
}
