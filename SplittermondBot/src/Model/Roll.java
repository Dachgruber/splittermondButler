package Model;

import View.RollTemplate;

public class Roll {

	RollTemplate embed = new RollTemplate();
	int diceAmount;
	

	int diceSize;
	int mod = 0;
	int test;
	Dice[] diceList ;

	public Roll(int diceAmount , int diceSize) {
		
		this.diceAmount = diceAmount;
		this.diceSize = diceSize;
		
		CreateDice();
	}
	
	private void CreateDice() {
		diceList = new Dice[diceAmount];
		
		for (int i = 0; i < diceAmount; i++)
		{
			this.diceList[i] = new Dice(this.diceSize);
		}
	}
	
	public int[] RollTheDice() {
		
		int[] resultField = new int[this.diceAmount];
		
		
		for (int i = 0; i < this.diceAmount; i++)
			{
				int tempResult = this.diceList[i].rollDice();
				resultField[i] = tempResult;	
			
			}
		
		return resultField;
	}

	public int getDiceAmount() {
		return diceAmount;
	}

	public void setDiceAmount(int diceAmount) {
		this.diceAmount = diceAmount;
	}

	public int getDiceSize() {
		return diceSize;
	}

	public void setDiceSize(int diceSize) {
		this.diceSize = diceSize;
	}

}
