package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import controller.Controller;
import model.Roll;
import model.tickbar.Enemy;
import model.tickbar.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordView extends ListenerAdapter implements View {

    private Controller cntrl;
    private GuildMessageReceivedEvent currentEvent;
    private final String TOKEN_PATH = "/bottoken.txt";
    private final String GM_ROLENAME = "Gamemaster";
    private final String PARTICIPANT_ROLENAME = "Participant";

    public DiscordView(Controller contrl) {
        this.cntrl = contrl;

        try {
            this.initateBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    /**
     * initiates the bot, handles login and displays parameters (currently playing...)
     *
     * @throws LoginException
     */
    private void initateBot() throws LoginException {

        JDABuilder jda = JDABuilder.createDefault(this.loadToken());
        jda.setActivity(Activity.playing("Throwing Dice left, right and center"));
        jda.setStatus(OnlineStatus.ONLINE);
        jda.addEventListeners(this);

        jda.setChunkingFilter(ChunkingFilter.ALL);
        jda.setMemberCachePolicy(MemberCachePolicy.ALL);
        jda.enableIntents(GatewayIntent.GUILD_MEMBERS);

        jda.build();

    }

    private String loadToken() {
        String returnString = "";
        try {
            FileReader reader = new FileReader(System.getProperty("user.dir") + TOKEN_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                returnString += line;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * required implementation of the listener
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        this.currentEvent = event;
        String args = event.getMessage().getContentRaw();
        //System.out.println("Listener Fired"); 								//DEBUG
        //System.out.println(this.currentEvent.getMessage().getContentRaw());

        //dont react if the bot send the message!
        if (!event.getAuthor().isBot())

            //only react if the correct prefix is used
            if (event.getMessage().getContentRaw().startsWith("!"))
                this.cntrl.executeCommand(args);
    }

    @Override
    public GuildMessageReceivedEvent getCurrentEvent() {
        return currentEvent;
    }

    @Override
    public void setCurrentEvent(GuildMessageReceivedEvent currentEvent) {
        this.currentEvent = currentEvent;
    }

    @Override
    public void displayMsg(String msg) {
        currentEvent.getChannel().sendMessage(msg).queue();

    }

    @Override
    public void askGM(String content) {
        Role gmRole = findRole(currentEvent.getGuild(), GM_ROLENAME);
        Member gm = currentEvent.getGuild().getMembersWithRoles(gmRole).get(0);
        gm.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(content)).queue();

    }


    @Override
    public void reply(String msg) {
        currentEvent.getMessage().reply(msg).queue();

    }

    @Override
    public void displayRoll(Roll rollEvent) {

        RollTemplate rollTemplate = new RollTemplate();
        EmbedBuilder embed = rollTemplate.buildRollEmbed(this.currentEvent, rollEvent);

        currentEvent.getMessage().reply(embed.build()).queue();
        embed.clear();
    }


    @Override
    public void displayGMRoll(Roll rollEvent) {
        RollTemplate rollTemplate = new RollTemplate();
        EmbedBuilder embed = rollTemplate.buildPrivateRollEmbed(this.currentEvent, rollEvent);


        Role gmRole = findRole(currentEvent.getGuild(), GM_ROLENAME);
        Member gm = currentEvent.getGuild().getMembersWithRoles(gmRole).get(0);

        MessageEmbed content = embed.build();
        gm.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(content)).queue();
        embed.clear();

    }


    @Override
    public void displayError(Exception exc) {
        currentEvent.getChannel().sendMessage(exc.getLocalizedMessage()).queue();

    }

    @Override
    public void displayTickNew() {
        Role partRole = findRole(currentEvent.getGuild(), PARTICIPANT_ROLENAME);
        this.displayMsg(partRole.getAsMention() + "The GM started a new fight! Type !tick join [INI] to join the battle!");

    }


    @Override
    public void displayTickContent(int currentTick, String turns, ArrayList<ArrayList<String>> nextMoves) {
        TickTemplate tickTemplate = new TickTemplate();
        EmbedBuilder embed = tickTemplate.buildTickEmbed(this.currentEvent, currentTick, turns, nextMoves);
        currentEvent.getChannel().sendMessage(embed.build()).queue();
        embed.clear();

    }

    @Override
    public void displayTickStart(ArrayList<String> players) {
        TickTemplate tickTemplate = new TickTemplate();
        EmbedBuilder embed = tickTemplate.buildStartingEmbed(this.currentEvent, players);
        currentEvent.getChannel().sendMessage(embed.build()).queue();
        embed.clear();

    }

    @Override
    public void displayTickPosition(int pos) {
        this.displayMsg("Your turn is at Tick: " + Integer.toString(pos));

    }

    //	@Override
//	public void displayOnlineImg(String string) {
//		URL url;
//		try {
//			url = new URL(string);
//			currentEvent.getChannel().sendFile(url.openStream()).queue();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
    @Override
    public void displayLocalImg(String string) {
        File file;
        try {
            file = new File(string);
            currentEvent.getChannel().sendFile(file).queue();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private Role findRole(Guild guild, String name) {

        List<Role> roles = guild.getRolesByName(name, true);

        return roles.get(0);

    }

    @Override
    public void displayBingo(String bingoResult) {

        Role gmRole = findRole(currentEvent.getGuild(), GM_ROLENAME);
        Member gm = currentEvent.getGuild().getMembersWithRoles(gmRole).get(0);

        MiscTemplate bingoTemplate = new MiscTemplate();
        EmbedBuilder embed = bingoTemplate.buildBingoEmbed(this.currentEvent, gm, bingoResult);

        currentEvent.getMessage().reply(embed.build()).queue();
        embed.clear();

    }
}

