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

    public WAMGame(int row, int col, int players, int time){

        this.row = row;

        this.col = col;

        this.time = time * 1000 ;

        this.wamPlayers = new ArrayList<WAMPlayer>();

        this.startTime = System.currentTimeMillis();

        this.time = this.startTime + this.time;

        for (int i = 0; i < players; i++){
            WAMPlayer player = new WAMPlayer();
            this.wamPlayers.add(player);
        }

        this.wamGame = new WAM();
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
