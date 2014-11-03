/**
 * @(#) MenuItem.java
 */

package game;

import lombok.Data;

@Data
public abstract class MenuItem {
	
	protected String name;

	protected ItemQuality quality;
	
	protected int price;

}
