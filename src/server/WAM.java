package server;

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

    /**
     * Gets the board
     *
     * @return the board
     */
    public Mole[][] getBoard() {
        return this.board;
    }

    public void update() {
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                double rand = Math.random();
                if (rand % 2 == 0) {
                    board[col][row] = Mole.UP;
                } else {
                    board[col][row] = Mole.DOWN;
                }
            }
        }
    }
}