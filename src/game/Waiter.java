/**
 * @(#) Waiter.java
 */

package game;

import java.util.Collection;
import lombok.Data;
import java.util.ArrayList;

/**
 * @(#) Waiter.java
 */
@Data
public class Waiter extends Employee {
	
	private Collection<Table> tables;

	
	public Waiter( String name ){
		this.name = name;
		tables = new ArrayList<Table>();
		this.salary = 200;
	}


	public boolean trainEmployee( int budget ) {
		if (this.expLevel != ExpLevel.HIGH && budget - this.trainW >= 0) {
			switch (this.expLevel) {
			case LOW:
				this.expLevel = ExpLevel.MEDIUM;
				this.salary = 300;
				return true;
			case MEDIUM:
				this.expLevel = ExpLevel.HIGH;
				this.salary = 400;
				return true;
			}
		}
		return false;
	}
}
