package view;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * @author Cornelius
 *         <p>
 *         class RollTemplate manages only the Embed-Visualisation of the roll.
 *         For handling the math, refer to class "Roll"
 */
public class MiscTemplate {
	EmbedBuilder embed;

	public MiscTemplate() {

		this.embed = new EmbedBuilder();
	}

	/**
	 * creates a embed for a current state of the tickBar
	 *
	 * @param event       Event
	 * @param currentTick current tick
	 * @param turn        turn
	 * @param nextMoves   next moves
	 * @return builder
	 */
	public EmbedBuilder buildBingoEmbed(GuildMessageReceivedEvent event, Member gm, String[] bingoResult) {
		this.embed.setTitle("Another round of BullshitBingo!");

		this.embed.setDescription(
				event.getAuthor().getAsMention() + " rolled " + gm.getAsMention() + " 's bullshit bingo!");
		this.embed.addField("You are recieving a special prize!", this.buildBingoString(bingoResult), false);

		this.embed.setColor(Color.YELLOW);

		return this.embed;
	}
	
	private String buildBingoString(String[] resultArray) {
		return (resultArray[0] + ": **" + resultArray[1] + "** " + resultArray[2]);
	}
}
