package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.bingo.Bingo;
import model.bingo.BingoTable;
import model.bingo.Rarity;
import model.tickbar.*;
import model.tickbar.TickBar;
import net.dv8tion.jda.api.entities.User;
import view.View;

public class DiceModel implements Model {
	TickBar tb; // TODO: access rights
	View view;
	Bingo bingo;

	final String BULLSHIT_PATH = "txt/bullshit.txt";

	public DiceModel(View view) {
		this.view = view;
		this.bingo = new BingoTable();
	}

	// ------------------------------Dice
	// Section-------------------------------------
	@Override
	public Roll rollDice(int[] args) {
		return new Roll(args[0], args[1]).RollTheDice();
	}

	@Override
	public Roll rollDice(int[] args, String[] calcArgs) {
		return new Roll(args[0], args[1]).RollTheDice().calcResult(calcArgs[0], calcArgs[1]);
	}

	// ------------------------------BullshitBingo
	// Section----------------------------------
	@Override
	public String[] rollBingo() {
		return this.bingo.catchBingo();
	}
	
	@Override
	public void loadFileFromTxt() {
		 try {
			this.bingo.loadTableFromTxt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	// ------------------------------TickBar
	// Section----------------------------------
	@Override
	public void newTickBar() {
		this.tb = new Controller();
		this.view.displayTickNew();
	}

	@Override
	public void joinEnemy(String name, String ini) {
		// Roll new dices and get their result
		int result = new Roll(1, 6).RollTheDice().getResult();
		int pos = Integer.parseInt(ini) - result;

		// put the enemy on the board
		this.tb.addEnemy(pos, name, "This is a default desctiption"); // TODO update description

		String replyStr = name + " joined at pos " + pos + " while rolling a " + result;

		this.view.reply(replyStr);
	}

	@Override
	public void joinPlayer(String string) {
		User player = this.view.getCurrentEvent().getAuthor();

		// Roll new dices and get their result
		int result = new Roll(1, 6).RollTheDice().getResult();

		// calc their position on the board
		int pos = Integer.parseInt(string) - result;
		this.tb.addPlayer(pos, player);

		String replyStr = "You joined at pos " + pos + " while rolling a " + result;

		this.view.reply(replyStr);
	}

	@Override
	public void tick() {
		this.tb.tick();
		this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getCurrentTurn(), this.tb.getNextMoves(5));
	}

	@Override
	public void startBattle() {
		if (this.tb != null) {
			this.tb.start();
			this.view.displayTickStart(this.tb.getStartingPlayersAsMentions());
			this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getCurrentTurn(), this.tb.getNextMoves(5));
		} else
			this.view.displayMsg("you funcking twat - there is no tickbar to start");
	}

	@Override
	public void movePlayer(String string) {
		User player = this.view.getCurrentEvent().getAuthor();

		int pos = Integer.parseInt(string);

		this.tb.movePlayer(player, pos);

		String replyStr = "You moved to pos " + pos;

		this.view.reply(replyStr);
	}

	@Override
	public void listTickBar() {
		this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getCurrentTurn(), this.tb.getNextMoves(10));
	}

	@Override
	public void showPosOfPlayer() {
		int pos = this.tb.getPosOfPlayer(this.view.getCurrentEvent().getAuthor());
		this.view.displayTickPosition(pos);
	}

	// ------------------------------Role Section----------------------------------
	@Override
	public void giveRole() {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeRole() {
		// TODO Auto-generated method stub
	}

	/**
	 * Loads a file and glues every line onto a long String
	 *
	 * @param filepath location if file
	 * @return content of file in String
	 * @throws IOException exception
	 */
	private String loadFileAsString(String filepath) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line).append("\n");
				line = br.readLine();
			}

			return sb.toString();
		}
	}

	@Override
	public void loadBingo() {
		try {
			this.bingo.loadTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveBingo() {
		this.bingo.saveTable();
		
	}

	@Override
	public void resetBingo() {
		this.bingo.resetTable();
		
	}

	@Override
	public void listBingo() {
		this.view.displayBingoList( this.bingo.listTable());
		
		
	}

	@Override
	public void addBingoItem(String name, String desc, String rarity) {
		this.bingo.addItem(name, desc, Rarity.valueOf(rarity.toUpperCase()));
		
	}

	@Override
	public void removeBingoItem(String id) {
		this.bingo.removeItem(Integer.parseInt(id));
		
	}

	@Override
	public void importBingo() {
		try {
			this.bingo.loadTableFromTxt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveFileToTXT() {
		
		
	}

	
}
