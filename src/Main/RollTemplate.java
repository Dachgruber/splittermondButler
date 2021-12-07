package Main;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * 
 * @author Cornelius
 *
 * class RollTemplate manages only the Embed-Visualisation of the roll. For handling the math, refer to class "Roll"
 */
public class RollTemplate {

	EmbedBuilder embed ;

	
	public RollTemplate() {

		embed = new EmbedBuilder();
	}
	
	/**
	 * Puzzling of the embed, display the roll author, the results, the dice used etc
	 * @param event  Command-received-event
	 * @param DiceThrow  [DiceAmount,DiceSize]
	 * @param resultList list of individual results
	 * @return finished embed object
	 */
	public EmbedBuilder buildRollEmbed(GuildMessageReceivedEvent event, int[] DiceThrow, int[] resultList) {
		
		String resultString = makeListToString(resultList);
		int resultInteger = makeListToResult(resultList);
		 embed.setTitle(Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1]) + "🎲");
		 
		 embed.setDescription(event.getAuthor().getAsMention() + " rolled " 
				 				+ Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1])
				 			    + "\n" 
				 			    + "resulting in " + "**" + Integer.toString(resultInteger) + "**");
		 
		 embed.addField("The Results are:", resultString, false);
		// embed.addField("With a Sum of", Integer.toString(resultInteger), false);
		 
		 if( testIfGood(resultList, DiceThrow[1])) {
			 embed.addField("Critical Success!", "", false);
		 }
		 if( testIfBad(resultList)) {
			 embed.addField("Critical Success!", "", false);
		 }
		 
		 embed.setColor(Color.GREEN);
		 //embed.setFooter("Splittermond-Rollbutler, created by" + event.getGuild().getOwner().getAsMention() , event.getGuild().getOwner().getUser().getAvatarUrl());
		 
		 return embed;
	}
	
	public EmbedBuilder buildCheckEmbed(GuildMessageReceivedEvent event, int[] DiceThrow, int[] resultList, int checkValue) {
		
	    embed = this.buildRollEmbed(event, DiceThrow, resultList);
		
		int resultInteger = makeListToResult(resultList);
	    boolean success = false;
	    
	    if (resultInteger > checkValue) {
	    	success = true;
	    }
	    
		if (success) {
				embed.setDescription(event.getAuthor().getAsMention() + " rolled " 
						+ Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1]) + " against " + Integer.toString(checkValue)
						+ "\n" 
						+ "resulting in " + "**" + Integer.toString(resultInteger) + "**"
						+ "Which resulted in a "+ "**" + "Success!" + "**");
			}
			else {
				embed.setDescription(event.getAuthor().getAsMention() + " rolled " 
						+ Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1]) + " against " + Integer.toString(checkValue)
		 			    + "\n" 
		 			    + "resulting in " + "**" + Integer.toString(resultInteger) + "**" 
		 			    + "\n"
		 			    + "Which resulted in a "+ "**" + "failure" + "**");
			}
		
		return embed;
	}
	
	private boolean testIfGood(int[] resultList, int bestPossible) {
		boolean goodThrow = false;
		boolean firstGoodThrowCatched = false;
		for (int i = 0; i <resultList.length; i++) {
			 if (resultList[i] == bestPossible || resultList[i] ==  bestPossible-1) {
				 if (!firstGoodThrowCatched) {
					 firstGoodThrowCatched = true;
				 }
				 else {
					 goodThrow = true;
					 break;
				 }
			 }
		}
		return goodThrow;
			 
	}
	
	private boolean testIfBad(int[] resultList) {
		boolean badThrow = false;
		boolean firstBadThrowCatched = false;
		for (int i = 0; i <resultList.length; i++) {
			 if (resultList[i] == 0 || resultList[i] ==  1) {
				 if (!firstBadThrowCatched) {
					 firstBadThrowCatched = true;
				 }
				 else {
					 badThrow = true;
					 break;
				 }
			 }
		}
		return badThrow;
			 
	}
	
	private String makeListToString(int[] resultList) {
		 String temp = "";
		 for (int i = 0; i <resultList.length; i++) {
			 temp += Integer.toString(resultList[i]);
			 temp += " ";
		 }
		 return temp;
	}
	
	private int makeListToResult(int[] resultList) {
		 int temp = 0;
		 for (int i = 0; i <resultList.length; i++) {
			 temp += resultList[i];
		 }
		 return temp;
	}
}
