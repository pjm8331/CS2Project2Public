package server;

import client.gui.WAMException;

import java.util.ArrayList;

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
            if (this.time == currentTime){
                go = false;
            }
        }
        for (WAMPlayer player : this.wamPlayers){
            player.close();
        }
    }
}
