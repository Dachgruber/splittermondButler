package View;

import java.util.ArrayList;

import Model.Enemy;
import Model.Roll;
import net.dv8tion.jda.api.entities.User;
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

    void displayTickContent(int i, User[] turn, Enemy[] enemies, ArrayList<ArrayList<String>> nextMoves);

    void displayTickStart(User[] playerNames, Integer[] integers);

    void displayTickPosition(int pos);

//	void displayOnlineImg(String string);

    void displayLocalImg(String string);

    void displayGMRoll(Roll r);

    void displayBingo(String bingoResult);

    void askGM(String string);
}
