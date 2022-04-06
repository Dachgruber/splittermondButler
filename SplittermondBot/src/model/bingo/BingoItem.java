package model.bingo;

/**
 * a BingoItem has a unique identification number (managed by the Table class),
 * a name and a description. The active-boolean is hidden and used to determine
 * if the item was already picked or not. The Rarity parameter is used in the
 * Table class to modify their probability of getting caught.
 *
 * @author Cornelius
 *
 */
public class BingoItem implements java.io.Serializable {

	private static final long serialVersionUID = 5735156531186608841L;
	private int id;
	private String name;
	private String desc;
	private Rarity rarity;
	private boolean active;

	public BingoItem(int id, String name, String desc, Rarity rarity) {

		this.id = id;
		this.name = name;
		this.desc = desc;
		this.active = true;
		this.rarity = rarity;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isActive() {
		return this.active;
	}

	public BingoItem activate() {
		this.active = true;
		return this;
	}

	public BingoItem deactivate() {
		this.active = false;
		return this;
	}

	public Rarity getRarity() {
		return this.rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public String[] asStringArray() {
		return new String[] { Integer.toString(this.id), this.name, this.desc, this.rarity.toString(),
				Boolean.toString(this.active) };
	}

}
