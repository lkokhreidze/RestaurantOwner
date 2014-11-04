/**
 * @(#) MenuItem.java
 */

package game;

import lombok.Data;

/**
 * @(#) MenuItem.java
 */
@Data
public abstract class MenuItem {
	
	protected String name;

	protected ItemQuality quality;
	
	protected int price;

}
