package model.bingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * the bingo table manages the bingo items, dishes out ids and is used to
 * pick/catch the bingo items based on their parameters
 *
 * @author Cornelius
 *
 */
public class BingoTable implements Bingo {

	private static int classID = 0;

	private HashMap<Integer, BingoItem> table; 

	private FileManager fm;
	
	private Random rnd;

	public BingoTable() {
		this.table = new HashMap<>();
		this.fm = new FileManager();
		this.rnd = new Random();
	}

	@Override
	public String catchBingoAsString() {
		return String.join(",", this.catchBingo());
	}

	@Override
	public String[] catchBingo() {
		
		BingoItem possibleItem = null;
		boolean itemPicked = false;
		
		while (!itemPicked) {
			if(table.size() < 1) {
				throw new RuntimeException("The BingoTable is empty");
			}
			
			//first, get a possible, randomly chosen item from the table
			Integer rndkey = rnd.nextInt(table.size()-1);
			possibleItem = this.table.get(rndkey);
		
			// check if item was already picked.
			// if item is deactivated, try to pick a new item
			// first, save the start item so that we know when we tried every item
			BingoItem firstItem = possibleItem;
			while (!possibleItem.isActive()) {
				//try the next item in the table. 
				//If we reach the end, start from the top
				rndkey = (rndkey+1) % (this.table.size());
				possibleItem = this.table.get(rndkey);
				//if we tried every item, break and return null
				if(possibleItem.equals(firstItem)) {
					throw new RuntimeException("The BingoTable has no active item");
				}
			}
			
			//finally, try to pass the rarity vibe check
			if(calcIfPicked(possibleItem)) {
				itemPicked = true;
			}
			
		}
		return possibleItem.deactivate().asStringArray();
		//return possibleItem.asStringArray();
	}
	
	/**
	 * calculates if the BingoItem gets picked with its set rarity
	 * @param item the BingoItem
	 * @return true if picked
	 */
	private boolean calcIfPicked(BingoItem item) {
		return rnd.nextDouble() < item.getRarity().getProbability();
	}
	
	@Override
	public void resetTable() {
		for (BingoItem entry : this.table.values())
			entry.activate();

	}

	@Override
	public boolean saveTable() {
		return this.fm.saveFileToDisk(this.table); // saveFile() returns boolean
	}

	@Override
	public boolean loadTable() throws Exception {
		// load temporary file to check for null-values
		HashMap<Integer, BingoItem> tempTable = this.fm.loadFileFromDisk();
		if (tempTable != null) {
			this.table = tempTable;
			return true;
		} else
			return false;

	}
	
	/**
	 * temporary function for debugging purposes. Loads table from TXT and includes it.
	 * 
	 * @throws Exception
	 */
	@Override
	public void loadTableFromTxt() throws Exception {
		String[] items = fm.loadFileFromTxt();
		for (String entry: items) {
			String[] itemArgs = entry.split(":");
			
			//check the itemArgs length. If the array is to short, fill up with default values
			if (itemArgs.length == 1) {
				this.addItem(itemArgs[0], "No Description given!", Rarity.COMMON);
			}
			else {
				if (itemArgs.length == 2) {
					this.addItem(itemArgs[0], itemArgs[1], Rarity.COMMON);
				}
				else {
					this.addItem(itemArgs[0], itemArgs[1], Rarity.valueOf(itemArgs[2].toUpperCase()));
				}
			}
			System.out.println("added item: " + itemArgs[0]);
		}
		
		
	}

	@Override
	public void addItem(String name, String description, Rarity rarity) {
		this.table.put(BingoTable.classID, new BingoItem(BingoTable.classID, name, description, rarity));
		BingoTable.classID++;

	}

	public String[] getItemAsArray(int id) {
		BingoItem bi = this.table.get(id);
		return bi.asStringArray();
	}

	@Override
	public void removeItem(int id) {
		this.table.remove(id);

	}

	@Override
	public ArrayList<String[]> listTable() {
		Set<Integer> tablekeys = this.table.keySet();
		ArrayList<String[]> returnArray = new ArrayList<>();

		for (Integer key : tablekeys)
			returnArray.add(this.table.get(key).asStringArray());
		return returnArray;
	}

}
