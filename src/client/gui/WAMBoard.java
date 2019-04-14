package client.gui;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Random;

/**
 * @author John Baxley(jnb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WAMBoard {
    private int rows;
    private int cols;
    private long time;
    private int players;
    private List<Observer<WAMBoard>> observerlist;
    private Spot[][] WAMboard;
    private Status status;
    private boolean myTurn;
    private long gameTime;

    public WAMBoard(int rows, int cols, int players, long time){
        this.rows = rows;
        this.cols = cols;
        this.players = players;
        this.observerlist = new LinkedList<>();
        this.WAMboard = new Spot[cols][rows];
        this.myTurn = false;
        this.gameTime = time;

        this.time = System.currentTimeMillis();

        for(int row = 0; row < rows; row++){
            for(int col = 0; col <cols; col++){
                WAMboard[col][row] = Spot.DOWN;
            }
        }
        this.status = Status.RUNNING;

    }

    public enum Spot{
        UP, DOWN
    }

    public void makeMove(){
        this.myTurn = true;
        alertObservers();
    }

    public enum Status{
        WIN, LOSE, TIE, ERROR, RUNNING, MoleUp, MoleDown
    }

    public long getTimeLeft(){
        long currentMs = System.currentTimeMillis() - this.time;
        long currentTime = currentMs/1000;
        return this.gameTime - currentTime;
    }

     public Spot isDown(int col, int row){
        return this.WAMboard[col][row];
    }

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
