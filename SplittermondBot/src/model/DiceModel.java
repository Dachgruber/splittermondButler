package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.tickbar.Enemy;
import model.tickbar.TickBar;
import model.tickbar.Controller;
import net.dv8tion.jda.api.entities.User;
import view.View;

public class DiceModel implements Model {
    TickBar tb; // TODO: access rights
    View view;

    final String BULLSHIT_PATH = "txt/bullshit.txt";

    public DiceModel(View view) {
        this.view = view;
    }

    //------------------------------Dice Section-------------------------------------
    @Override
    public Roll rollDice(int[] args) {
        return new Roll(args[0], args[1]).RollTheDice();
    }

    @Override
    public Roll rollDice(int[] args, String[] calcArgs) {
        return new Roll(args[0], args[1]).RollTheDice().calcResult(calcArgs[0], calcArgs[1]);
    }

    //------------------------------BullshitBingo Section----------------------------------
    @Override
    public String rollBingo() {
        String[] items;
        String result = "bullshitbingo failed";
        try {
            items = loadFileAsString(BULLSHIT_PATH).split(";"); //The Items are separated in the txt by an ;

            System.out.println("Bingo rolled, length, result");
            System.out.println(items.length - 1);

            Roll bbroll = new Roll(1, items.length - 1); // -1 as the last item in the array it always empty
            bbroll.RollTheDice();

            System.out.println(bbroll.getResult());

            result = items[bbroll.getResult() - 1]; // as the dice results start with 1, not 0
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    //------------------------------TickBar Section----------------------------------
    @Override
    public void newTickBar() {
        tb = new Controller();
        this.view.displayTickNew();
    }

    @Override
    public void joinEnemy(String name, String ini) {
        // Roll new dices and get their result
        int result = new Roll(1, 6).RollTheDice().getResult();
        int pos = Integer.parseInt(ini) - result;
        
        
        // put the enemy on the board
        this.tb.addEnemy(pos , name, "This is a default desctiption"); //TODO update description

        String replyStr = name + " joined at pos "
                + pos
                + " while rolling a "
                + result;

        this.view.reply(replyStr);
    }

    @Override
    public void joinPlayer(String string) {
        User player = this.view.getCurrentEvent().getAuthor();

        // Roll new dices and get their result
        int result = new Roll(1, 6).RollTheDice().getResult();

        // calc their position on the board
        int pos = Integer.parseInt(string) - result;
        this.tb.addPlayer(pos,player);

        String replyStr = "You joined at pos "
                + pos
                + " while rolling a "
                + result;

        this.view.reply(replyStr);
    }

    @Override
    public void tick() {
        this.tb.tick();
        this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getCurrentTurn(), this.tb.getNextMoves(5));
    }

    @Override
    public void startBattle() {
        if (tb != null) {
            tb.start();
            this.view.displayTickStart(this.tb.getStartingPlayersAsMentions());
            this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getCurrentTurn(), this.tb.getNextMoves(5));
        } else {
            this.view.displayMsg("you funcking twat - there is no tickbar to start");
        }
    }

    @Override
    public void movePlayer(String string) {
        User player = this.view.getCurrentEvent().getAuthor();

        int pos = Integer.parseInt(string);

        this.tb.movePlayer(player, pos);

        String replyStr = "You moved to pos "
                + pos;

        this.view.reply(replyStr);
    }

    @Override
    public void listTickBar() {
        this.view.displayTickContent(this.tb.getCurrentTick(), this.tb.getCurrentTurn(), this.tb.getNextMoves(10));
    }

    @Override
    public void showPosOfPlayer() {
        int pos = tb.getPosOfPlayer(this.view.getCurrentEvent().getAuthor());
        this.view.displayTickPosition(pos);
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
}
