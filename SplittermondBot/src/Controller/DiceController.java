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
					Roll r = this.model.rollDice(args);
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
		}
	}
	
	
	/**
	  * Executing the rolling of the dice and sending the embed off. This is where roll, rolltemplate and the commands
	  * come together 
	  * @deprecated
	  * 
	  * @param event	  Current message-received-event
	  * @param diceAmount How many dice needs to be rolled?
	  * @param diceSize	  How many sides does each dice have?
	  */
	 private void DiceRoll(GuildMessageReceivedEvent event, int diceAmount, int diceSize) {
		
		 Roll rollEvent = new Roll(diceAmount, diceSize);
		 int[] results =rollEvent.RollTheDice();
		
		 int[] throwParam = new int[2];
		 	   throwParam[0]= diceAmount;
		 	   throwParam[1]= diceSize;
		 
		 	   
		 RollTemplate rollTemplate= new RollTemplate();
		 EmbedBuilder embed = rollTemplate.buildRollEmbed(event, throwParam, results);
		 
		 event.getMessage().reply(embed.build()).queue();
		 embed.clear();
	 }
	
}
