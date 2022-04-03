package controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/**
 * a command is a custom data set that includes a prefix, a actual order and an string array of arguments
 *
 * @author Cornelius
 */
public class Command {
    private String prefix = "!";
    private String cmd;
    private String[] args;

    /**
     * creates a new command and assign every part of it
     *
     * @param fullCommand Command
     */
    public Command(String fullCommand) {
        String[] parts = this.splitCommand(fullCommand);
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
    	
    	//remove all whitespaces because we wont need them anyway
    	String compactedCommand = command.replaceAll(" ", "");
    	
    	//split the command 
    	ArrayList<String> returnArray = this.splitByType(command);
    	
    	//Debug output
    	for (String s : returnArray) {
    		System.out.println(s);
    	}
    	
    	//temp: turn to String array as we use arrays and not arrayLists atm
    	return returnArray.toArray(new String[returnArray.size()]) ;
    }
    
    /**
	 * splits a string into a ArrayList by using the change of types as delimiters.
	 * Handling integers, Uppercase, lowercase, whitespaces and special characters as delimiters. 
	 * Will split loss free, but wont produce empty elements with only whitespaces.
	 * @param str String that needs splitting
	 * @return ArrayList with every block of string in own entry
	 */
	private ArrayList<String> splitByType(String str) {
		ArrayList <String> returnArray = new ArrayList<>();
		//save the type of the first char to compare it later
		int previousType = Character.getType(str.charAt(0)); 
		//also save the first character as a string
		String entryString = str.substring(0,1);
		
		//iterate over the input string
		for (int i = 1; i < str.length(); i++) {
			
			//we want to look at each char individually
			char currentChar = str.charAt(i);
			
			//whitespace is special: we want to split on whitespace, but
			//dont want to split twice or include the whitespace in the final array
			if (currentChar == ' ') {
				returnArray.add(entryString);
				
				//manually jump to the next char as the whitespace doesnt need comparing to
				entryString = Character.toString(str.charAt(i+1));
				i++;
				continue;
			}
			
			//check if the current type is different than the previous type
			if (Character.getType(currentChar) == previousType) {
				//if its the same type, simply add it to the entry String
				entryString += currentChar;
			}
			else {
				// if its a different type, add the entry into the returnArray and start fresh with the new char
					returnArray.add(entryString);
					entryString = Character.toString(currentChar);	
				}
				//save the type of the current looked char to compare it to the next one
				previousType = Character.getType(str.charAt(i));
			}
			
		//after iterating we need to add the rest of the entries to the returnArray
		returnArray.add(entryString);
		return returnArray;
	}
    
    // getter and setter
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String[] getArgs() {
        return args;
    }

    // set the full args array
    public void setArgs(String[] args) {
        this.args = args;
    }

    // only set one specific argument
    public void setArgs(int pos, String arg) {
        this.args[pos] = arg;
    }

    public String getRawCommand() {
        return prefix + cmd + ArrayUtils.toString(args);
    }
}