package client.gui;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Random;

/**
 * The model for the WAM game
 * @author John Baxley(jmb3471)
 * @author Peter Mastropaolo(pjm8331)
 * */
public class WAMBoard {
    //Number of rows and columns for this game
    private int rows;
    private int cols;

    //Time when game started
    private long time;

    //Number of the players
    private int players;

    //List of observers on this board
    private List<Observer<WAMBoard>> observerlist;

    //Representation of the board through up and down spots
    private Spot[][] WAMboard;

    //Status of the board
    private Status status;

    //Duration of the game
    private long gameTime;

    //Default constructor for WAMboard
    public WAMBoard(int rows, int cols, int players, long time){
        this.rows = rows;
        this.cols = cols;
        this.players = players;
        this.observerlist = new LinkedList<>();
        this.WAMboard = new Spot[rows][cols];
        this.gameTime = time;

        this.time = System.currentTimeMillis();

        for(int row = 0; row < rows; row++){
            for(int col = 0; col <cols; col++){
                WAMboard[row][col] = Spot.DOWN;
            }
        }
        this.status = Status.RUNNING;

    }

    //Whether or not a mole is up or down in a spot
    public enum Spot{
        UP, DOWN
    }

    //Status of the game
    public enum Status{
        WIN, LOSE, TIE, ERROR, RUNNING
    }

    /**
     * Get how much time is left in the game
     * @return the time left in the game
     */
    public long getTimeLeft(){
        long currentMs = System.currentTimeMillis() - this.time;
        long currentTime = currentMs/1000;
        return this.gameTime - currentTime;
    }

    /**
     * Check whether or not a spot is up or down
     * @param col column of spot
     * @param row row of spot
     * @return the spot which is either up or down
     */
     public Spot isDown(int col, int row){
        return this.WAMboard[row][col];
    }

    /**
     * adds
     * @param observer
     */
    public void addObserver(Observer<WAMBoard> observer){
        this.observerlist.add(observer);
    }

    public void gameLost(){
        this.status = Status.LOSE;
        alertObservers();
    }
    public void gameTied(){
        this.status = Status.TIE;
        alertObservers();
    }
    public void gameWon(){
        this.status = Status.WIN;
        alertObservers();
    }

    public void error(){
        this.status = Status.ERROR;
        alertObservers();
    }

    public void alertObservers(){
        for(Observer<WAMBoard> observs: this.observerlist){
            observs.update(this);
        }
    }

    public void close(){
        alertObservers();
    }

    public Status getStatus(){
        return this.status;
    }

    public void MoleUp(int num){
        int[] spot = this.getSpot(num);
        this.WAMboard[spot[0]][spot[1]] = Spot.UP;
        alertObservers();

    }

    public void MoleDown(int num){
        int[] spot = this.getSpot(num);
        this.WAMboard[spot[0]][spot[1]] = Spot.DOWN;
        alertObservers();
    }


    private int[] getSpot(int num){
        int row = num/this.rows;
        int col = num%this.cols;
        int[] spot = new int[2];
        spot[0] = row;
        spot[1] = col;
        return spot;
    }

}
