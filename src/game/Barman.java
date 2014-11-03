/**
 * @(#) Barman.java
 */

package game;

import lombok.Data;

@Data
public class Barman extends Employee {
	
	public Barman(String name){
		this.name = name;
	}
}
