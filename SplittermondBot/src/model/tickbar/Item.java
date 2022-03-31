package model.tickbar;

public class Item {

	private int pos;
	private String name;
	private ItemType type;
	
	/**
	 * generate a new Item on the Tickbar
	 * @param pos position on the tickbar
	 * @param name display name
	 */
	public Item(int pos, String name) {
		this.pos = pos;
		this.name = name;
	}


	 // getters and setters
	 
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}
	
	//check functions for convenience
	
	public boolean isPlayer() {
		return this.type == ItemType.PLAYER;
	}
	
	public boolean isEnemy() {
		return this.type == ItemType.ENEMY;
	}
	
	public boolean isMessage() {
		return this.type == ItemType.MSG;
	}
}
