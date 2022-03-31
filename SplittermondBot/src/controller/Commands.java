package controller;

import java.util.Arrays;

/**
 * this class validates the given command and can correct a command to the corresponding correct command
 *
 * @author Cornelius
 */
public class Commands {

    private final String prefix = "!";

    private final String validOperator[] =
            {"+", "-"};

    //-----------------------------Valid CMDs--------------------------------
    private final String validCommands[] =
            //{"roll","giveRole","removeRole"};
            {"roll", "gmroll", "giveRole", "removeRole", "tick", "bingo"};


    private final String roll[] = //roll some dice
            {"roll", "throw", "werfe", "w�rfel", "r", "w�rfle", "rolle"};

    private final String gmroll[] = //roll some dice, but only for the gm
            {"gmroll", "gmthrow", "gamemaster", "gm", "gmr"};

    private final String giveRole[] = //assign a role
            {"give", "get", "giveRole", "getRole"};

    private final String removeRole[] = //remove said role
            {"removeRole", "remove"};

    private final String tick[] =    //do something with the tickleiste
            {"tick", "tickleiste", "battle", "t"};

    private final String bingo[] =    //play bullshit-bingo
            {"bbb", "bullshit", "bingo", "bullshitbingo", "nik"};


    //-----------------------------Valid ticks--------------------------------
    private final String validTickCmd[] =
            {"create", "start", "next", "move", "set", "pos", "list", "join", "addenemy"};


    private final String tickCreate[] = //create a new tickleiste
            {"create", "new"};

    private final String tickStart[] = //start the battle
            {"start", "fight", "battle"};

    private final String tickJoin[] = //joins the leiste
            {"join"};

    private final String tickNext[] = //move one step on the leiste
            {"next", "tick", "step"};

    private final String tickMove[] = //move your character a set amount of ticks
            {"move", "movechar"};

    private final String tickSet[] =  //set your character on a specific field
            {"set"};

    private final String tickPos[] = //show your current position
            {"pos", "position", "current", "me"};

    private final String tickList[] = //list the actions that are about to happen
            {"list", "display", "show"};

    private final String tickAddEnemy[] = //adds a new Enemy to the tickbar
            {"addenemy",};


    public Commands() {

    }

    /**
     * checks if a string is accepted as a command
     *
     * @param command string
     * @return true if command is valid
     */
    public boolean isValidCommand(Command cmd) {
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
     * checks if a command contains a valid calculation as in 2W10 + 13 etc
     *
     * @param command string
     * @return true if command is valid
     */
    public boolean isValidCalc(Command cmd) {
        boolean valid = false;

        if ((cmd.getArgs().length > 1)) {
            System.out.println("the to checked operator is:");
            System.out.println(cmd.getArgs()[1]);
            if (Arrays.asList(validOperator).contains((cmd.getArgs()[1]))) { //is the used operator legal?

                if (this.isInteger(cmd.getArgs()[2])) {                    //is the operator followed by an integer?
                    System.out.println("Right command");
                    valid = true;
                }
            }
        }
        return valid;

    }


    /**
     * corrects spelling of command and returns standard command string
     *
     * @param command
     * @param i
     * @return string correct command
     */
    public Command correctCommand(Command command) {
        String lcCommand = command.getCmd().toLowerCase();
        if (Arrays.asList(roll).contains(lcCommand))
            command.setCmd("roll");
        else if (Arrays.asList(gmroll).contains(lcCommand))
            command.setCmd("gmroll");
        else if (Arrays.asList(giveRole).contains(lcCommand))
            command.setCmd("giveRole");
        else if (Arrays.asList(removeRole).contains(lcCommand))
            command.setCmd("removeRole");
        else if (Arrays.asList(tick).contains(lcCommand))
            command.setCmd("tick");
        else if (Arrays.asList(bingo).contains(lcCommand))
            command.setCmd("bingo");
        return command;

    }

    /**
     * checks if a command is valid in context of the tick parent-cmd. Only checks the second argument
     *
     * @param Command cmd
     * @return
     */
    public boolean checkvalidTickCommand(Command cmd) {
        boolean valid = false;
        if (Arrays.asList(validTickCmd).contains(cmd.getArgs()[0])) {
            valid = true;
        }
        return valid;
    }

    /**
     * corrects the command in the context of the tick command. replaces the first argument in the args array
     *
     * @param string
     * @return
     */
    public Command correctTickCommand(Command command) {

        //get second word of the command, !tick create etc
        String lcCommand = command.getArgs()[0].toLowerCase();
        if (Arrays.asList(tickCreate).contains(lcCommand))
            command.setArgs(0, "create");
        else if (Arrays.asList(tickStart).contains(lcCommand))
            command.setArgs(0, "start");
        else if (Arrays.asList(tickNext).contains(lcCommand))
            command.setArgs(0, "next");
        else if (Arrays.asList(tickPos).contains(lcCommand))
            command.setArgs(0, "pos");
        else if (Arrays.asList(tickList).contains(lcCommand))
            command.setArgs(0, "list");
        else if (Arrays.asList(tickMove).contains(lcCommand))
            command.setArgs(0, "move");
        else if (Arrays.asList(tickSet).contains(lcCommand))
            command.setArgs(0, "set");
        else if (Arrays.asList(tickJoin).contains(lcCommand))
            command.setArgs(0, "join");
        else if (Arrays.asList(tickAddEnemy).contains(lcCommand))
            command.setArgs(0, "addenemy");
        return command;


    }

    /**
     * checks if the command acts as a valid roll command.
     * A command is considered valid if either no args are given (default roll) or 2 Integers split with a W is given (2W10 etc)
     *
     * @param strings
     * @return
     */
    public boolean checkValidRoll(String[] args) {
        //check for case with no args
        if (args.length == 0) return true;

        //else check for the second condition
        String[] parts = args[0].split("W");
        if (this.isInteger(parts[0]) && this.isInteger(parts[1])) return true;

        //command was not valid
        return false;
    }

    /**
     * checks if str is an Integer
     *
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
     *
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
        } else {
            return null;
        }

    }

}
