package view;

import java.awt.Color;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import model.Roll;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * @author Cornelius
 * <p>
 * class RollTemplate manages only the Embed-Visualisation of the roll. For handling the math, refer to class "Roll"
 */
public class RollTemplate {
    EmbedBuilder embed;


    public RollTemplate() {
        embed = new EmbedBuilder();
    }

    /**
     * Puzzling of the embed, display the roll author, the results, the dice used etc
     *
     * @param event      Command-received-event
     * @param DiceThrow  [DiceAmount,DiceSize]
     * @param resultList list of individual results
     * @return finished embed object
     */
    public EmbedBuilder buildRollEmbed(GuildMessageReceivedEvent event, Roll rollEvent) {

        embed.setTitle(rollEvent.getDiceAmount() + " W" + rollEvent.getDiceSize() + "🎲");

        embed.setDescription(event.getAuthor().getAsMention() + " rolled "
                + rollEvent.getDiceAmount() + " W" + rollEvent.getDiceSize()
                + "\n"
                + "resulting in " + "**" + rollEvent.getResult() + "**");

        embed.addField("The Results are:", this.makeListToString(rollEvent.getResultField()), false);
        // embed.addField("With a Sum of", Integer.toString(resultInteger), false);

        if (testIfGood(rollEvent.getResultField(), rollEvent.getDiceSize())) {
            embed.addField("Critical Success!", "", false);
        }
        if (testIfBad(rollEvent.getResultField())) {
            embed.addField("Critical Failure!", "", false);
        }

        embed.setColor(Color.GREEN);
        //embed.setFooter("Splittermond-Rollbutler, created by" + event.getGuild().getOwner().getAsMention() , event.getGuild().getOwner().getUser().getAvatarUrl());

        return embed;
    }

    public EmbedBuilder buildPrivateRollEmbed(GuildMessageReceivedEvent currentEvent, Roll rollEvent) {
        embed = this.buildRollEmbed(currentEvent, rollEvent);
        embed.setTitle("GMRoll: " + rollEvent.getDiceAmount() + " W" + rollEvent.getDiceSize() + "🎲");
        embed.setDescription(currentEvent.getAuthor().getAsMention() + " rolled secrently"
                + rollEvent.getDiceAmount() + " W" + rollEvent.getDiceSize()
                + " to you.\n"
                + "resulting in " + "**" + rollEvent.getResult() + "**");
        return embed;
    }

//	public EmbedBuilder buildCheckEmbed(GuildMessageReceivedEvent event, Roll rollEvent) {
//		
//	    embed = this.buildRollEmbed(rollEvent);
//		
//		int resultInteger = makeListToResult(resultList);
//	    boolean success = false;
//	    
//	    if (resultInteger > checkValue) {
//	    	success = true;
//	    }
//	    
//		if (success) {
//				embed.setDescription(event.getAuthor().getAsMention() + " rolled " 
//						+ Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1]) + " against " + Integer.toString(checkValue)
//						+ "\n" 
//						+ "resulting in " + "**" + Integer.toString(resultInteger) + "**"
//						+ "Which resulted in a "+ "**" + "Success!" + "**");
//			}
//			else {
//				embed.setDescription(event.getAuthor().getAsMention() + " rolled " 
//						+ Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1]) + " against " + Integer.toString(checkValue)
//		 			    + "\n" 
//		 			    + "resulting in " + "**" + Integer.toString(resultInteger) + "**" 
//		 			    + "\n"
//		 			    + "Which resulted in a "+ "**" + "failure" + "**");
//			}
//		
//		return embed;
//	}
//	
//	public EmbedBuilder buildSaveEmbed(GuildMessageReceivedEvent event, int[] DiceThrow, int[] resultList, int checkValue) {
//		
//	    embed = this.buildCheckEmbed(event, DiceThrow, resultList, checkValue);
//	    
//	    int resultInteger = makeListToResult(resultList);
//		int checkInteger = largest(resultList) + secondlargest(resultList);
//	    boolean success = false;
//	    
//	    if (checkInteger > checkValue) {
//	    	success = true;
//	    }
//	    
//		if (success) {
//				embed.setDescription(event.getAuthor().getAsMention() + " rolled a save roll wirh" 
//						+ Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1]) + " against " + Integer.toString(checkValue)
//						+ "\n" 
//						+ "resulting in " + "**" + Integer.toString(resultInteger) + "**"
//						+ "Which resulted in a "+ "**" + "Success!" + "**");
//			}
//			else {
//				embed.setDescription(event.getAuthor().getAsMention() + " rolled " 
//						+ Integer.toString(DiceThrow[0]) + " W" + Integer.toString(DiceThrow[1]) + " against " + Integer.toString(checkValue)
//		 			    + "\n" 
//		 			    + "resulting in " + "**" + Integer.toString(resultInteger) + "**" 
//		 			    + "\n"
//		 			    + "Which resulted in a "+ "**" + "failure" + "**");
//			}
//		
//		return embed;
//	}

    /**
     * checks if the throw is considered a critical success.
     * Only twice the bestPossible, or one bestPossible and one bestPossible-1 result in critical success
     * @param resultList
     * @param bestPossible
     * @return true if resultList contains criticalSuccess
     */
    private boolean testIfGood(int[] resultList, int bestPossible) {
    	//the bestPossible has to be included everytime
    	if (ArrayUtils.contains(resultList, bestPossible)){
    		//remove the bestPossible to search for another one
    		int[] tempArray = ArrayUtils.remove(resultList,  ArrayUtils.indexOf(resultList, bestPossible));
    		//if the num next to bestPossible is included or the bestPossible is included twice, the throw is a success!
    		if (ArrayUtils.contains(tempArray, bestPossible-1) || ArrayUtils.contains(tempArray, bestPossible)) {
    			return true;
    		}
    	}
    	return false;
    }
   
    /**
     * checks if the throw is considered a critical failure.
     * Only twice the bestPossible, or one bestPossible and one bestPossible-1 result in critical success
     * @param resultList
     * @param bestPossible
     * @return true if resultList contains criticalSuccess
     */
    private boolean testIfBad(int[] resultList) {
    	//the worst possible result 1 has to be included everytime
    	if (ArrayUtils.contains(resultList, 1)) {
    		//remove the 1 to search for a second 1
    		int[] tempArray = ArrayUtils.remove(resultList, ArrayUtils.indexOf(resultList, 1));
    		//if another 1 or a 2 is included, the throw is a failure!
    		if (ArrayUtils.contains(tempArray, 1) || ArrayUtils.contains(tempArray, 2)) {
    			return true;
    		}
    	}
    	return false;
    }

    private String makeListToString(int[] resultList) {
        String temp = "";
        for (int i = 0; i < resultList.length; i++) {
            temp += Integer.toString(resultList[i]);
            temp += " ";
        }
        return temp;
    }

    private int makeListToResult(int[] resultList) {
        int temp = 0;
        for (int i = 0; i < resultList.length; i++) {
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
            } else if (smax < arr[i]) {
                smax = arr[i];
            }
        }

        // return second largest number
        return smax;
    }

    private int largest(int[] arr) {
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
