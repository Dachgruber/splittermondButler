package model;

import java.util.ArrayList;
import java.util.Collections;

import net.dv8tion.jda.api.entities.User;

/**
 * the tickbar is the implementation of the classic 'tickleiste', which is the battle board of Splittermond.
 * User and enemies join the battle with a 1W6 for some random start initative. Then, the battle starts with the first
 * player, and every player gets called corresponding to their turn. After some action the player can either move their pointer relative
 * to their last position or set themselves to a specific point on the table
 *
 * @author Cornelius
 */
public class TickBar {

    int currentTick;

    //used to calc the actual tick if the battle starts with negative ticks
    int offset = 0;

    //players and the positions are represented by dynamic lists. The pos-integer for the list
    //is used as an internal userID
    ArrayList<User> players;
    ArrayList<Enemy> enemies;
    ArrayList<Integer> playerPos;
    ArrayList<Integer> enemyPos;

    /**
     * creates a new Tickbar, starting at tick 0
     */
    public TickBar() {
        this.currentTick = 0;
        this.players = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.enemyPos = new ArrayList<>();
        this.playerPos = new ArrayList<>();
    }

    /**
     * initialises the tickbar, checks if negative pos are given and calcs the offset needed if some player start at a negative number
     */
    public void start() {
        //looks at the first enemy and the first user and determines, who comes first
        int lowestPos = Math.min(Collections.min(playerPos), Collections.min(enemyPos));
        if (lowestPos < 0)
            this.offset = lowestPos;

        this.currentTick = 0;

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

    public User[] getPlayers() {
        return players.toArray(new User[players.size()]);
    }

    public Enemy[] getEnemies() {
        return enemies.toArray(new Enemy[enemies.size()]);
    }

    public Integer[] getPlayerPos() {
        return playerPos.toArray(new Integer[playerPos.size()]);
    }

    public Integer[] getEnemyPos() {
        return enemyPos.toArray(new Integer[enemyPos.size()]);
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
     */
    public void joinPlayer(User player, int startTick) {
        this.players.add(player);
        this.playerPos.add(startTick);
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
