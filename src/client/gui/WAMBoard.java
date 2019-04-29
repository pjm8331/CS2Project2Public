package client.gui;

import java.util.LinkedList;
import java.util.List;

/**
 * The model for the WAM game
 * @author John Baxley(jmb3471)
 * @author Peter Mastropaolo(pjm8331)
 * */
public class WAMBoard {
    //Number of rows and columns for this game
    private int rows;
    private int cols;

    private int score;

    //List of observers on this board
    private List<Observer<WAMBoard>> observerlist;

    //Representation of the board through up and down spots
    private Spot[][] WAMboard;

    //Status of the board
    private Status status;


    /**
     * Constructor for WAMBoard
     * @param rows number of rows the board has
     * @param cols number of columns the board has
     * @param players number of players the board has
     */
    public WAMBoard(int rows, int cols, int players) throws WAMException{
        if (players < 0){
            throw new WAMException("Missing players");
        }
        this.rows = rows;
        this.cols = cols;
        this.score = 0;
        this.observerlist = new LinkedList<>();
        this.WAMboard = new Spot[rows][cols];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col <cols; col++){
                WAMboard[row][col] = Spot.DOWN;
            }
        }
        this.status = Status.RUNNING;

    }

    /**
     * Whether or not a mole is up or down in a spot
     */
    public enum Spot{
        UP, DOWN
    }

    /**
     * Status of the game
     */
    public enum Status{
        WIN, LOSE, TIE, ERROR, RUNNING
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

    /**
     * Sets the Status of the board to lose and alerts observers
     */
    public void gameLost(){
        this.status = Status.LOSE;
        alertObservers();
    }

    /**
     * Sets the Status of the board to tied and alerts observers
     */
    public void gameTied(){
        this.status = Status.TIE;
        alertObservers();
    }

    /**
     * Sets the Status of the board to win and alerts observers
     */
    public void gameWon(){
        this.status = Status.WIN;
        alertObservers();
    }

    /**
     * Sets the Status of the board to error and alerts observers
     */
    public void error(){
        this.status = Status.ERROR;
        alertObservers();
    }

    /**
     * alerts the observer to update
     */
    public void alertObservers(){
        for(Observer<WAMBoard> observs: this.observerlist){
            observs.update(this);
        }
    }
    //close the board
    public void close(){
        alertObservers();
    }

    /**
     * get status
     * @return the current status of the board
     */
    public Status getStatus(){
        return this.status;
    }

    /**
     *Sets the mole in a certain spot to up
     * @param num number representing the spot
     */
    public void MoleUp(int num){
        int[] spot = this.getSpot(num);
        this.WAMboard[spot[0]][spot[1]] = Spot.UP;
        alertObservers();

    }

    public void changeScore(int num){
        this.score = num;
    }

    public int getScore(){
        return this.score;
    }

    /**
     * Sets the mole in a certain spot to down
     * @param num number representing the spot
     */
    public void MoleDown(int num){
        int[] spot = this.getSpot(num);
        this.WAMboard[spot[0]][spot[1]] = Spot.DOWN;
        alertObservers();
    }

    /**
     * returns a spot on the board
     * @param num input number
     * @return the spot on the board
     */
    private int[] getSpot(int num){
        int row = num/this.cols;
        int col = num%this.cols;
        int[] spot = new int[2];
        spot[0] = row;
        spot[1] = col;
        return spot;
    }

    /**
     * @return the number of rows on the board
     */
    public int getRows(){
        return this.rows;
    }
    /**
     * @return the number of columns on the board
     */
    public int getCols(){
        return this.cols;
    }



}
