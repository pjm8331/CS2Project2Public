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
        Random random = new Random();
        while (go){
            currentTime = System.currentTimeMillis();

            this.wamGame.update(wamPlayers);

            try{
                for (WAMPlayer player : this.wamPlayers){
                    player.makeWhack();
                }
            }
            catch (WAMException e){
                e.printStackTrace();
            }
            if (this.time <= currentTime){
                go = false;
            }
        }

        WAMPlayer winner = this.wamPlayers.get(0);
        int index = -1;

        for (WAMPlayer player : this.wamPlayers){
            if (winner.getScore() < player.getScore()){
                winner = player;
            }
            index++;
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
