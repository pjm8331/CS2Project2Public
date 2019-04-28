package server;

import client.gui.WAMException;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Handles the game logic
 *@author John Baxley(jmb3471)
 *@author Peter Mastropaolo(pjm8331)
 */
public class WAM {
    private static int rows;
    private static int cols;

    public enum Mole {
        UP, DOWN
    }

    private Mole[][] board;

    public WAM(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Mole[cols][rows];
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                board[col][row] = Mole.DOWN;
            }
        }
    }

    public Mole getSpot(int row, int col){
        return this.board[row][col];
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols(){
        return this.cols;
    }

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
     *
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

    public void update(ArrayList<WAMPlayer> players) {
        server.Mole mole = new server.Mole(this.getRows(), this.getCols(), this, players );
        server.Mole mole1 = new server.Mole(this.getRows(), this.getCols(), this, players );
        mole.run();
        mole1.run();

    }
}