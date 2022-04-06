/**
 *
 */
package model.bingo;

import java.util.ArrayList;

/**
 * @author Cornelius
 *
 */
public interface Bingo {

	/**
	 * get a random BingoItem as a single String and deactivate the Item
	 *
	 * @return
	 */
	String catchBingoAsString();

	/**
	 * get a random BingoItem as a StringArray with every information and deactivate
	 * the Item
	 *
	 * @return
	 */
	String[] catchBingo();

	/**
	 * reset the table to reactivate every single item
	 */
	void resetTable();

	/**
	 * saves the current state of the Table to the disk
	 *
	 * @return saving successful?
	 */
	boolean saveTable();

	/**
	 * loads the most up-to-date version of the bingoTable
	 *
	 * @return false if loaded table was null for some reason, true if loading
	 *         successful
	 * @throws Exception
	 */
	boolean loadTable() throws Exception;

	/**
	 * adds a new Item to the bingo table
	 *
	 * @param name        short name of item
	 * @param description longer description
	 * @param rarity      defines the picking chance
	 */
	void addItem(String name, String description, Rarity rarity);

	/**
	 * removes the specified item from the table. This cannot be reverted!
	 *
	 * @param id identification number of the item
	 */
	void removeItem(int id);

	/**
	 * gets a 2D-String matrix with every added Item. Used for GM/Debug purposes
	 *
	 * @return String matrix with whole table
	 */
	ArrayList<String[]> listTable();

	/**
	 * temporary function for debugging purposes. Loads table from TXT and includes it.
	 * @deprecated
	 * @throws Exception
	 */
	void loadTableFromTxt() throws Exception;

}
