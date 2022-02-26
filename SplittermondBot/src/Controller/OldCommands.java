package Controller;



import java.util.Scanner;

import Model.Roll;
import View.RollTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
/**
 * 
 * @author Cornelius
 * 
 * Class Commands acts as the join between the roll-butler code and the Discord API
 *@deprecated
 *
 */
public class OldCommands extends ListenerAdapter {
	 
	public String prefix = "!";	//specifies the prefix for every Message ("!roll", "!msg", etc) fff
	
	/*
		 @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

	        MessageReaction reaction = event.getReaction();
	        ReactionEmote emote = reaction.getReactionEmote();
	        MessageChannel channel = event.getChannel();
	        System.out.println("test");
	        channel.sendMessage("Commands:\n !notify - notifies users who have subscribed to mailing list").queue();
	    }
	
	*/
	
	 /**
	  * Handling of received messages, calls corresponding functions and handles possible errors
	  * 
	  */
	 
	 public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		 
		 String[] args = event.getMessage().getContentRaw().split(" ");	//Message gets stored in String-Array
		 
		 
		 //test-commands for the two possible message functions
		 if (args[0].equalsIgnoreCase(prefix + "reply")) {
			 event.getMessage().reply("Lmao").queue();
		 }
		 
		 else if (args[0].equalsIgnoreCase(prefix + "msg")) {
			 event.getChannel().sendMessage("lol").queue();
		 }
		 
		 
		 //main Method for Battling
		 else if (args[0].equalsIgnoreCase(prefix + "privat")) {
			 event.getMessage().addReaction("⚔️").queue();
		 }
		 
		 
		 // main method for doing a check against a character value
		 
		 else if (args[0].equalsIgnoreCase(prefix + "check")) {
			 
			 if (args.length == 2) {		//Does the user specifies the value?
											//if not, throw error

				 	if (this.isInteger(args[1]) ) {
				 				CheckRoll(event, Integer.parseInt(args[1]));
				 			} 
				 	} 

			 else
			 {
				 event.getChannel().sendMessage("You need to specify a value").queue();

			 }
	 	}
		 
		 
		 // main method for rolling the dice
//		 else if (args[0].equalsIgnoreCase(prefix + "roll")) {
//			
//			if (args.length == 3) {		//Does the user specifies dice amount and dice size?
//										//if not, roll the default toll (2w10)
//				
//				if (this.isInteger(args[1]) ) {
//				 	if (this.isValidDice(args[2])) 
//				 	{
//				 		DiceRoll(event, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
//				 	} 
//			 	} 
//				
//			}
//			else
//			{
//				DiceRoll(event, 2, 10); //Default Roll
//			
//			}
		 //RollTemplate rolltemp = new RollTemplate();
		 //EmbedBuilder embed = rolltemp.getEmbed(event);
		 //event.getChannel().sendMessage(embed.build()).queue();
		 //embed.clear();
		 
		//}
		 
		 else if (args[0].equalsIgnoreCase(prefix + "save")) {
				
			 	DiceRoll(event, 4, 10);
			 	
		 }

		 else if (args[0].equalsIgnoreCase(prefix + "risk")) {
				
			 	DiceRoll(event, 4, 10);
			 	
		 }
			 
			 
		 else if (args[0].equalsIgnoreCase(prefix + "roll")) {
				
				if (args.length == 3) {		//Does the user specifies dice amount and dice size?
											//if not, roll the default toll (2w10)
					
					if (this.isInteger(args[1]) ) {
						if (this.isInteger(args[2])) 
					 	{
					 		DiceRoll(event, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					 	}
						
						
					 	if (this.isValidDice(args[2])) 
					 	{
					 		DiceRoll(event, Integer.parseInt(args[1]), this.makeDiceToNumber(args[2]));
					 	} 
				 	} 
					
				}
				else
				{
					DiceRoll(event, 2, 10); //Default Roll
				
				}
			 //RollTemplate rolltemp = new RollTemplate();
			 //EmbedBuilder embed = rolltemp.getEmbed(event);
			 //event.getChannel().sendMessage(embed.build()).queue();
			 //embed.clear();
			 
			 }
			 
		 
		 
		else if (args[0].equalsIgnoreCase(prefix + "giverole")) {
			 if (event.getMessage().getMentionedRoles().toArray().length == 1) {
				 if (event.getMessage().getMentionedUsers().toArray().length == 1) {
					 Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
					 Role roleToGive = event.getMessage().getMentionedRoles().get(0);
					 event.getGuild().addRoleToMember(member, roleToGive).queue();
					 event.getMessage().reply("Role " +  roleToGive.getAsMention() + "succesfully given to " + member.getAsMention()).queue();
				 } else {
					 event.getMessage().reply("Please mention only 1 user").queue();
				 }
			 } else {
				 event.getMessage().reply("Please mention only 1 role to give").queue();
			 }
		}
		 
		else if (args[0].equalsIgnoreCase(prefix + "removerole")) {
			 if (event.getMessage().getMentionedRoles().toArray().length == 1) {
				 if (event.getMessage().getMentionedUsers().toArray().length == 1) {
					 Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
					 Role roleToRemove = event.getMessage().getMentionedRoles().get(0);
					 event.getGuild().removeRoleFromMember(member, roleToRemove).queue();
					 event.getMessage().reply("Role " +  roleToRemove.getAsMention() + "succesfully removed from " + member.getAsMention()).queue();
				 } else {
					 event.getMessage().reply("Please mention only 1 user").queue();
				 }
			 } else {
				 event.getMessage().reply("Please mention only 1 role to remove").queue();
			 }}
		}
	 


	



	/**
	  * Executing the rolling of the dice and sending the embed off. This is where roll, rolltemplate and the commands
	  * come together
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
	 
	 private void CheckRoll(GuildMessageReceivedEvent event, int checkValue) {
			
		 Roll rollEvent = new Roll(2, 10);
		 int[] results =rollEvent.RollTheDice();
		 
		 int[] throwParam = new int[2];
		 	   throwParam[0]= 2;
		 	   throwParam[1]= 10;
		 
		 RollTemplate rollTemplate= new RollTemplate();
		 EmbedBuilder embed = rollTemplate.buildCheckEmbed(event, throwParam, results, checkValue);
		 
		 event.getMessage().reply(embed.build()).queue();
		 embed.clear();
		 
		 
		 
		 
	 }
	 
	private boolean isInteger(String str) {
		    try {
		        Integer.parseInt(str);
		        return true;
		    } catch (NumberFormatException nfe) {
		        return false;
		    }
		}
	 
	 private boolean isValidDice(String string) {
		Scanner sc = new Scanner(string);
		String[] parts = 
		
		Boolean isValid = false;
			if (parts[0].equals("W")) {
				if (this.isInteger(parts[1])) {
					isValid = true;
				}
			}
			return isValid;
		}
	
	  private int makeDiceToNumber(String input) {
		  //String string = input.toLowerCase();
		  String[] parts = input.split("(?<=W)", 2);
		  return Integer.parseInt(parts[1]);
			
	  }
		
		

}
