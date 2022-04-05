package model.bingo;

/**
 * The rarity of a bingo item is used to give feedback to the player how lucky he has been. It also influences the chance of which the item gets picked
 * by the picking algorithm, so that rare items are actually rare items.
 * Rarity names should always be self explanatory!
 * @author Cornelius
 *
 */
public enum Rarity {
	COMMON,UNCOMMON,RARE,EPIC,LEGENDARY
}
