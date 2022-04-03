package controller;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A command is a custom data set that represents the commands send from the
 * user to the software. It always includes a prefix, the actual instruction and
 * a variable number of arguments. Depending on the type of command the
 * arguments could include secondary instructions, integers, single chars etc.
 *
 * @author Cornelius
 */
public class Command {
	private String prefix;
	private String cmd;
	private String[] args;

	/**
	 * Creates a new Command from a string input. Assigns prefix, instruction and
	 * creates a argument field
	 *
	 * @param fullCommand Command as String
	 */
	public Command(String fullCommand) {
		String[] parts = this.splitCommand(fullCommand);
		this.prefix = parts[0];
		this.cmd = parts[1];
		this.args = Arrays.copyOfRange(parts, 2, parts.length);
	}

	/**
	 * splits the command-string into an array with every word
	 *
	 * @param command Command
	 * @return StringArray with words
	 */
	private String[] splitCommand(String command) {

		command.replaceAll(" ", "");

		// split the command
		ArrayList<String> returnArray = this.splitByType(command);

		// Debug output
		for (String s : returnArray)
			System.out.println(s);

		// temp: turn to String array as we use arrays and not arrayLists atm
		return returnArray.toArray(new String[returnArray.size()]);
	}

	/**
	 * splits a string into a ArrayList by using the change of types as delimiters.
	 * Handling integers, Uppercase, lowercase, whitespaces and special characters
	 * as delimiters. Will split loss free, but wont produce empty elements with
	 * only whitespaces.
	 *
	 * @param str String that needs splitting
	 * @return ArrayList with every block of string in own entry
	 */
	private ArrayList<String> splitByType(String str) { // this is imported from another project of mine
		ArrayList<String> returnArray = new ArrayList<>();
		// save the type of the first char to compare it later
		int previousType = Character.getType(str.charAt(0));
		// also save the first character as a string
		String entryString = str.substring(0, 1);

		// iterate over the input string
		for (int i = 1; i < str.length(); i++) {

			// we want to look at each char individually
			char currentChar = str.charAt(i);

			// whitespace is special: we want to split on whitespace, but
			// dont want to split twice or include the whitespace in the final array
			if (currentChar == ' ') {
				returnArray.add(entryString);

				// manually jump to the next char as the whitespace doesnt need comparing to
				entryString = Character.toString(str.charAt(i + 1));
				i++;
				continue;
			}

			// check if the current type is different than the previous type
			if (Character.getType(currentChar) == previousType)
				// if its the same type, simply add it to the entry String
				entryString += currentChar;
			else {
				// if its a different type, add the entry into the returnArray and start fresh
				// with the new char
				returnArray.add(entryString);
				entryString = Character.toString(currentChar);
			}
			// save the type of the current looked char to compare it to the next one
			previousType = Character.getType(str.charAt(i));
		}

		// after iterating we need to add the rest of the entries to the returnArray
		returnArray.add(entryString);
		return returnArray;
	}

	// getters and setters, self explanatory
	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String[] getArgs() {
		return this.args;
	}

	/**
	 * override the current arguments with a new array, use with caution!
	 *
	 * @param args
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}

	/**
	 * update one specific argument on a specific position. Note that prefix and
	 * instruction arent counted in the pos integer
	 *
	 * @param pos Position of argument
	 * @param arg content as String
	 */
	public void setArgs(int pos, String arg) {
		this.args[pos] = arg;
	}

	/**
	 * Assembles the full Command into a single string. The different parts are
	 * spaced with a whitespace character
	 *
	 * @return
	 */
	public String getRawCommand() {
		String returnString = this.prefix + ' ' + this.cmd;

		for (String s : this.args)
			returnString += ' ' + s;

		return returnString;
	}

}