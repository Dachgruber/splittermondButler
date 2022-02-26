package Model;

import View.View;
import net.dv8tion.jda.api.entities.User;

public class DiceModel implements Model {

	TickBar tb;
	View view;
	
	public DiceModel(View view) {
		this.view = view;
	}
//------------------------------Dice Section-------------------------------------
	@Override
	public Roll rollDice(Integer[] args) {
		return new Roll(args[0],args[1]);

	}
//------------------------------TickBar Section----------------------------------
	@Override
	public void newTickBar() {
		tb = new TickBar();
		this.view.displayTickNew();
	}

	@Override
	public void joinPlayer(String string) {
		User player = this.view.getCurrentEvent().getAuthor();
		Roll r = new Roll(1, 6);
		int result = r.RollTheDice()[0];
		int pos = Integer.parseInt(string) - result ;
		this.tb.joinPlayer(player, pos);
		String replyStr = "You joined at pos "
				+ Integer.toString(pos) 
				+ " while rolling a "
				+ Integer.toString(result);
		this.view.reply(replyStr);
	
	}

	@Override
	public void tick() {
		this.tb.tick();
		this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getTurn(), this.tb.getNextMoves(5));
		
	}
	
	@Override
	public void startBattle() {
		tb.start();
		this.view.displayTickStart(this.tb.getPlayers(), this.tb.getPlayerPos());
		this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getTurn(), this.tb.getNextMoves(5));
	}
	
	@Override
	public void movePlayer(String string) {
		User player = this.view.getCurrentEvent().getAuthor();
		int pos = Integer.parseInt(string);
		this.tb.movePlayer(player, pos);
		String replyStr = "You moved to pos "
				+ Integer.toString(pos); 
		this.view.reply(replyStr);
	}
//------------------------------Role Section----------------------------------	
	@Override
	public void giveRole() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRole() {
		// TODO Auto-generated method stub

	}

	

}
