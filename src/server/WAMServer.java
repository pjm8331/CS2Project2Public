package server;

import client.gui.WAMException;
import common.WAMProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WAMServer implements WAMProtocol, Runnable {

    private ServerSocket server;
    private int players;
    private int rows;
    private int cols;
    private int time;

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

    @Override
    public void run(){
        try{
           for(int i = 0; i<this.players; i++){
               System.out.print("Wainting for player " + (i+1) + "...");
               Socket playerSocket = server.accept();
               WAMPlayer player = new WAMPlayer();
           }
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
