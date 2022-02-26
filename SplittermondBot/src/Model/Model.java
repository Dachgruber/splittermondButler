package Model;

public interface Model {
	

	Roll rollDice(Integer[] args);
	
	Roll rollDice(Integer[] args, String[] calcArgs);
	
	void giveRole();
	
	void removeRole();

	void newTickBar();

	void joinPlayer(String string);

	void tick();
	
	void startBattle();

	void movePlayer(String string);
}
