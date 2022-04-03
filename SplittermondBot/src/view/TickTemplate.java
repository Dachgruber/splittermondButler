package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * @author Cornelius
 *         <p>
 *         class RollTemplate manages only the Embed-Visualisation of the roll.
 *         For handling the math, refer to class "Roll"
 */
public class TickTemplate {
	EmbedBuilder embed;

	public TickTemplate() {
		this.embed = new EmbedBuilder();
	}

	/**
	 * creates a embed for a current state of the tickBar
	 *
	 * @param event
	 * @param currentTick
	 * @param turn
	 * @param enemies
	 * @param nextMoves
	 * @return
	 */
	public EmbedBuilder buildTickEmbed(GuildMessageReceivedEvent event, int currentTick, String turns,
			ArrayList<ArrayList<String>> nextMoves) {

		this.embed.setTitle("We are at Tick: " + currentTick);
		String desc = "";
		String[] turn = turns.split(",");
		for (String entry : turn)
			desc += "its " + entry + " turn!\n";

		this.embed.setDescription(desc);

		String moves = "";
		for (ArrayList<String> l : nextMoves) {
			for (String c : l)
				moves += c + "  ";
			moves += "\n ";
		}

		this.embed.addField("The next moves are:", moves, false);
		// embed.addField("With a Sum of", Integer.toString(resultInteger), false);

		this.embed.setColor(Color.BLUE);
		// embed.setFooter("Splittermond-Rollbutler, created by" +
		// event.getGuild().getOwner().getAsMention() ,
		// event.getGuild().getOwner().getUser().getAvatarUrl());

		return this.embed;
	}

	public EmbedBuilder buildStartingEmbed(GuildMessageReceivedEvent event, ArrayList<String> playerNames) {

		this.embed.setTitle("The Fight starts!");
		String desc = "";
		for (String playerName : playerNames)
			desc += playerName + " " + this.getRandomTaunt() + "\n";
		this.embed.setDescription(desc);

		this.embed.setColor(Color.BLUE);
		// embed.setFooter("Splittermond-Rollbutler, created by" +
		// event.getGuild().getOwner().getAsMention() ,
		// event.getGuild().getOwner().getUser().getAvatarUrl());

		return this.embed;
	}

	private String getRandomTaunt() {
		String[] taunts = { "fights", "starts in the battle", "is ready to clash", "wants to brawl", "is giga tylt",
				"is ready to rumble" };
		Random rand = new Random();
		return taunts[rand.nextInt(taunts.length)];
	}

	private boolean testIfGood(int[] resultList, int bestPossible) {
		boolean goodThrow = false;
		boolean firstGoodThrowCatched = false;
		for (int element : resultList)
			if (element == bestPossible || element == bestPossible - 1)
				if (!firstGoodThrowCatched)
					firstGoodThrowCatched = true;
				else {
					goodThrow = true;
					break;
				}
		return goodThrow;

	}

	private boolean testIfBad(int[] resultList) {
		boolean badThrow = false;
		boolean firstBadThrowCatched = false;
		for (int element : resultList)
			if (element == 0 || element == 1)
				if (!firstBadThrowCatched)
					firstBadThrowCatched = true;
				else {
					badThrow = true;
					break;
				}
		return badThrow;

	}

	private String makeListToString(int[] resultList) {
		String temp = "";
		for (int element : resultList) {
			temp += Integer.toString(element);
			temp += " ";
		}
		return temp;
	}

	private int makeListToResult(int[] resultList) {
		int temp = 0;
		for (int element : resultList)
			temp += element;
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
		for (int i = 1; i < arr.length; i++)
			if (fmax < arr[i]) {
				smax = fmax;
				fmax = arr[i];
			} else if (smax < arr[i])
				smax = arr[i];

		// return second largest number
		return smax;
	}

	private int largest(int[] arr) {
		// Create maxValue variable and initialize with 0
		int maxValue = 0;

		// Check maximum element using for loop
		for (Integer integer : arr)
			if (integer > maxValue)
				maxValue = integer;
		return maxValue;
	}
}
