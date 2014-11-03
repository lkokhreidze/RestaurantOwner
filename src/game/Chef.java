/**
 * @(#) Chef.java
 */

package game;

import lombok.Data;

@Data
public class Chef extends Employee {
	private int taxCode;

	public Chef(String name) {
		this.name = name;
	}

}
