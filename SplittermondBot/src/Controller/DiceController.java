package Controller;

import javax.security.auth.login.LoginException;

import Model.DiceModel;
import Model.Model;
import Model.Roll;
import View.DiscordView;
import View.RollTemplate;
import View.View;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * The DiceController manages the input command and executes the corresponding functions of the model
 * @author Cornelius
 *
 */
public class DiceController implements Controller{
	View view;
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
	public void executeCommand(String fullcommand) {
		
		//first task: make an actual command:
		Command command = new Command(fullcommand);
		
		//then, try to correct any spelling mistakes for the first argument
		cmd.correctCommand(command);
		
		//last: check, if its valid:
		if (!cmd.isValidCommand(command)) {
			this.view.displayError(new IllegalArgumentException("Wrong Command you little dipshit"));
		}
		else {
		
			//finally: execute the corresponding action
			
			//if the action equals "roll" or "gmroll", get the arguments, create a new RollEvent and display it
			if ( (command.getCmd().equals("roll")) || (command.getCmd().equals("gmroll")) ) {
				if (cmd.checkValidRoll(command.getArgs())) {
					
					//sets the default value to 2W10
					Integer[] args = {2,10};
					//if arguments are given, override the default values
					if (command.getArgs().length > 0) {
						args = cmd.getRollArgs(command.getArgs());
					}
					Roll r;
					
					//if the command includes a valid arithmetic expression, calculate the result first
					if (cmd.isValidCalc(command)) {
						
						//System.out.println("calc arguments are: ");   //DEBUG
						//System.out.println(command.getArgs()[1]);
						//ystem.out.println(command.getArgs()[2]);
						
						String[] calcArgs = {command.getArgs()[1],command.getArgs()[2]};
						
						//the roll being created with calculate-Arguments
						r = this.model.rollDice(args, calcArgs);
					}
					else
						
						//if no arithmetic expression is given, roll a "normal" set of dice
						r = this.model.rollDice(args);
					
					//check if "gmroll" was given. If true, display the result in a secret gmroll to the GM only
					if (command.getCmd().equals("roll"))
						this.view.displayRoll(r);
					else
						this.view.displayGMRoll(r);
					
					//DiceRoll(this.view.getCurrentEvent(), args[0], args[1]);
				}
			}
			
			
			//if the action equals "tick", create a new "tickleiste" and act
			else if (command.getCmd().equals("tick")) {
				
				//does the tick come with a second  command?
				if (command.getArgs().length != 0) {
					//get the second command in line, so that we can initiate the corresponding tickBar Action:
					//correct the command in the second argument
					cmd.correctTickCommand(command);
			
					//is the tick command valid?
					if (cmd.checkvalidTickCommand(command)) {
						//get the first argument and check what action should be done
						String secondCommand = command.getArgs()[0];
						//check if current model as a tickbar, creates if 
						if (secondCommand.equals("create")) {
						this.model.newTickBar();
						}
						else if (secondCommand.equals("join")) {
							this.model.joinPlayer(command.getArgs()[1]);
						}
						else if (secondCommand.equals("next")) {
							this.model.tick();
						}
						else if (secondCommand.equals("start")) {
							this.model.startBattle();
						}
						else if (secondCommand.equals("list")) {
							this.model.listTickBar();
						}
						else if (secondCommand.equals("pos")) {
							this.model.showPosOfPlayer();
						}
						else if (secondCommand.equals("move")) {
							this.model.movePlayer(command.getArgs()[1]);
						}
					}
						else
							this.view.displayMsg("do you have the stupid - invalid tick command");
					
					}
				else
					this.view.displayMsg("donkey - second tick command is missing");
				
			}
			
			if (command.getCmd().equals("bingo")) {
				String bingoResult = this.model.rollBingo();
				this.view.displayBingo(bingoResult);
				
//				if (cmd.checkValidRoll(command.getArgs())) {
//					Integer[] args = {1,16};
//					Roll r = this.model.rollDice(args);
//					this.view.displayRoll(r);
//					//this.view.displayLocalImg("img/bingo.jpg");
				
			}
		}
	}
	
}
