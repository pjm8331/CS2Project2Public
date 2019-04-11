package client.gui;

/**
 * @author John Baxley(jnb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WhackBoard {
    public static int ROWS;
    public static int COLS;
    public static int TIME;
    public static int PLAYERS;

    public enum Status{
        WIN, LOSE, TIE, ERROR, NOT_DONE
    }
    public int getTimeLeft(){
        return 0;
    }
}
