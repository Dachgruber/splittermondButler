package Model;

import java.util.ArrayList;
import java.util.Collections;

import net.dv8tion.jda.api.entities.User;

/**
 * the tickbar is the implementation of the classic 'tickleiste', which is the battle board of Splittermond. 
 * User and enemies join the battle with a 1W6 for some random start initative. Then, the battle starts with the first
 * player, and every player gets called corresponding to their turn. After some action the player can either move their pointer relative
 * to their last position or set themselves to a specific point on the table
 * @author Cornelius
 *
 */
public class TickBar {

	int currentTick;
	
	//used to calc the actual tick if the battle starts with negative ticks
	int offset = 0;
	
	//players and the positions are represented by dynamic lists. The pos-integer for the list
	//is used as an internal userID
	ArrayList<User> players;
	ArrayList<Integer> playerPos;
	
	/**
	 * creates a new Tickbar, starting at tick 0
	 */
	public TickBar() {
		this.currentTick = 0;
		this.players = new ArrayList<User>();
		this.playerPos = new ArrayList<Integer>();
	}
	
	public User[] getPlayers() {
		return players.toArray(new User[players.size()]);
	}

	public Integer[] getPlayerPos() {
		return playerPos.toArray(new Integer[playerPos.size()]);
	}

	/**
	 * steps the tickbar to the next tick
	 */
	public void tick() {
		this.currentTick++;
	}
	
	public int getCurrentTick() {
		return currentTick + offset;
		
	}
	
	/**
	 * adds a player to the tickbar. Needs the User-Object and the start-position
	 * @param player User-Object of the player
	 * @param playerInitiative 1W6-INI of the player
	 */
	public void joinPlayer(User player, int startTick) {
		this.players.add(player);
		this.playerPos.add(startTick);
	}
	
	/**
	 * moves the player relative to the current position
	 * @param player
	 * @param moveDistance
	 */
	public void movePlayer(User player, int moveDistance) {
		int playerID = players.indexOf(player);
		int newPos = playerPos.get(playerID) + moveDistance;
		playerPos.set(playerID, newPos);
	}
	
	/**
	 * gets the playernames to act at the current Tick
	 * @return
	 */
	public User[] getTurn() {
		return this.getPlayersAtPos(this.getCurrentTick());
	}
	
	/**
	 * returns the turn of the user
	 * @param user
	 * @return
	 */
	public int getPositionOfUser(User player) {
		int playerID = players.indexOf(player);
		return playerPos.get(playerID);

	}
	
	/**
	 * really bad method of getting the next moves of players into a 2D-String matrix
	 * @param length amount of moves that should be considered
	 * @return ArrayLists of every user acting on the corresponding tick
	 */
	public ArrayList<ArrayList<String>> getNextMoves(int length) {
		ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
		
		//we only look at the next few moves....
		for (int i = 0; i < length; i++){
			
			//create the entry array and add the current move
			ArrayList<String> entry = new ArrayList<String>();
			entry.add(Integer.toString(this.getCurrentTick()+i) + ":");
			
			//for every move get the players that are positioned at that pos
			User[] player = this.getPlayersAtPos(this.getCurrentTick()+i);
			for(User element : player) {
				entry.add(element.getName());
			}
			
			//finally, add the line into the matrix
			lines.add(entry);
			
		}
		return lines;
	}
	
	/**
	 * determines the players, that have their turn at the named position
	 * @param pos
	 * @return
	 */
	private User[] getPlayersAtPos(int pos) {
		ArrayList<User> returnArray = new ArrayList<User>() ;
		for (int entry: playerPos) {
			if (entry == pos)
				returnArray.add(players.get(playerPos.indexOf(entry)));
				
		}
		
		return returnArray.toArray(new User[returnArray.size()]);
	}

	/**
	 * initialises the tickbar, checks if negative pos are given and calcs the offset needed if some player start at a negative number
	 */
	public void start() {
		int lowestPos = Collections.min(playerPos);
		if (lowestPos < 0) 
			this.offset = lowestPos;
			
		this.currentTick = 0;
		
	}
}
