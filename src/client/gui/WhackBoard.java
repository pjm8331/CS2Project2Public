package client.gui;

import java.util.List;

/**
 * @author John Baxley(jnb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WhackBoard {
    public static int ROWS;
    public static int COLS;
    public static int TIME;
    public static int PLAYERS;
    private List<Observer<WhackBoard>> observerlist;
    private Spot[][] board;

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

    public void addObserver(Observer<WhackBoard> observer){
        this.observerlist.add(observer);
    }
}
