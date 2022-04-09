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
	private final Controller cntrl;
	private GuildMessageReceivedEvent currentEvent;
	private final String TOKEN_PATH = "/bottoken.txt";

	private final String ADMIN_ROLENAME = "Admin";
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
	 * initiates the bot, handles login and displays parameters (currently
	 * playing...)
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
		StringBuilder returnString = new StringBuilder();

		try {
			FileReader reader = new FileReader(System.getProperty("user.dir") + this.TOKEN_PATH);
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;

			while ((line = bufferedReader.readLine()) != null)
				returnString.append(line);
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnString.toString();
	}

	/**
	 * required implementation of the listener
	 */
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		this.currentEvent = event;
		String args = event.getMessage().getContentRaw();
		// System.out.println("Listener Fired"); //DEBUG
		// System.out.println(this.currentEvent.getMessage().getContentRaw());

		// dont react if the bot send the message!
		if (!event.getAuthor().isBot())
			// only react if the correct prefix is used
			if (event.getMessage().getContentRaw().startsWith("!"))
				this.cntrl.executeCommand(args);
	}

	@Override
	public GuildMessageReceivedEvent getCurrentEvent() {
		return this.currentEvent;
	}

	@Override
	public void setCurrentEvent(GuildMessageReceivedEvent currentEvent) {
		this.currentEvent = currentEvent;
	}

	@Override
	public void displayMsg(String msg) {
		this.currentEvent.getChannel().sendMessage(msg).queue();
	}

	@Override
	public void askGM(String content) {
		Role gmRole = this.findRole(this.currentEvent.getGuild(), this.GM_ROLENAME);
		Member gm = this.currentEvent.getGuild().getMembersWithRoles(gmRole).get(0);
		gm.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(content)).queue();
	}

	@Override
	public void reply(String msg) {
		this.currentEvent.getMessage().reply(msg).queue();
	}

	@Override
	public void displayRoll(Roll rollEvent) {
		RollTemplate rollTemplate = new RollTemplate();
		EmbedBuilder embed = rollTemplate.buildRollEmbed(this.currentEvent, rollEvent);

		this.currentEvent.getMessage().reply(embed.build()).queue();
		embed.clear();
	}

	@Override
	public void displayGMRoll(Roll rollEvent) {
		RollTemplate rollTemplate = new RollTemplate();
		EmbedBuilder embed = rollTemplate.buildPrivateRollEmbed(this.currentEvent, rollEvent);

		Role gmRole = this.findRole(this.currentEvent.getGuild(), this.GM_ROLENAME);
		Member gm = this.currentEvent.getGuild().getMembersWithRoles(gmRole).get(0);

		MessageEmbed content = embed.build();
		gm.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(content)).queue();
		embed.clear();
	}

	@Override
	public void displayError(Exception exc) {
		this.currentEvent.getChannel().sendMessage(exc.getLocalizedMessage()).queue();
	}

	@Override
	public void displayTickNew() {
		Role partRole = this.findRole(this.currentEvent.getGuild(), this.PARTICIPANT_ROLENAME);
		this.displayMsg(
				partRole.getAsMention() + "The GM started a new fight! Type !tick join [INI] to join the battle!");
	}

	@Override
	public void displayTickContent(int currentTick, String turns, ArrayList<ArrayList<String>> nextMoves) {
		TickTemplate tickTemplate = new TickTemplate();
		EmbedBuilder embed = tickTemplate.buildTickEmbed(this.currentEvent, currentTick, turns, nextMoves);
		this.currentEvent.getChannel().sendMessage(embed.build()).queue();
		embed.clear();
	}

	@Override
	public void displayTickStart(ArrayList<String> players) {
		TickTemplate tickTemplate = new TickTemplate();
		EmbedBuilder embed = tickTemplate.buildStartingEmbed(this.currentEvent, players);
		this.currentEvent.getChannel().sendMessage(embed.build()).queue();
		embed.clear();
	}

	@Override
	public void displayTickPosition(int pos) {
		this.displayMsg("Your turn is at Tick: " + pos);
	}

	@Override
	public void displayLocalImg(String string) {
		File file;

		try {
			file = new File(string);
			this.currentEvent.getChannel().sendFile(file).queue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Role findRole(Guild guild, String name) {
		List<Role> roles = guild.getRolesByName(name, true);
		return roles.get(0);
	}

	@Override
	public void displayBingo(String[] bingoResult) {
		Role gmRole = this.findRole(this.currentEvent.getGuild(), this.GM_ROLENAME);
		Member gm = this.currentEvent.getGuild().getMembersWithRoles(gmRole).get(0);

		MiscTemplate bingoTemplate = new MiscTemplate();
		EmbedBuilder embed = bingoTemplate.buildBingoEmbed(this.currentEvent, gm, bingoResult);

		this.currentEvent.getMessage().reply(embed.build()).queue();
		embed.clear();
	}

	/**
	 * d
	 */
	@Override
	public void displayBingoList(ArrayList<String[]> listTable) {
		
			String outputString = "The bullshitbingo currently contains: \n" + "ID, Name, Description, Rarity, isActivated?\n";
			for (String[] entryArray: listTable) {
				for (String entryElem : entryArray) {
					outputString += entryElem + ", ";
				}
				outputString += "\n";
			}
			
			this.sendPrivateMessage(this.currentEvent.getAuthor(), outputString);
		}
		
		
	
	@Override
	public boolean isAuthorGM() {
		try {
			Role gmRole = this.findRole(this.currentEvent.getGuild(), this.GM_ROLENAME);
			return this.currentEvent.getMember().getRoles().contains(gmRole);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void sendPrivateMessage(User user, String content) {
        // openPrivateChannel provides a RestAction<PrivateChannel>
        // which means it supplies you with the resulting channel
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });
    }

	@Override
	public boolean isAuthorAdmin() {
		try {
			Role AdminRole = this.findRole(this.currentEvent.getGuild(), this.ADMIN_ROLENAME);
			return this.currentEvent.getMember().getRoles().contains(AdminRole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
