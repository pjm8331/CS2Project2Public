package server;

import client.gui.WAMException;
import com.sun.webkit.ThemeClient;

import java.util.ArrayList;
import java.util.Random;

public class WAMGame implements Runnable{
    private ArrayList<WAMPlayer> wamPlayers;

    private WAM wamGame;

    private long time;

    private int col;

    private int row;

    private long startTime;

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
    @Override
    public void run(){
        boolean go = true;
        long currentTime;
        while (go){
            currentTime = System.currentTimeMillis();

            this.wamGame.update(wamPlayers);
            this.wamGame.update(wamPlayers);

            if (this.time <= currentTime){
                go = false;
            }
        }

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
