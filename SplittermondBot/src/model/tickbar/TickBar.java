package model.tickbar;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.User;

public interface TickBar {

	/**
	 * starts the tickbar by calculating the lowest postition
	 */
	void start();

	/**
	 * moves the tickbar one step forward
	 */
	void tick();

	/**
	 * moves the tickbar multiple steps forward
	 *
	 * @param numberOfTicks
	 */
	void tick(int numberOfTicks);

	/**
	 * returns the current Tick of the TickBar
	 */

	int getCurrentTick();

	// ------------------------Marker managmenet-------------------------------

	/**
	 * adds a new Player to the TickBar
	 *
	 * @param startingPos
	 * @param user        the JDA User object
	 */
	void addPlayer(int startingPos, User user);

	/**
	 * adds an enemy to the Tickbar
	 *
	 * @param startingPos
	 * @param name        the unique name of the enemy
	 * @param shortDesc   short description of the enemy
	 */
	void addEnemy(int startingPos, String name, String shortDesc);

	/**
	 * adds a message to the TickBar
	 *
	 * @param position
	 * @param messageContent this content gets displayed during trigger
	 */
	void addMsg(int position, String messageContent);

	/**
	 * moves a Player to a new Position, relative to its old position
	 *
	 * @param user         the user object
	 * @param moveDistance
	 */

	void movePlayer(User user, int moveDistance);

	/**
	 * moves an enemy to a new Position, relative to its old position
	 *
	 * @param name         the unique name
	 * @param moveDistance
	 */
	void moveEnemy(String name, int moveDistance);

	/**
	 * sets the player to a new absolute position
	 *
	 * @param user   the user object
	 * @param newPos
	 */
	void setPosOfPlayer(User user, int newPos);

	/**
	 * sets the enemy to a new absolute position
	 *
	 * @param name   the unique name
	 * @param newPos
	 */
	void setPosOfEnemy(String name, int newPos);

	/**
	 * deletes a player from the TickBar. Attention! There is no recycling bin!
	 *
	 * @param the user Object
	 */
	void killPlayer(User user);

	/**
	 * deletes an enemy from the TickBar. Attention! There is no recycling bin!
	 *
	 * @param name the unique name
	 */
	void killEnemy(String name);

	// --------------------Display methods etc-------------------------

	/**
	 * returns the pos of the player
	 *
	 * @param User user object
	 */
	int getPosOfPlayer(User user);

	/**
	 * returns the pos of the Enemy
	 *
	 * @param name the unique name
	 */
	int getPosOfEnemy(String name);

	/**
	 * get the names and mentions of everyone at the specified turn
	 *
	 * @param checkPos the pos the Turns should be looked up
	 */
	String getTurnsAtPos(int checkPos);

	/**
	 * get the Turns at the current Tick. Wrapper of getTurnsAtPos() for the
	 * currentTick for convienience
	 */
	String getCurrentTurn();

	/**
	 * get a short overview of the next actions as an ArrayList matrix.
	 *
	 * @param numberOfNextMoves the length of the matrix
	 */
	ArrayList<ArrayList<String>> getNextMoves(int numberOfNextMoves);

	/**
	 * get a overview of the starting players
	 */
	ArrayList<String> getStartingPlayersAsMentions();

}
