package client.gui;

import java.util.LinkedList;
import java.util.List;

/**
 * @author John Baxley(jnb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WAMBoard {
    public static int rows;
    public static int cols;
    public static int time;
    public static int players;
    private List<Observer<WAMBoard>> observerlist;
    private Spot[][] WAMboard;
    private Status status;

    public WAMBoard(int rows, int cols, int players, int time){
        this.rows = rows;
        this.cols = cols;
        this.players = players;
        this.time = time;
        this.observerlist = new LinkedList<>();
        this.WAMboard = new Spot[cols][rows];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col <cols; col++){
                WAMboard[col][row] = Spot.DOWN;
            }
        }
        this.status = Status.NOT_DONE;

    }
    public enum Spot{
        UP, DOWN
    }
    public enum Status{
        WIN, LOSE, TIE, ERROR, NOT_DONE
    }
    public int getTimeLeft(){
        return time;
    }
    public Spot isDown(int col, int row){
        return this.WAMboard[col][row];
    }

    public void addObserver(Observer<WAMBoard> observer){
        this.observerlist.add(observer);
    }

    public void alertObservers(){
        for(Observer<WAMBoard> observs: this.observerlist){
            observs.update(this);
        }
    }
}
