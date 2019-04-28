package server;

import java.util.ArrayList;
import java.util.Random;

public class Mole extends Thread {
    int row;

    int col;

    WAM.Mole state;

    WAM wam;

    ArrayList<WAMPlayer> wamplayers;

    public Mole(int row, int col, WAM wam, ArrayList<WAMPlayer> wamplayers){
        this.col = col;
        this.row = row;
        this.state = WAM.Mole.DOWN;
        this.wam = wam;
        this.wamplayers = wamplayers;

    }

    public void run(){
        Random random = new Random();
        int rand = random.nextInt(this.wam.getRows() * this.wam.getCols());

        int[] spot = this.wam.getSpotNum(rand);

        this.state = WAM.Mole.UP;
        this.wam.setUp(spot[0],spot[1]);

        for (WAMPlayer player : this.wamplayers) {
            player.moleUp(rand);
        }

        int rand2 = random.nextInt(1000) + 2000;

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){e.printStackTrace();}

        this.wam.setDown(spot[0], spot[1]);
        this.state = WAM.Mole.DOWN;
        for (WAMPlayer player : this.wamplayers) {
            player.moleDown(rand);
        }
    }
}
