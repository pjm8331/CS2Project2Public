package client.gui;

import java.util.Observer;

/**
 * @author John Baxley(jnb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WhackBoard {
    public static int ROWS;
    public static int COLS;
    public static int TIME;
    public static int PLAYERS;
    private list<Observer<WhackBoard>> observerlist;

    public enum Status{
        WIN, LOSE, TIE, ERROR, NOT_DONE
    }
    public int getTimeLeft(){
        return 0;
    }

    public void addObserver(Observer<WhackBoard> observer){
        this.
    }
}
