package View;

import java.awt.Color;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * 
 * @author Cornelius
 *
 * class RollTemplate manages only the Embed-Visualisation of the roll. For handling the math, refer to class "Roll"
 */
public class TickTemplate {

	EmbedBuilder embed ;

	
	public TickTemplate() {

		embed = new EmbedBuilder();
	}
	
	/**
	 * creates a embed for a current state of the tickBar
	 * @param event
	 * @param currentTick 
	 * @param turn
	 * @param nextMoves
	 * @return
	 */
	public EmbedBuilder buildTickEmbed(GuildMessageReceivedEvent event, int currentTick, User[] turn, ArrayList<ArrayList<String>> nextMoves) {
		
		 embed.setTitle("We are at Tick: " + Integer.toString(currentTick));
		 String desc = "";
		 for (User entry : turn) {
			 desc += "its " +  entry.getAsMention() + "turn!\n";
		 }
		 embed.setDescription(desc);
		 
		 String moves = "";
		 for (ArrayList<String> l: nextMoves) {
			 for (String c: l) {
				 moves += c + "  ";
			 }
			 moves += "\n ";
		 }
		 
		 embed.addField("The next moves are:", moves, false);
		// embed.addField("With a Sum of", Integer.toString(resultInteger), false);
		 
	
		 
		 embed.setColor(Color.BLUE);
		 //embed.setFooter("Splittermond-Rollbutler, created by" + event.getGuild().getOwner().getAsMention() , event.getGuild().getOwner().getUser().getAvatarUrl());
		 
		 return embed;
	}
	
	public EmbedBuilder buildStartingEmbed(GuildMessageReceivedEvent event, User[] playerNames, Integer[] playerPos) {
		
		 embed.setTitle("The Fight starts!");
		 String desc = "";
		 for (int i = 0; i < playerNames.length; i++) {
			 desc += playerNames[i].getAsMention() + " fights, starting at " + Integer.toString(playerPos[i]) + "\n";
		 }
		 embed.setDescription(desc);
		 
		 embed.setColor(Color.BLUE);
		 //embed.setFooter("Splittermond-Rollbutler, created by" + event.getGuild().getOwner().getAsMention() , event.getGuild().getOwner().getUser().getAvatarUrl());
		 
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
	
	// Java method to find second largest 
	// number in array
	private int secondlargest(int[] arr) {
	    
	  // declare variables
	  int fmax = 0; // first largest
	  int smax = 0; // second largest

	  // assign first element to fmax, smax
	  fmax = arr[0];
	  smax = arr[0];

	  // compare with remaining elements
	  // loop
	  for (int i = 1; i < arr.length; i++) {
	    if (fmax < arr[i]) {
	      smax = fmax;
	      fmax = arr[i];
	    } else if(smax < arr[i]) {
	      smax = arr[i];
	    }
	  }

	  // return second largest number
	  return smax;
	}
	
	private int largest(int [] arr) {
		// Create maxValue variable and initialize with 0
        int maxValue = 0;
  
        // Check maximum element using for loop
        for (Integer integer : arr) {
            if (integer > maxValue)
                maxValue = integer;
        }
        return maxValue;
	}
}
