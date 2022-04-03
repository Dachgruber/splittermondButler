package main;

import javax.security.auth.login.LoginException;

import controller.DiceController;

public class BotStartup {
	public static void main(String[] args) throws LoginException {
		new DiceController();
	}
}
