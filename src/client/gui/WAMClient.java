package client.gui;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static common.WAMProtocol.*;

/**
 * The controller in MVC - handles communication between the server and the model
 *@author John Baxley(jmb3471)
 *@author Peter Mastropaolo(pjm8331)
 */
public class WAMClient {
    //Whether or not debug messages are seen
    private static boolean Debug = false;

    //Socket for communicating with the server
    private Socket clientSocket;

    //Input from the server
    private Scanner in;

    //Output to the server
    private PrintStream out;

    //The model which keeps the game
    private WAMBoard wamBoard;

    //Whether or not the game is running
    private boolean go;


    /**
     * Constructor for WAMClient
     * @param host the host server to connect to
     * @param port the port of the server to connect to
     * @throws WAMException
     */
    public WAMClient(String host, int port) throws WAMException{
        try {
            this.clientSocket = new Socket(host, port);
            this.in = new Scanner(clientSocket.getInputStream());
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.go = true;

            String request = this.in.next();
            String args = this.in.nextLine();

            String[] fields = args.trim().split( " " );

            int rows = Integer.parseInt(fields[0]);
            int cols = Integer.parseInt(fields[1]);
            int players = Integer.parseInt(fields[2]);
            int time = Integer.parseInt(fields[3]);

            this.wamBoard = new WAMBoard(rows, cols, players, time);

            if (!request.equals(WELCOME)){
                throw new WAMException("No welcome message");
            }
            WAMClient.print("Connected to server" + this.clientSocket);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return wamboard to be given to the gui representation
     */
    public WAMBoard getWamBoard(){
        return this.wamBoard;
    }

    public void sendWhack(int row, int col){
        this.out.println(WHACK + row + " " + col);
    }

    /**
     * Only prints if debug is true
     * @param msg message to be printed out
     */
    private static void print(Object msg){
        if (WAMClient.Debug){
            System.out.println(msg);
        }

    }

    /**
     * gets the go variable
     * @return boolean go
     */
    private synchronized boolean getGo(){
        return this.go;
    }

    /**
     * Sets go to false, stopping the game
     */
    private synchronized void stop(){
        this.go = false;
    }

    /**
     * Called when starting to receive messages from server
     */
    public void startListener(){
        new Thread(() -> this.run()).start();
    }

    /**
     * Closes the connection to the server and the model
     */
    public void close(){
        try{
            this.clientSocket.close();
        }
        catch (IOException e){

        }
        this.wamBoard.close();
    }

    /**
     * Handles all server messages and updates the board accordingly
     */
    private void run(){
        while(this.getGo()){
            try{
                String input = this.in.next();
                String args = this.in.nextLine().trim();
                String[] fields = args.trim().split( " " );

                switch(input){
                    case MOLE_UP:
                        int spot = Integer.parseInt(fields[0]);
                        this.wamBoard.MoleUp(spot);
                        break;
                    case MOLE_DOWN:
                        int spot2 = Integer.parseInt(fields[0]);
                        this.wamBoard.MoleDown(spot2);
                        break;
                    case WHACK:
                    case GAME_LOST:

                        this.wamBoard.gameLost();
                        this.stop();
                        break;
                    case GAME_TIED:
                        this.wamBoard.gameTied();
                        this.stop();
                        break;
                    case GAME_WON:
                        this.wamBoard.gameWon();
                        this.stop();
                        break;
                    case SCORE:
                        this.wamBoard.changeScore(Integer.parseInt(fields[1]));
                    case ERROR:
                        System.out.print("Error!");
                        this.wamBoard.error();
                        break;
                    default:
                        System.err.println("Unknown Command: " + input);
                        this.stop();
                        break;
                }
            }

            catch( NoSuchElementException e){
                System.out.print("Lost connection: " + e);
                this.stop();
            }
            catch( Exception f){
                System.out.print(f.getMessage());
                this.stop();
            }
        }
    }
}
