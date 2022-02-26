package Model;

import Controller.Command;
import View.RollTemplate;

public class Roll {

	RollTemplate embed = new RollTemplate();
	int diceAmount;
	int diceSize;
	
	int[] resultField;
	int result = 0;
	
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
	
	public void RollTheDice() {
		
		this.resultField = new int[this.diceAmount];
		
		for (int i = 0; i < this.diceAmount; i++) {
				int tempResult = this.diceList[i].rollDice();
				resultField[i] = tempResult;	
			
			}
		this.calcResult();
		
	}
	
	
	public void calcResult() {
		int newResult = 0;
		for ( int n = 0; n < this.resultField.length; n++) {
			newResult += resultField[n];
		}
		this.result = newResult;
	}
	
	public void calcResult(String operator, String calcArg) {
		
		int newResult = 0;
		for ( int n = 0; n < this.resultField.length; n++) {
			newResult += resultField[n];
		}
			switch (operator) {
			case "+":
				newResult += Integer.parseInt(calcArg);
				break;
			case "-":
				newResult -= Integer.parseInt(calcArg);
				break;
			
			
		}
		
		this.result = newResult;
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

	public int[] getResultField() {
		return resultField;
	}
	
	public int getResult() {
		return result;
	}

}
