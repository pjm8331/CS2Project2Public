package server;

import client.gui.WAMException;
import common.WAMProtocol;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles the creation of the players, the game, and all sockets for communication
 *@author John Baxley(jmb3471)
 *@author Peter Mastropaolo(pjm8331)
 */
public class WAMServer implements WAMProtocol, Runnable {

    private ServerSocket server; //Server socket for the game
    private int players; //How many players are in the game
    private int rows; //The number of rows for the board
    private int cols; //The number of columns for the board
    private int time; //The amount of time the game will run for

    /**
     * Constructor for the server
     * @param port what port to create the server at
     * @param rows how many rows the game will have
     * @param cols how many columns the game will have
     * @param players how many players the game will have
     * @param time how long the game will run
     * @throws WAMException custom exception for the game
     */
    public WAMServer(int port, int rows, int cols, int players, int time) throws WAMException{
        try{
            server = new ServerSocket(port);
        }
        catch(IOException e){
            throw new WAMException(e);
        }
        this.rows = rows;
        this.cols = cols;
        this.players = players;
        this.time = time;
    }

    /**
     * main function for the creation of the game
     * @param args takes in the args when creating the game
     * @throws WAMException
     */
    public static void main(String[] args) throws WAMException{
        if(args.length != 5){
            System.out.println("Usage: java WAMServer <port> <rows> <columns> <player #> <duration>");
        }

        int port = Integer.parseInt(args[0]);
        int rows = Integer.parseInt(args[1]);
        int cols = Integer.parseInt(args[2]);
        int players = Integer.parseInt(args[3]);
        int time = Integer.parseInt(args[4]);
        WAMServer server = new WAMServer(port, rows, cols, players, time);
        server.run();
    }

    /**
     * Runs the server and creates players, the game, and the sockets for the players
     */
    @Override
    public void run(){
        try{
            ArrayList<WAMPlayer> Player = new ArrayList<>();
            WAM wam = new WAM(this.rows, this.cols);
            WAMGame game = new WAMGame(this.rows, this.cols, Player, this.time, wam);
           for(int i = 0; i<this.players; i++){
               System.out.print("Waiting for player " + (i+1) + "...");
               Socket playerSocket = server.accept();

               System.out.println("Player " + (i+1) +" connected!");
               WAMPlayer player = new WAMPlayer(playerSocket, wam);
               player.connect();
               Player.add(player);
           }
            System.out.println("WORKING");
           new Thread(game).run();
        }
        catch(IOException e){
            System.err.println("IOException!");
            e.printStackTrace();
        }
        catch(WAMException e){
            System.err.println("Failed player creation!");
            e.printStackTrace();
        }

    }
}
