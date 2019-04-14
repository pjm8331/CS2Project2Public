package client.gui;

import common.WAMProtocol;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static common.WAMProtocol.*;

public class WAMClient {
    private static boolean Debug = false;

    private Socket clientSocket;

    private Scanner in;

    private PrintStream out;

    private WAMBoard wamBoard;

    private boolean go;


    public WAMClient(String host, int port, WAMBoard wamBoard) throws WAMException{
        try {
            this.clientSocket = new Socket(host, port);
            this.in = new Scanner(clientSocket.getInputStream());
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.go = true;

            String request = this.in.next();
            String args = this.in.nextLine();

            if (!request.equals(WELCOME)){
                throw new WAMException();
            }
            WAMClient.print("Connected to server" + this.clientSocket);

            this.wamBoard = wamBoard;

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void print(Object logmsg){
        if (WAMClient.Debug){
            System.out.println(logmsg);
        }

    }
    private synchronized boolean getGo(){
        return this.go;
    }

    private synchronized void stop(){
        this.go = false;
    }

    public void startListener(){
        new Thread(() -> this.run()).start();
    }

    public void close(){
        try{
            this.clientSocket.close();
        }
        catch (IOException e){

        }
        this.wamBoard.close();
    }

    private void run(){
        while(this.getGo()){
            try{
                String input = this.in.next();
                String args = this.in.nextLine().trim();

                System.out.println(input + " " + args);
                switch(input){
                    case MOLE_UP:
                        String[] fields = args.trim().split( " " );
                        int spot = Integer.parseInt(fields[0]);
                        this.wamBoard.MoleUp(spot);
                        break;
                    case MOLE_DOWN:
                        String[] fields2 = args.trim().split( " " );
                        int spot2 = Integer.parseInt(fields2[0]);
                        this.wamBoard.MoleDown(spot2);
                        break;
                    case WHACK:
                    case GAME_LOST:
                        System.out.print(GAME_LOST);
                        System.out.print("Lost Game!");
                        this.wamBoard.gameLost();
                        this.stop();
                        break;
                    case GAME_TIED:
                        System.out.print(GAME_TIED);
                        System.out.print("Tied Game!");
                        this.wamBoard.gameTied();
                        this.stop();
                        break;
                    case GAME_WON:
                        System.out.print(GAME_WON);
                        System.out.print("Won Game!");
                        this.wamBoard.gameWon();
                        this.stop();
                        break;
                    case SCORE:
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
