package server;

import client.gui.WAMException;

import java.util.ArrayList;
import java.util.Random;


/**
 * Handles the game logic
 *@author John Baxley(jmb3471)
 *@author Peter Mastropaolo(pjm8331)
 */
public class WAM {
    private static int rows;
    private static int cols;


    /**
     * Whether or not the game is running
     */
    public enum Status {
        RUNNING, NOT
    }

    private Status status;

    public enum Mole {
        UP, DOWN
    }

    private Mole[][] board; //2d array of the whack-a-mole board

    /**
     * Constructor for the game
     * @param rows how many rows the board will have
     * @param cols how many columns the board will have
     */
    public WAM(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.status = Status.RUNNING;
        this.board = new Mole[cols][rows];
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                board[col][row] = Mole.DOWN;
            }
        }
    }

    /**
     * method for changing the status
     * @param status either RUNNING or NOT
     */
    public void changeStatus(Status status){
        this.status = status;
    }

    /**
     * @return the status of the game
     */
    public Status getStatus(){
        return this.status;
    }

    /**
     * gets the spot on the board
     * @param row what row the spot is
     * @param col what column the spot is
     * @return the enum of the spot
     */
    public Mole getSpot(int row, int col){
        return this.board[row][col];
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

    /**
     * gets the row and column based on the number given
     * @param num number used to determine the spot
     * @return the row and column of the board spot
     */
    public int[] getSpotNum(int num){
        int row = num/this.cols;
        int col = num%this.cols;
        int[] spot = new int[2];
        spot[0] = row;
        spot[1] = col;
        return spot;
    }

    /**
     * Gets the board
     * @return the board
     */
    public Mole[][] getBoard() {
        return this.board;
    }

    /**
     * Sets a spot on the 2d array to Mole enum down
     * @param row of the spot
     * @param col of the spot
     */
    public void setDown(int row, int col){

        this.board[row][col] = Mole.DOWN;
    }

    /**
     * Sets a spot on the 2d array to Mole enum up
     * @param row of the spot
     * @param col of the spot
     */
    public void setUp(int row, int col) {
        this.board[row][col] = Mole.UP;
    }

    /**
     * creates and runs mole threads
     * @param players list of players in the game
     */
    public void update(ArrayList<WAMPlayer> players) {
        Random random = new Random();
        int rand = random.nextInt(rows * cols);

        server.Mole mole = new server.Mole(this.getRows(), this.getCols(), this, players );
        server.Mole mole1 = new server.Mole(this.getRows(), this.getCols(), this, players );

        mole.start();
        mole1.start();

        mole.run();
        mole1.run();



    }
}