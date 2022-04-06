package view;

import java.util.ArrayList;

import model.Roll;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface View {
	/**
	 * sends a Message msg into the current channel
	 *
	 * @param msg
	 */
	void displayMsg(String msg);

	/**
	 * replies to the author of the message
	 */
	void reply(String msg);

	/**
	 * displays a rollEvent as a reply to the initiator
	 *
	 * @param roll
	 */
	void displayRoll(Roll roll);

	/**
	 * sends an Exception that occured during rolling
	 *
	 * @param exc
	 */
	void displayError(Exception exc);

	GuildMessageReceivedEvent getCurrentEvent();

	void setCurrentEvent(GuildMessageReceivedEvent currentEvent);

	void displayTickNew();

	void displayTickContent(int i, String turnsAtTick, ArrayList<ArrayList<String>> nextMoves);

	void displayTickStart(ArrayList<String> playerNames);

	void displayTickPosition(int pos);

//	void displayOnlineImg(String string);

	void displayLocalImg(String string);

	void displayGMRoll(Roll r);

	void askGM(String string);

	void displayBingo(String[] bingoResult);
}
