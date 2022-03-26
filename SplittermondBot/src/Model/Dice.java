package Model;

import java.util.Random;

public class Dice {

	Random randomGenerator = new Random();
	int sides;
	
	public Dice(int sides) {
		this.sides = sides;
	}
	
	public int rollDice() {
		return randomGenerator.nextInt(sides)+1;
	}
	
}
