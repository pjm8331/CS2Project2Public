package server;

import client.gui.WAMException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Mole threads for controlling Mole.UP and Mole.DOWN
 * @author John Baxley(jmb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class Mole extends Thread {
    int row; //Number of rows in the game

    int col; //Number of columns in the gmae

    WAM.Mole state; //State of the mole

    WAM wam; //The game array

    ArrayList<WAMPlayer> wamplayers; //List of the players in the game

    /**
     * Constructor for the thread
     * @param row the rows
     * @param col the columns
     * @param wam the array
     * @param wamplayers the players
     */
    public Mole(int row, int col, WAM wam, ArrayList<WAMPlayer> wamplayers){
        this.col = col;
        this.row = row;
        this.state = WAM.Mole.DOWN;
        this.wam = wam;
        this.wamplayers = wamplayers;

    }

    /**
     * Runs the thread for controlling up and down
     */
    public void run(){
        Random random = new Random();
        int rand = random.nextInt(this.wam.getRows() * this.wam.getCols());

        int[] spot = this.wam.getSpotNum(rand);

        this.state = WAM.Mole.UP;
        this.wam.setUp(spot[0],spot[1]);

        for (WAMPlayer player : this.wamplayers) {
            player.moleUp(rand);
        }

        for (WAMPlayer player : this.wamplayers){
            try {
                player.makeWhack();
            }
            catch (WAMException e){e.printStackTrace();}
        }

        int rand2 = random.nextInt(1000) + 1000;

        try {
            Thread.sleep(rand2);
        }
        catch (InterruptedException e){e.printStackTrace();}

        this.wam.setDown(spot[0], spot[1]);
        this.state = WAM.Mole.DOWN;
        for (WAMPlayer player : this.wamplayers) {
            player.moleDown(rand);
        }
    }
}
