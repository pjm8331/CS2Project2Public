package server;

import client.gui.WAMException;

import java.util.ArrayList;


/**
 * Handles the game logic
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
        int score = 0;
        int index = -1;
        ArrayList<WAMPlayer> tied = new ArrayList<WAMPlayer>();
        ArrayList<Integer> tiedNum = new ArrayList<Integer>();

        for (int i = 0; i < this.wamPlayers.size(); i++){
            if (score < this.wamPlayers.get(i).getScore()){
                score = this.wamPlayers.get(i).getScore();
                index = i;
                for (int j = 0; j < tied.size(); j++){
                    tied.remove(j);
                    tiedNum.remove(j);
                }
            }
            else if (score == this.wamPlayers.get(i).getScore() && (this.wamPlayers.get(i).getScore() != 0 )){
                tied.add(this.wamPlayers.get(index));
                tied.add(this.wamPlayers.get(i));
                tiedNum.add(index);
                tiedNum.add(i);
            }

        }

        if (tied.size() != 0){
            for (WAMPlayer wamPlayer : this.wamPlayers) {
                for (WAMPlayer wamPlayer1 : tied) {
                    if (wamPlayer.equals(wamPlayer1  )) {
                        wamPlayer.gameTied();
                    } else {
                        wamPlayer.gameLost();
                    }
                }
            }

        }
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

