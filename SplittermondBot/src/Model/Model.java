package Model;

public interface Model {
	

	Roll rollDice(Integer[] args);
	
	void giveRole();
	
	void removeRole();

	void newTickBar();

	void joinPlayer(String string);

	void tick();
	
	void startBattle();

	void movePlayer(String string);
}
