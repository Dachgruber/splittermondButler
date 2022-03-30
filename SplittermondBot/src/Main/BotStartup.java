package Main;

import javax.security.auth.login.LoginException;

import Controller.Commands;
import Controller.Controller;
import Controller.DiceController;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class BotStartup {
    public static void main(String[] args) throws LoginException {
        Controller controller = new DiceController();

    }
}
