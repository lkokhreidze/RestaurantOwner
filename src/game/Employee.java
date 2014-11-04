/**
 * @(#) Employee.java
 */

package game;

import lombok.Data;

/**
 * @(#) Employee.java
 */
@Data
public abstract class Employee extends Person {
	protected static int trainCB = 1200;	//barman and chef training cost
	protected static int trainW = 800;	   //waiter training cost
	protected int salary = 300;

	protected ExpLevel expLevel = ExpLevel.LOW;


	public boolean trainEmployee( int budget ) {
		if (this.expLevel != ExpLevel.HIGH && budget - this.trainCB >= 0) {
			switch (this.expLevel) {
			case LOW:
				this.expLevel = ExpLevel.MEDIUM;
				this.salary = 400;
				return true;
			case MEDIUM:
				this.expLevel = ExpLevel.HIGH;
				this.salary = 500;
				return true;
				default:
					return false;
			}
		}
		return false;
	}


}
