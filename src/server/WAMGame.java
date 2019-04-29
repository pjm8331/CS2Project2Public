package server;

import client.gui.WAMException;

import java.util.ArrayList;


/**
 * Handles the game logic
 * NOTE: Double click mouse when playing game
 * Doesn't appear to count whack unless double clicked
 * @author John Baxley(jmb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WAMGame implements Runnable{
    private ArrayList<WAMPlayer> wamPlayers;

    private WAM wamGame; //The array the game will run on

    private long time; //How long the game will run

    private int col; //The number of columns

    private int row; //The number of rows

    private long startTime; //The start time variable used to calculate when the game ends

    /**
     * Constructor for the game
     * @param row The rows in the game
     * @param col The columns in the game
     * @param players The players in the game
     * @param time How long the game will run
     * @param wamGame The array the game will run on
     */
    public WAMGame(int row, int col, ArrayList<WAMPlayer> players, int time, WAM wamGame){

        this.row = row;

        this.col = col;

        this.time = time * 1000 ;

        this.wamPlayers = players;

        this.startTime = System.currentTimeMillis();

        this.time = this.startTime + this.time;

        this.wamGame = wamGame;
    }

    //go will correspond to time

    /**
     * runs the game
     */
    @Override
    public void run(){
        boolean go = true;
        long currentTime;
        while (go){
            currentTime = System.currentTimeMillis();
            this.wamGame.update(wamPlayers);

            if (this.time <= currentTime){
                go = false;
            }
        }
        this.wamGame.changeStatus(WAM.Status.NOT);
        int score = this.wamPlayers.get(0).getScore();
        int index = 0;
        ArrayList<WAMPlayer> tied = new ArrayList<WAMPlayer>();
        ArrayList<Integer> tiedNum = new ArrayList<Integer>();
        ArrayList<WAMPlayer> loser = new ArrayList<WAMPlayer>();
        WAMPlayer winner = this.wamPlayers.get(0);

        //Checks for clear winner
        for (int i = 1; i < this.wamPlayers.size(); i++){
            if (score < this.wamPlayers.get(i).getScore()){
                loser.add(winner);
                score = this.wamPlayers.get(i).getScore();
                index = i;
                winner = this.wamPlayers.get(i);
                for (int j = 0; j < tied.size(); j++){
                    tied.remove(j);
                    tiedNum.remove(j);
                }
            }
            //Checks for ties
            else if (score == this.wamPlayers.get(i).getScore() && (this.wamPlayers.get(i).getScore() != 0 )){
                if (score != 0) {
                    tied.add(this.wamPlayers.get(index));
                    tied.add(this.wamPlayers.get(i));

                    tiedNum.add(index);
                    tiedNum.add(i);
                }
                else {
                    index = i;
            }
            }
            else {
                loser.add(wamPlayers.get(i));
            }

        }


        if (tied.size() == 0){
            winner.gameWon();
            for (WAMPlayer wamPlayer : loser){
                wamPlayer.gameLost();
            }
        }
        else {
            for (WAMPlayer wamPlayer : tied){
                wamPlayer.gameTied();
            }
            for (WAMPlayer wamPlayer : loser){
                wamPlayer.gameLost();
            }
        }
        //Sends messages for tied
        if (tied.size() != 0){
            for (WAMPlayer wamPlayer : this.wamPlayers) {
                for (WAMPlayer wamPlayer1 : tied) {
                    if (wamPlayer.equals(wamPlayer1)) {
                        wamPlayer.gameTied();
                    }
                    else {
                        wamPlayer.gameLost();
                    }
                }
            }

        }

        //Sends messages for winners and losers
        for (int i = 0; i < this.wamPlayers.size(); i++){
            if (i == index){
                this.wamPlayers.get(i).gameWon();
            }
            else {
                this.wamPlayers.get(i).gameLost();
            }
        }
        for (WAMPlayer player : this.wamPlayers){
            player.close();
        }
    }
}

