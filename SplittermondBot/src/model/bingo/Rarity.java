package model.bingo;

/**
 * The rarity of a bingo item is used to give feedback to the player how lucky
 * he has been. It also influences the chance of which the item gets picked by
 * the picking algorithm, so that rare items are actually rare items. 
 *
 * @author Cornelius
 *
 */
public enum Rarity {
	
	//Rarity names should always be self explanatory!
	//When changing the probability, the documentation should always be edited as well!
	
	COMMON(0.3) , 			
	UNCOMMON(0.25), 
	RARE(0.2), 
	EPIC(0.125), 
	LEGENDARY(0.1);
	
	private final double probability;
	
	Rarity(double d) {
		this.probability = d;
	}
	
	public double getProbability() {
		return probability;
	}
}
