package server;

import client.gui.WAMException;
import common.WAMProtocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Handles the players in the game
 * @author John Baxley(jmb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public class WAMPlayer implements WAMProtocol, Closeable, Runnable{

    private Socket socket; //The player socket

    private Scanner scanner; //The scanner for input

    private PrintStream printStream; //The printstream for output

    private int score; //Holds the players score

    private WAM wamGame; //The array the game operates on

    private int playerNum; //the player number for identification

    /**
     * Constructor for the player
     * @param socket the socket of the player
     * @param wamGame 2d array the game operates on
     * @param playerNum the number of the player
     * @throws WAMException
     */
    public WAMPlayer(Socket socket, WAM wamGame, int playerNum) throws WAMException{
        this.socket = socket;
        this.score = 0;
        this.wamGame = wamGame;
        this.playerNum = playerNum;
        try {
            this.scanner = new Scanner(socket.getInputStream());
            this.printStream = new PrintStream(socket.getOutputStream());
        }
        catch (IOException e){
            throw new WAMException(e);
        }
    }

    /**
     * Method used to connect to the server(sends the WELCOME message)
     */
    public void connect(){
        printStream.println(WELCOME + " " + this.wamGame.getRows() + " " +
                this.wamGame.getCols() + " " + 1 + " " + this.playerNum);
    }

    /**
     * Sends the MOLE_UP message to the server
     * @param spot what spot to put the mole up at
     */
    public void moleUp(int spot){
        printStream.println(MOLE_UP + " " + spot);
    }

    /**
     * Sends the MOLE_DOWN message to the server
     * @param spot what spot to put the mole down at
     */
    public void moleDown(int spot){
        printStream.println(MOLE_DOWN + " " + spot);
    }

    /**
     * Sends a WHACK message
     */
    public void whack(){
        printStream.println(WHACK);
    }

    /**
     * Sends a SCORE message
     */
    public void score(){
        printStream.println(SCORE + " " +  this.score);
    }

    /**
     * Sends a GAME_LOST message
     */
    public void gameLost(){
        printStream.println(GAME_LOST);
    }

    /**
     * Sends a GAME_WON message
     */
    public void gameWon(){
        printStream.println(GAME_WON);
    }

    /**
     * Sends a GAME_TIED message
     */
    public void gameTied(){
        printStream.println(GAME_TIED);
    }

    /**
     * Sends an ERROR message
     * @param message message of the error to be sent
     */
    public void error(String message){
        printStream.println(ERROR + " " + message);
    }

    /**
     * @return the scanner of the player
     */
    public Scanner getScanner(){
        return this.scanner;
    }

    public synchronized void makeWhack()throws WAMException{

    }


    /**
     * Runs the player
     */
    @Override
    public void run() {
        boolean go = true;
        while (go){
            if (this.wamGame.getStatus() == WAM.Status.NOT){
                break;
            }
            String response = scanner.nextLine();
            if (response.startsWith(WHACK)){
                String args = scanner.nextLine().trim();
                String[] fields = args.trim().split( " " );
                int[] rowcol = this.wamGame.getSpotNum(Integer.parseInt(fields[1]));
                if (this.wamGame.getSpot(rowcol[0], rowcol[1]) == WAM.Mole.UP){
                    this.score += 1;
                    this.printStream.println(SCORE + " " + this.score);

                }
                else {
                    this.score -= 1;
                    this.printStream.println(SCORE + " " + this.score);
                }
            }
        }

        //wait for whack messge
        //call some whack method on the game or all moles
    }

    /**
     * @return the score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Closes the players socket
     */
    public void close(){
        try{
            socket.close();
        }
        catch (IOException e){}
    }
}
