package model.tickbar;

import net.dv8tion.jda.api.entities.User;

public class Player extends Item {
	
	private User userid;
	
	/**
	 * create a new player on the tickbar. Players are referenced by the jda user object
	 * @param pos pos on the tickbar
	 * @param name (nick)name for displayment
	 * @param user discord user entity
	 */
	public Player(int pos, String name, User user) {
		//initialise the item and set the type to player
		super(pos, name);
		this.userid = user;
		this.setType(ItemType.PLAYER);
	}

	public User getUser() {
		return userid;
	}

	public void setUser(User user) {
		this.userid = user;
	}

	
	
}
