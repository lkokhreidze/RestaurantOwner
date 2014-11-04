/**
 * @(#) Table.java
 */

package game;

import lombok.Data;

/**
 * @(#) Table.java
 */
@Data
public class Table
{
	private int number;
	
	private boolean isTableAssigned = false;
	
	private boolean tableOccupied = false;
	
	private Waiter waiter;

    private int income;
	
	public Table( int number ){
		this.number = number;
	}
	
}
