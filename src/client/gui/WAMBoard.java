package client.gui;

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
    private Spot[][] board;

    public WAMBoard(int rows, int cols, int players, int time){
        this.rows = rows;
        this.cols = cols;
        this.players = players;
        this.time = time;

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
    public boolean isDown(int col, int row){
        return(col >= 0 && col < this.cols) && (row >=0 && row < this.rows) &&
                (this.board[col][row] == Spot.DOWN);
    }

    public void addObserver(Observer<WAMBoard> observer){
        this.observerlist.add(observer);
    }
}
