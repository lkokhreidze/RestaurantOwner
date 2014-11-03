/**
 * @(#) Employee.java
 */

package game;

import lombok.Data;

@Data
public abstract class Employee extends Person {
	protected static int trainCB = 1200;	//barman and chef training cost
	protected static int trainW = 800;	   //waiter training cost
	protected int salary;

	protected ExpLevel expLevel = ExpLevel.LOW;

	protected Player player;

	public boolean trainEmployee(int budget) {
		if (this.expLevel != ExpLevel.HIGH && budget - this.trainCB >= 0) {
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