package model;

import view.RollTemplate;

public class Roll {
	RollTemplate embed = new RollTemplate(); // TODO: ACCESS RIGHTS
	int diceAmount;
	int diceSize;

	int[] resultField;
	int result = 0;

	int mod = 0;
	int test;
	Dice[] diceList;

	/**
	 * creates a new Roll event, needs amount of dices and the used size
	 *
	 * @param diceAmount amount of dices
	 * @param diceSize   size of dice
	 */
	public Roll(int diceAmount, int diceSize) {
		this.diceAmount = diceAmount;
		this.diceSize = diceSize;

		this.CreateDice();
	}

	private void CreateDice() {
		this.diceList = new Dice[this.diceAmount];

		for (int i = 0; i < this.diceAmount; i++)
			this.diceList[i] = new Dice(this.diceSize);
	}

	public Roll RollTheDice() {
		this.resultField = new int[this.diceAmount];

		for (int i = 0; i < this.diceAmount; i++) {
			int tempResult = this.diceList[i].rollDice();
			this.resultField[i] = tempResult;
		}

		this.calcResult();

		return this;
	}

	public void calcResult() {
		int newResult = 0;

		for (int i : this.resultField)
			newResult += i;

		this.result = newResult;
	}

	public Roll calcResult(String operator, String calcArg) {
		int newResult = 0;

		for (int element : this.resultField)
			newResult += element;

		switch (operator) {
		case "+" -> newResult += Integer.parseInt(calcArg);
		case "-" -> newResult -= Integer.parseInt(calcArg);
		}

		this.result = newResult;

		return this;
	}

	public int getDiceAmount() {
		return this.diceAmount;
	}

	public void setDiceAmount(int diceAmount) {
		this.diceAmount = diceAmount;
	}

	public int getDiceSize() {
		return this.diceSize;
	}

	public void setDiceSize(int diceSize) {
		this.diceSize = diceSize;
	}

	public int[] getResultField() {
		return this.resultField;
	}

	public int getResult() {
		return this.result;
	}

}
