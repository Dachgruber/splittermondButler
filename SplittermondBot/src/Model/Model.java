package Model;

public interface Model {

    /**
     * rolls a set of dice
     *
     * @param args
     * @return
     */
    Roll rollDice(Integer[] args);

    Roll rollDice(Integer[] args, String[] calcArgs);

    String rollBingo();

    void giveRole();

    void removeRole();

    void newTickBar();

    void joinPlayer(String string);

    void tick();

    void startBattle();

    void movePlayer(String string);

    void listTickBar();

    void showPosOfPlayer();

    void joinEnemy(String name, String ini);
}
