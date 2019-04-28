package server;

import client.gui.WAMException;
import com.sun.webkit.ThemeClient;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

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

        for (int i = 0; i < this.wamPlayers.size(); i++){
            if (score < this.wamPlayers.get(i).getScore()){
                score = this.wamPlayers.get(i).getScore();
                index = i;
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
