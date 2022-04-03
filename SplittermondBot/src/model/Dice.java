package model;

import java.util.Random;

public class Dice {
	Random randomGenerator = new Random();

	int sides; // TODO: decide access rights

	public Dice(int sides) {
		this.sides = sides;
	}

	public int rollDice() {
		return this.randomGenerator.nextInt(this.sides) + 1;
	}
}
