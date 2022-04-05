package model.bingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * the bingo table manages the bingo items, dishes out ids and is used to pick/catch the bingo items based on their parameters
 * @author Cornelius
 *
 */
public class BingoTable implements Controller{

		private static int classID = 0;
	
		private HashMap<Integer,BingoItem> table;
		
		public BingoTable() {
			this.table = new HashMap<>();
		}

		@Override
		public String catchBingoAsString() {
			return String.join(",", this.catchBingo());
		}

		@Override
		public String[] catchBingo() {
			Object[] keys = this.table.keySet().toArray();
			Object rndkey = keys[new Random().nextInt(keys.length)];
			BingoItem possibleItem = this.table.get(rndkey);
			
			//check if item was already picked.
			//if item is deactivated, try to pick a new item
			if (!possibleItem.isActive()) {
				return this.catchBingo();
			}
			else
				return possibleItem.deactivate().asStringArray();
		}

		@Override
		public void resetTable() {
			for (BingoItem entry: this.table.values()) {
				entry.activate();
			}
			
		}

		@Override
		public void saveTable() {
			
		}
		
		@Override
		public void loadTable() {
			
		}

		@Override
		public void addItem(String name, String description, Rarity rarity) {
			this.table.put(classID, new BingoItem(classID, name, description, rarity));
			classID++;
			
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
			ArrayList<String[]> returnArray = new ArrayList<String[]>();
			
			for (Integer key: tablekeys) {
				returnArray.add(this.table.get(key).asStringArray());
			}
			return returnArray;
		}
		
		
}
