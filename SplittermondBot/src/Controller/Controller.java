package Controller;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Controller {

	/**
	 * executes a command
	 * @param args
	 */
	void executeCommand(String args);
		 
}
