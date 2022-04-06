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
		Integer rndkey = rnd.nextInt(table.size()-1);
		
		BingoItem possibleItem = this.table.get(rndkey);
		
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
				String[] tempArray = {"No bingo for you"};
				return tempArray ;
			}
		}	
		return possibleItem.deactivate().asStringArray();
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
	 * @deprecated
	 * @throws Exception
	 */
	@Override
	public void loadTableFromTxt() throws Exception {
		String[] items = fm.loadFileFromTxt();
		for (String entry: items) {
			String[] itemArgs = entry.split(":");
			this.addItem(itemArgs[0], itemArgs[1], Rarity.COMMON);
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
