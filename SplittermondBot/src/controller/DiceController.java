package controller;

import model.DiceModel;
import model.Model;
import model.Roll;
import view.DiscordView;
import view.View;

/**
 * The DiceController manages the input command and executes the corresponding functions of the model
 *
 * @author Cornelius
 */
public class DiceController implements Controller {
    View view; // TODO: decide which access right we need here
    Model model;
    Commands cmd;

    /**
     * create a fresh Controller with a new View and a new Model
     */
    public DiceController() {
        view = new DiscordView(this);
        model = new DiceModel(view);
        cmd = new Commands();
    }

    @Override
    public void executeCommand(String fullCommand) {
        // first task: make an actual command:
        Command command = new Command(fullCommand);

        // then, try to correct any spelling mistakes for the first argument
        cmd.correctCommand(command);

        // last: check, if its valid:
        if (!cmd.isValidCommand(command)) {
            this.view.displayError(new IllegalArgumentException("Wrong Command you little dipshit"));
        } else {
            // finally: execute the corresponding action

            // if the action equals "roll" or "gmroll", get the arguments, create a new RollEvent and display it
            switch (command.getCmd()) {
                case "roll":
                case "gmroll":
                    if (cmd.isValidRoll(command)) {
                        // sets the default value to 2W10
                        int[] args = {2, 10};

                        // if arguments are given, override the default values
                        if (command.getArgs().length > 0) {
                            args = cmd.getRollArgs(command.getArgs());
                        }

                        Roll r;

                        // if the command includes a valid arithmetic expression, calculate the result first
                        if (cmd.isValidCalc(command)) {
                            //System.out.println("calc arguments are: ");   //DEBUG
                            //System.out.println(command.getArgs()[1]);
                            //System.out.println(command.getArgs()[2]);

                            String[] calcArgs =cmd.getCalcArgs(command);

                            // the roll being created with calculate-Arguments
                            r = this.model.rollDice(args, calcArgs);
                        } else {
                            // if no arithmetic expression is given, roll a "normal" set of dice
                            r = this.model.rollDice(args);
                        }

                        // check if "gmroll" was given. If true, display the result in a secret gmroll to the GM only
                        if (command.getCmd().equals("roll"))
                            this.view.displayRoll(r);
                        else // roll should be a gm roll
                            this.view.displayGMRoll(r);

                        // DiceRoll(this.view.getCurrentEvent(), args[0], args[1]);
                    }
                    break;
                // if the action equals "tick", create a new "tickleiste" and act
                case "tick":

                    // does the tick come with a second  command?
                    if (command.getArgs().length != 0) {
                        // get the second command in line, so that we can initiate the corresponding tickBar Action:
                        // correct the command in the second argument
                        cmd.correctTickCommand(command);

                        // is the tick command valid?
                        if (cmd.checkValidTickCommand(command)) {
                            // get the first argument and check what action should be done
                            String secondCommand = command.getArgs()[0];
                            // check if current model as a tickbar, creates if
                            switch (secondCommand) {
                                case "create" -> {
                                    this.model.newTickBar();
                                    this.view.askGM("Please add the enemies! Use !tick addenemy [enemyname] [initative]");
                                }
                                case "join" -> this.model.joinPlayer(command.getArgs()[1]);
                                case "addenemy" -> this.model.joinEnemy(command.getArgs()[1], command.getArgs()[2]);
                                case "next" -> this.model.tick();
                                case "start" -> this.model.startBattle();
                                case "list" -> this.model.listTickBar();
                                case "pos" -> this.model.showPosOfPlayer();
                                case "move" -> this.model.movePlayer(command.getArgs()[1]);
                            }
                        } else {
                            this.view.displayMsg("do you have the stupid - invalid tick command");
                        }
                    } else {
                        this.view.displayMsg("donkey - second tick command is missing");
                    }
                    break;
                case "bingo":
                    String bingoResult = this.model.rollBingo();
                    this.view.displayBingo(bingoResult);
                    break;
            }
        }
    }
}
