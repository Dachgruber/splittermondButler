package view;

import java.awt.Color;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * @author Cornelius
 * <p>
 * class RollTemplate manages only the Embed-Visualisation of the roll. For handling the math, refer to class "Roll"
 */
public class MiscTemplate {
    EmbedBuilder embed;


    public MiscTemplate() {

        embed = new EmbedBuilder();
    }

    /**
     * creates a embed for a current state of the tickBar
     *
     * @param event Event
     * @param currentTick current tick
     * @param turn turn
     * @param nextMoves next moves
     * @return builder
     */
    public EmbedBuilder buildBingoEmbed(GuildMessageReceivedEvent event, Member gm, String bingoResult) {
        embed.setTitle("Another round of BullshitBingo!");

        embed.setDescription(event.getAuthor().getAsMention() + " rolled " + gm.getAsMention() + " 's bullshit bingo!");
        embed.addField("You are recieving a special prize!", bingoResult, false);

        embed.setColor(Color.YELLOW);

        return embed;
    }
}
