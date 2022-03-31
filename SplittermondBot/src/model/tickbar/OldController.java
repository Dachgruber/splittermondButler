package model.tickbar;

import java.util.ArrayList;
import java.util.Collections;

import net.dv8tion.jda.api.entities.User;

/**
 * the Controller manages the tickbar, creates tickbarItems and dishes out the needed information for every other class
 * @author Cornelius
 * @deprecated
 *
 */
public class OldController {

    int currentTick;

    //every item (called markers) on the tickbar gets saved in a dynamic list
    ArrayList<Item> markers;

    /**
     * creates a new Tickbar, starting at tick 0
     */
    public OldController() {
        this.currentTick = 0;
        this.markers = new ArrayList<>();
    }

    
    public void start() {
        int lowestPos = this.calcLowestPos();
        if (lowestPos < 0)
        	this.currentTick = lowestPos;
        else
        	this.currentTick = 0;

    }

    public void tick() {
        this.currentTick++;
    }
    
   
    public void tick(int numberOfTicks) {
    	this.currentTick += numberOfTicks;
    }
    
    private int calcLowestPos() {
		// TODO Auto-generated method stub
    	// int lowestPos = Math.min(Collections.min(playerPos), Collections.min(enemyPos)); //old method
		return 0;
	}

	
   

    public int getCurrentTick() {
        return currentTick + offset;

    }

    public ArrayList<Item> getMarkers() {
    	return markers;							
    }
    
    public ArrayList<Player> getPlayers() {
    	ArrayList<Player> returnArray = new ArrayList<>();
    	
    	for (Item entry: markers) {
    		if (entry.isPlayer()) {
    			Player pl = (Player) entry;		//type casting is ugly, may be updated in the future
    			returnArray.add(pl);
    		}
    	}
    	 return returnArray;							//@SpuelMett ja ich benutze hier wieder lokale Arrays, ist hier einfacher :smilingcat
    }
    
    public ArrayList<Enemy> getEnemies() {
    	ArrayList<Enemy> returnArray = new ArrayList<>();
    	
    	for (Item entry: markers) {
    		if (entry.isEnemy()) {
    			Enemy en = (Enemy) entry;
    			returnArray.add(en);
    		}
    	}
    	 return returnArray;							
    }
    
    /**
     * adds a new marker to the tickbar
     * @param marker
     */
    public void addMarker(Item marker) { 
    	if (marker != null )
    		this.markers.add(marker);
    }
    
    /**
     * moves a marker by a set distance
     * @param marker
     * @param moveDistance
     */
    
    public void moveMarker(Item marker, int moveDistance) {
    	marker.setPos(marker.getPos() + moveDistance);
    }
    /**
     * sets a marker to a predifined position
     * @param marker
     * @param moveDistance
     */
    public void setMarker(Item marker, int newPos) {
    	marker.setPos(newPos);
    }
    
    /**
     * really bad method of getting the next moves of players and enemies into a 2D-String matrix
     *
     * @param length amount of moves that should be considered
     * @return ArrayLists of every user acting on the corresponding tick
     */
    public ArrayList<ArrayList<String>> getNextMoves(int length) {
        ArrayList<ArrayList<String>> lines = new ArrayList<>();

        //we only look at the next few moves....
        for (int i = 0; i < length; i++) {

            //create the entry array and add the current move
            ArrayList<String> entry = new ArrayList<>();
            entry.add(Integer.toString(this.getCurrentTick() + i) + ":");

            //for every move get the players that are positioned at that pos
            User[] player = this.getPlayersAtPos(this.getCurrentTick() + i);
            for (User element : player) {
                entry.add(element.getName());
            }

            //also, add the enemies
            //TODO currently the players move before the enemies every time!
            Enemy[] enemy = this.getEnemiesAtPos(this.getCurrentTick() + i);
            for (Enemy element : enemy) {
                entry.add(element.getName());
            }

            //finally, add the line into the matrix
            lines.add(entry);

        }
        return lines;
    }

    /**
     * gets the playernames to act at the current Tick
     *
     * @return
     */
    public User[] getPlayerTurn() {
        return this.getPlayersAtPos(this.getCurrentTick());
    }

    /**
     * gets the playernames to act at the current Tick
     *
     * @return
     */
    public Enemy[] getEnemyTurn() {
        return this.getEnemiesAtPos(this.getCurrentTick());
    }


    /**
     * adds a player to the tickbar. Needs the User-Object and the start-position
     *
     * @param player           User-Object of the player
     * @param playerInitiative 1W6-INI of the player
     * @deprecated
     */
    public void joinPlayer(User player, int startTick) {
    	Player pl = new Player(startTick, player.getName(), player);
        this.markers.add(pl);
    }

    /**
     * moves the player relative to the current position
     *
     * @param player
     * @param moveDistance
     */
    public void movePlayer(User player, int moveDistance) {
        int playerID = players.indexOf(player);
        int newPos = playerPos.get(playerID) + moveDistance;
        playerPos.set(playerID, newPos);
    }

    /**
     * returns the turn of the user
     *
     * @param user
     * @return
     */
    public int getPositionOfUser(User player) {
        int playerID = players.indexOf(player);
        return playerPos.get(playerID);

    }


    /**
     * determines the players, that have their turn at the named position
     *
     * @param pos
     * @return
     */
    private User[] getPlayersAtPos(int pos) {
        ArrayList<User> returnArray = new ArrayList<>();
        for (int entry : playerPos) {
            if (entry == pos)
                returnArray.add(players.get(playerPos.indexOf(entry)));

        }

        return returnArray.toArray(new User[returnArray.size()]);
    }

    /**
     * adds an enemy to the tickbar
     *
     * @param enemy
     * @param startTick
     */
    public void joinEnemy(Enemy en, int startTick) {
        this.enemies.add(en);
        this.enemyPos.add(startTick);

    }

    /**
     * moves the enemy relative to the current position
     *
     * @param enemy
     * @param moveDistance
     */
    public void moveEnemy(Enemy en, int moveDistance) {
        int enemyID = enemies.indexOf(en);
        int newPos = enemyPos.get(enemyID) + moveDistance;
        playerPos.set(enemyID, newPos);
    }

    /**
     * returns the turn of the enemy
     *
     * @param user
     * @return
     */
    public int getPositionOfEnemy(Enemy en) {
        int enemyID = enemies.indexOf(en);
        return enemyPos.get(enemyID);

    }


    /**
     * determines the enemies, that have their turn at the named position
     *
     * @param pos
     * @return
     */
    private Enemy[] getEnemiesAtPos(int pos) {
        ArrayList<Enemy> returnArray = new ArrayList<>();
        for (int entry : enemyPos) {
            if (entry == pos)
                returnArray.add(enemies.get(enemyPos.indexOf(entry)));

        }

        return returnArray.toArray(new Enemy[returnArray.size()]);
    }
}
