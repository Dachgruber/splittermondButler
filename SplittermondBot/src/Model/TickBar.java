package Model;

import java.util.ArrayList;
import java.util.Collections;

import net.dv8tion.jda.api.entities.User;

public class TickBar {

	int currentTick;
	int offset = 0;
	ArrayList<User> players;
	ArrayList<Integer> playerPos;
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

	public void tick() {
		this.currentTick++;
	}
	
	public int getCurrentTick() {
		return currentTick + offset;
		
	}
	
	public void joinPlayer(User player, int playerInitiative) {
		this.players.add(player);
		this.playerPos.add(playerInitiative);
	}
	
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
	 * @return
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

	public void start() {
		int lowestPos = Collections.min(playerPos);
		if (lowestPos < 0) 
			this.offset = lowestPos;
			
		this.currentTick = 0;
		
	}
}
