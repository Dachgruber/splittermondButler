package model;

public interface Model {

	/**
	 * rolls a set of dice
	 *
	 * @param args Arguments
	 * @return Roll-object
	 */
	Roll rollDice(int[] args);

	/**
	 * rolls a set of dice and calculates the calcArgs into the result
	 *
	 * @param args     Arguments
	 * @param calcArgs Arithmetic calculation arguments
	 * @return Roll-object
	 */
	Roll rollDice(int[] args, String[] calcArgs);

	/**
	 * roll the bullshitbingo
	 *
	 * @return bullshitbingo item
	 */
	String[] rollBingo();

	/**
	 * gives a discord user role
	 *
	 * @deprecated
	 */
	@Deprecated
	void giveRole();

	/**
	 * removes a discord user role
	 *
	 * @deprecated
	 */
	@Deprecated
	void removeRole();

	/**
	 * create a new TickBar
	 */
	void newTickBar();

	/**
	 * Join a player to the current tickleiste
	 *
	 * @param string name of player
	 */
	void joinPlayer(String string);

	/**
	 * Join an enemy to the current tickleiste
	 *
	 * @param name name of the enemy
	 * @param ini  the iniative of the enemy as string
	 */
	void joinEnemy(String name, String ini);

	/**
	 * move the whole tickleite 1 step
	 */
	void tick();

	/**
	 * start the fight of the tickleiste
	 */
	void startBattle();

	/**
	 * move a player on the tickleiste
	 *
	 * @param string playername
	 */
	void movePlayer(String string);

	/**
	 * list some of the next steps of the tickleiste
	 */
	void listTickBar();

	/**
	 * show the position of the user that send the current message
	 */
	void showPosOfPlayer();

	/**
	 * TODO debug method. Loads TXT-File for Bingo
	 */
	void loadFileFromTxt();
}
