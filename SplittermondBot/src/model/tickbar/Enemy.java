package model.tickbar;

/**
 * an enemy is an item on the tickbar that acts the same way as a player, but
 * implements a short description
 *
 * @author Cornelius
 */
public class Enemy extends Item {

	private String desc;

	public Enemy(int pos, String name, String desc) {
		super(pos, name);
		this.desc = desc;
		this.setType(ItemType.ENEMY);
	}

	// getters and setters
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
