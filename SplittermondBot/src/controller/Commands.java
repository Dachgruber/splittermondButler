package controller;

import java.util.Arrays;

/**
 * this class is used for correcting and validating commands in the context of
 * this software. It currently holds the valid commands with their aliases,
 * defines the correct prefix and specifies the syntax a specific kind of
 * command needs to follow.
 *
 * @author Cornelius
 */
public class Commands {
	private final String[] bingo = // play bullshit-bingo
			{ "bbb", "bullshit", "bingo", "bullshitbingo", "nik" };
	
	private final String[] validBingoCmd = // play bullshit-bingo
		{ "load", "save", "reset", "import", "list", "add", "remove" };
	
	private final String[] bingoLoad = 
		{"load", "lade" };
	
	private final String[] bingoSave =
		{"save", "speicher" };
	
	private final String[] bingoReset = 
		{"reload", "reset" };
	
	private final String[] bingoImport = 
		{"import" };
	
	private final String[] bingoList = 
		{"list" };
	
	private final String[] bingoAdd = 
		{"add", "new" };
	
	private final String[] bingoRemove = 
		{"remove", "delete" };

	//-------------------------------------------------------------
	
	private final String[] giveRole = // assign a role
			{ "give", "get", "giveRole", "getRole" };

	private final String[] gmroll = // roll some dice, but only for the gm
			{ "gmroll", "gmthrow", "gamemaster", "gm", "gmr" };

	private final String prefix = "!";

	private final String[] removeRole = // remove said role
			{ "removeRole", "remove" };

	private final String[] roll = // roll some dice
			{ "roll", "throw", "werfe", "w�rfel", "r", "w�rfle", "rolle" };

	private final String[] tick = // do something with the tickleiste
			{ "tick", "tickleiste", "battle", "t" };

	private final String[] tickAddEnemy = // adds a new Enemy to the tickbar
			{ "addenemy", };

	private final String[] tickCreate = // create a new tickleiste
			{ "create", "new" };

	private final String[] tickJoin = // joins the leiste
			{ "join" };

	private final String[] tickList = // list the actions that are about to happen
			{ "list", "display", "show" };

	private final String[] tickMove = // move your character a set amount of ticks
			{ "move", "movechar" };

	private final String[] tickNext = // move one step on the leiste
			{ "next", "tick", "step" };

	private final String[] tickPos = // show your current position
			{ "pos", "position", "current", "me" };

	private final String[] tickSet = // set your character on a specific field
			{ "set" };

	private final String[] tickStart = // start the battle
			{ "start", "fight", "battle" };

	// -----------------------------Valid CMDs--------------------------------
	private final String[] validCommands = { "roll", "gmroll", "giveRole", "removeRole", "tick", "bingo", "debug" };

	private final String[] validOperator = { "+", "-" };

	// -----------------------------Valid ticks--------------------------------
	private final String[] validTickCmd = { "create", "start", "next", "move", "set", "pos", "list", "join",
			"addenemy" };

	public Commands() {
	} // currently no constructor is needed

	/**
	 * checks if a command is valid in context of the tick parent-cmd. Only checks
	 * the second argument
	 *
	 * @param cmd Command
	 * @return valid
	 */
	public boolean checkValidTickCommand(Command cmd) {
		return Arrays.asList(this.validTickCmd).contains(cmd.getArgs()[0]);
	}

	/**
	 * corrects spelling of command and returns standard command string
	 *
	 * @param command Command
	 * @return string correct command
	 */
	public Command correctCommand(Command command) {
		String lcCommand = command.getCmd().toLowerCase();

		if (Arrays.asList(this.roll).contains(lcCommand))
			command.setCmd("roll");
		else if (Arrays.asList(this.gmroll).contains(lcCommand))
			command.setCmd("gmroll");
		else if (Arrays.asList(this.giveRole).contains(lcCommand))
			command.setCmd("giveRole");
		else if (Arrays.asList(this.removeRole).contains(lcCommand))
			command.setCmd("removeRole");
		else if (Arrays.asList(this.tick).contains(lcCommand))
			command.setCmd("tick");
		else if (Arrays.asList(this.bingo).contains(lcCommand))
			command.setCmd("bingo");

		return command;
	}

	/**
	 * corrects the command in the context of the tick command. replaces the first
	 * argument in the args array
	 *
	 * @param command Command
	 * @return Command
	 */
	public Command correctTickCommand(Command command) {
		// get second word of the command, !tick create etc
		String lcCommand = command.getArgs()[0].toLowerCase();

		if (Arrays.asList(this.tickCreate).contains(lcCommand))
			command.setArgs(0, "create");
		else if (Arrays.asList(this.tickStart).contains(lcCommand))
			command.setArgs(0, "start");
		else if (Arrays.asList(this.tickNext).contains(lcCommand))
			command.setArgs(0, "next");
		else if (Arrays.asList(this.tickPos).contains(lcCommand))
			command.setArgs(0, "pos");
		else if (Arrays.asList(this.tickList).contains(lcCommand))
			command.setArgs(0, "list");
		else if (Arrays.asList(this.tickMove).contains(lcCommand))
			command.setArgs(0, "move");
		else if (Arrays.asList(this.tickSet).contains(lcCommand))
			command.setArgs(0, "set");
		else if (Arrays.asList(this.tickJoin).contains(lcCommand))
			command.setArgs(0, "join");
		else if (Arrays.asList(this.tickAddEnemy).contains(lcCommand))
			command.setArgs(0, "addenemy");

		return command;
	}
	
	/**
	 * corrects the command in the context of the bingo command. replaces the first
	 * argument in the args array
	 *
	 * @param command Command
	 * @return Command
	 */
	public Command correctBingoCommand(Command command) {
		// get second word of the command, !bingo list etc
		String lcCommand = command.getArgs()[0].toLowerCase();

		if (Arrays.asList(this.bingoLoad).contains(lcCommand))
			command.setArgs(0, "load");
		else if (Arrays.asList(this.bingoSave).contains(lcCommand))
			command.setArgs(0, "save");
		else if (Arrays.asList(this.bingoReset).contains(lcCommand))
			command.setArgs(0, "reset");
		else if (Arrays.asList(this.bingoImport).contains(lcCommand))
			command.setArgs(0, "import");
		else if (Arrays.asList(this.bingoList).contains(lcCommand))
			command.setArgs(0, "list");
		else if (Arrays.asList(this.bingoAdd).contains(lcCommand))
			command.setArgs(0, "add");
		else if (Arrays.asList(this.bingoRemove).contains(lcCommand))
			command.setArgs(0, "remove");

		return command;
	}

	/**
	 * gets the calc arguments from the command
	 *
	 * @param command
	 * @return 1.elem operator, 2.elem integer
	 */
	public String[] getCalcArgs(Command command) {
		return new String[] { command.getArgs()[3], command.getArgs()[4] };
	}

	/**
	 * parses the command and returns the two integer values of the dice roll (f.e.
	 * 2W10 -> [2,10])
	 *
	 * @param strings Array of roll arguments as strings
	 * @return int array with dice amount and dice size
	 */
	public int[] getRollArgs(String[] strings) {

		Integer diceAmount = Integer.parseInt(strings[0]);
		Integer diceSize = Integer.parseInt(strings[2]);
		return new int[] { diceAmount, diceSize };

	}

	/**
	 * checks if str is an Integer
	 *
	 * @param str String that should be checked
	 * @return true when the given string is an integer
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
	 * checks if a command contains a valid calculation as in 2W10 + 13 etc. Command
	 * is valid if args are containing a roll, one operator and another integer ex:
	 * [2,W,10,+,13]. Further args are not considered
	 *
	 * @param cmd Command
	 * @return true if command is valid
	 */
	public boolean isValidCalc(Command cmd) {
		String[] args = cmd.getArgs();

		// does the command include the roll? normal rolls are to short and dont include
		// calc arguments
		if (this.isValidRoll(cmd) && args.length > 3)
			// the operator is located at the 4. pos in the args
			// is the used operator legal?
			if (Arrays.asList(this.validOperator).contains(args[3]))
				// is the operator followed by an integer?
				if (this.isInteger(args[4]))
					return true;
		return false;
	}

	/**
	 * checks if the given command is considered valid. Checks for correct prefix
	 * and valid instruction, but does not respect any arguments.
	 *
	 * @param cmd Command
	 * @return true if command is valid
	 */
	public boolean isValidCommand(Command cmd) {
		if (cmd.getPrefix().equals(this.prefix))
			if (Arrays.asList(this.validCommands).contains(cmd.getCmd()))
				return true;

		return false;
	}

	/**
	 * checks if the given command contains valid roll arguments. The args are
	 * considered valid if either no args are given (default roll) or 2 Integers
	 * split with a W is given ([2, W, 10] etc). Further arguments as well as the
	 * instruction are not considered
	 *
	 * @param command
	 * @return valid true if command is valid
	 */
	public boolean isValidRoll(Command command) {
		String[] args = command.getArgs();
		// check for case with no args
		if (args.length == 0)
			return true;

		// else check for the second condition

		if (this.isInteger(args[0]) && args[1].equalsIgnoreCase("w") && this.isInteger(args[2]))
			return true;
		else
			return false;

	}
	
	/**
	 * check if the given command is considered valid in the context of the bingo command
	 * @param command
	 * @return true if command is valid
	 */
	public boolean isValidBingo(Command command) {
		String[] args = command.getArgs();
		// check for case with no args, as the default !bingo is legal!
				if (args.length == 0)
					return true;
		return Arrays.asList(this.validBingoCmd).contains(command.getArgs()[0]);
	}

}
