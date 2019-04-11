package client.gui;

import java.util.List;

/**
 * @author John Baxley(jnb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WAMBoard {
    public static int ROWS;
    public static int COLS;
    public static int TIME;
    public static int PLAYERS;
    private List<Observer<WAMBoard>> observerlist;
    private Spot[][] board;

    public WAMBoard(int ROWS, int COLS, int PLAYERS, int TIME){
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.PLAYERS = PLAYERS;
        this.TIME = TIME;

    }
    public enum Spot{
        UP, DOWN
    }
    public enum Status{
        WIN, LOSE, TIE, ERROR, NOT_DONE
    }
    public int getTimeLeft(){
        return 0;
    }
    public boolean isDown(int col, int row){
        return(col >= 0 && col < COLS) && (row >=0 && row < ROWS) &&
                (this.board[col][row] == Spot.DOWN);
    }

    public void addObserver(Observer<WAMBoard> observer){
        this.observerlist.add(observer);
    }
}
