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

public class DiceController implements Controller{
	View view;
	Model model;
	Commands cmd;
	
	
	public DiceController() {
		view = new DiscordView(this);
		model = new DiceModel(view);
	    cmd = new Commands();
		
		
	}

	@Override
	public void executeCommand(String fullcommand) {
		
		//first order: make an actual command:
		Command command = new Command(fullcommand);
		
		//then, try to correct any spelling mistakes
		
		cmd.correctCommand(command);
		
		//last: check, if its valid:
		if (!cmd.isValidCommand(command)) {
			this.view.displayError(new IllegalArgumentException("Wrong Command you little dipshit"));
		}
		else {
		
			//finally: execute the corresponding action
			
			//if the action equals "roll", get the arguments, create a new RollEvent and display it
			if (command.getCmd().equals("roll")){
				if (cmd.checkValidRoll(command.getArgs())) {
					
					//sets the default value to 2W10
					Integer[] args = {2,10};
					//if arguments are given, override the default values
					if (command.getArgs().length > 0) {
						args = cmd.getRollArgs(command.getArgs());
					}
					Roll r;
					if (cmd.isValidCalc(command)) {
						System.out.println("calc arguments are: ");
						System.out.println(command.getArgs()[1]);
						System.out.println(command.getArgs()[2]);
						String[] calcArgs = {command.getArgs()[1],command.getArgs()[2]};
						r = this.model.rollDice(args, calcArgs);
					}
					else
						r = this.model.rollDice(args);
					
					
					this.view.displayRoll(r);
					//DiceRoll(this.view.getCurrentEvent(), args[0], args[1]);
				}
			}
			
			//if the action equals "tick", create a new "tickleiste" and act
			if (command.getCmd().equals("tick")) {
				this.view.displayMsg("Feature isnt implemented yet");
				
				//get the second command in line, so that we can initiate the corresponding tickBar Action:
				String secondCommand = command.getArgs()[0];
				System.out.println(secondCommand);
				
				//check if current model as a tickbar, creates if 
				if (secondCommand.equals("create")) {
					this.model.newTickBar();
				}
				else if (secondCommand.equals("join")) {
					this.model.joinPlayer(command.getArgs()[1]);
				}
				else if (secondCommand.equals("tick")) {
					this.model.tick();
				}
				else if (secondCommand.equals("start")) {
					this.model.startBattle();
				}
				else if (secondCommand.equals("move")) {
					this.model.movePlayer(command.getArgs()[1]);
				}
				
				
			}
			
			if (command.getCmd().equals("bingo")) {
				if (cmd.checkValidRoll(command.getArgs())) {
					Integer[] args = {1,16};
					Roll r = this.model.rollDice(args);
					this.view.displayRoll(r);
				}
			}
		}
	}
	
}
