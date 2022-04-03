package model.tickbar;

/**
 * a message is a simple marker on the tickbar, that acts as a reminder for
 * damage output etc
 *
 * @author Cornelius
 */
public class Message extends Item {

	static int MessageCount = 0;
	private String contents;

	/**
	 * generate a unique ID for every Message created, essentially replacing the
	 * "name" of this marker
	 *
	 * @return String generated ID
	 */
	public static String generateID() {
		return "Msg" + Message.MessageCount;
	}

	public Message(int pos, String name, String content) {
		super(pos, name);
		this.contents = content;
		this.setType(ItemType.MSG);
		Message.MessageCount++;
	}

	// getters and setters
	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
