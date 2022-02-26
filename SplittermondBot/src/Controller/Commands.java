package Controller;

import java.util.Arrays;
import java.util.Scanner;

/**
 * this class validates the given command and can correct a command to the corresponding correct command
 * @author Cornelius
 *
 */
public class Commands {

	private final String prefix = "!";
	
	private final String validOperator[] = 
		{"+","-"};
	
	private final String validCommands[] =
		//{"roll","giveRole","removeRole"};
		{"roll","giveRole","removeRole", "tick", "bingo"};
	
	private final String roll[] =
		{"roll", "throw", "werfe", "würfel", "r", "würfle", "rolle"};
	
	private final String giveRole[] =
		{"give", "get", "giveRole", "getRole"};
		
	private final String removeRole[] =
		{"removeRole", "remove"};
	
	private final String tick[] =
		{"tick", "tickleiste", "battle", "t"};

	private final String bingo[] =
		{"bbb", "bullshit", "bingo", "bullshitbingo", "nik"};
		
	public Commands() {
		
	}
	
	/**
	 * checks if a string is accepted as a command
	 * @param command string
	 * @return true if command is valid
	 */
	public boolean isValidCommand(Command cmd){
		boolean valid = false;
		
		if (cmd.getPrefix().equals(prefix)) { //is the correct prefix used?
			System.out.println("Right prefix");
			System.out.println(cmd.getCmd());
			
			if (Arrays.asList(validCommands).contains(cmd.getCmd())) { //is the actual command valid?
				System.out.println("Right command");
				valid = true;
				}
		}
		
		return valid;
		
		
	}
	
	/**
	 * checks if a command contains a valid calculaton as in 2W10 + 13 etc
	 * @param command string
	 * @return true if command is valid
	 */
	public boolean isValidCalc(Command cmd){
		boolean valid = false;
		
		if ((cmd.getArgs().length > 0)) {
			System.out.println("the to checked operator is:");
			System.out.println(cmd.getArgs()[1]);
			if (Arrays.asList(validOperator).contains((cmd.getArgs()[1]))) { //is the used operator legal?
			
				if (this.isInteger(cmd.getArgs()[2])) { 					//is the operator followed by an integer?
					System.out.println("Right command");
					valid = true;
					}
			}
		}
		return valid;
		
	}
	
	
	/**
	 * corrects spelling of command and returns standard command string
	 * @param command
	 * @return string correct command
	 */
	public Command correctCommand(Command command) {
		String lcCommand = command.getCmd().toLowerCase();
		if(Arrays.asList(roll).contains(lcCommand))
			command.setCmd("roll");
		else if(Arrays.asList(giveRole).contains(lcCommand))
			command.setCmd("giveRole");
		else if(Arrays.asList(removeRole).contains(lcCommand))
			command.setCmd("removeRole");
		else if(Arrays.asList(tick).contains(lcCommand))
			command.setCmd("tick");
		else if(Arrays.asList(bingo).contains(lcCommand))
			command.setCmd("bingo");
		return command;
	}

	/**
	 * checks if the command acts as a valid roll command.
	 * A command is considered valid if either no args are given (default roll) or 2 Integers split with a W is given (2W10 etc)
	 * @param strings
	 * @return
	 */
	public boolean checkValidRoll(String[] args) {
		boolean valid = false;
		if (args.length == 0) {
			valid = true;
		}
		else {
			String[] parts = args[0].split("W");
			if (this.isInteger(parts[0]))
				if(this.isInteger(parts[1]))
					valid = true;
		}
	
			
		return valid;
	}
	
	/**
	 * checks if str is an Integer
	 * @param str
	 * @return
	 */
	private boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	}
	


/**
 * parses the command and returns the two integer values of the dice roll (f.e. 2W10 -> [2,10])
 * @param strings
 * @return int array with dice amount and dice size
 */
	public Integer[] getRollArgs(String[] strings) {
		if (this.checkValidRoll(strings)) {
			String[] parts = strings[0].split("W");
			Integer diceAmount = Integer.parseInt(parts[0]);
			Integer diceSize = Integer.parseInt(parts[1]); 
			Integer[] returnArray = {diceAmount, diceSize};
			return returnArray;
			}
		else {
			return null;
		}
		
	}
}
