/**
 *
 */
package model.tickbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.dv8tion.jda.api.entities.User;

/**
 * @author Cornelius
 *
 */
public class Controller implements TickBar {

	private int currentTick;
	
	private HashMap<String,Item> markers;
	
	public Controller() {
		this.currentTick = 0;
		this.markers = new HashMap<>();
	}
	@Override
	public void start() {
		this.currentTick = this.calcLowestPos();	//calcLowestPos returns 0 if no marker is below 0
	}

	@Override
	public void tick() {
		this.currentTick++;

	}

	@Override
	public void tick(int numberOfTicks) {
		this.currentTick += numberOfTicks;

	}

	@Override
	public int getCurrentTick() {
		return currentTick;
	}

	@Override
	public void addPlayer(int startingPos, User user) {
		this.markers.put(user.getName(), new Player(startingPos, user.getName(), user));

	}

	@Override
	public void addEnemy(int startingPos, String name, String shortDesc) {
		this.markers.put(name,new Enemy(startingPos, name, shortDesc));

	}

	@Override
	public void addMsg(int position, String messageContent) {
		this.markers.put(Message.generateID(), new Message(position, Message.generateID(), messageContent));

	}

	@Override
	public void movePlayer(User user, int moveDistance) {
		Item pl = this.markers.get(user.getName());
		pl.setPos(pl.getPos() + moveDistance);
	}

	@Override
	public void moveEnemy(String name, int moveDistance) {
		Item en = this.markers.get(name);
		en.setPos(en.getPos() + moveDistance);

	}

	
	@Override
	public void setPosOfPlayer(User user, int newPos) {
		this.markers.get(user.getName()).setPos(newPos);

	}

	@Override
	public void setPosOfEnemy(String name, int newPos) {
		this.markers.get(name).setPos(newPos);

	}

	@Override
	public void killPlayer(User user) {
		this.markers.remove(user.getName());

	}

	@Override
	public void killEnemy(String name) {
		this.markers.remove(name);

	}

	@Override
	public int getPosOfPlayer(User user) {
		return this.markers.get(user.getName()).getPos();
	}

	@Override
	public int getPosOfEnemy(String name) {
		return this.markers.get(name).getPos();
	}

	@Override
	public String getTurnsAtPos(int checkPos) {
		 String returnString = "";
		 for (Item i : markers.values()) {
			 if (i.getPos() == checkPos) {
				 returnString += i.getName() + ", ";
			 }
		 }
	     return returnString; 
	}

	@Override
	public String getCurrentTurn() {
		return this.getTurnsAtPos(this.getCurrentTick());
	}

	@Override
	public ArrayList<ArrayList<String>> getNextMoves(int numberOfNextMoves) {
		ArrayList<ArrayList<String>> lines = new ArrayList<>();

        //we only look at the next few moves....
        for (int i = 0; i < numberOfNextMoves; i++) {

            //create the entry array and add the current move
            ArrayList<String> entry = new ArrayList<>();
            entry.add(Integer.toString(this.getCurrentTick() + i) + ":");
            
            //then add the names of the marker at this point
            entry.add(getTurnsAtPos(this.getCurrentTick() + i));

        }
        return lines;
	}
	
	/**
	 * finds the lowest position of the current marker set
	 * @return lowest position, 0 if every marker is positive
	 */
	private int calcLowestPos() {
		
		int lowestPos = 0;
		//go through every added marker
		for (Item i : markers.values()) {
			if (i.getPos()<lowestPos)
				lowestPos = i.getPos();
		}
		return lowestPos;
	}
	
	
	public ArrayList<String> getStartingPlayersAsMentions() {
		ArrayList<String> players = new ArrayList<>();

		for (Item i : markers.values()) {
			if (i.isPlayer()) {				//we only want to return players
				players.add(i.getName());		//type casting is ugly, may be changed in the future
			}
		}
		return players;
	}
}
