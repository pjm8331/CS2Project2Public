package client.gui;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static common.WAMProtocol.*;

public class WAMClient {
    private Socket clientSocket;

    private Scanner in;

    private PrintStream out;

    private WAMBoard wamBoard;

    private boolean go;

    //finish not done
    //todo
    public WAMClient(String host, int port, WAMBoard wamBoard) throws WAMException{
        try {
            this.clientSocket = new Socket(host, port);
            this.wamBoard = wamBoard;
            this.in = new Scanner(clientSocket.getInputStream());
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.go = true;
        }
        catch (IOException e){}
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

                switch(input){
                    case WELCOME:
                    case MOLE_UP:
                    case MOLE_DOWN:
                    case WHACK:
                    case GAME_LOST:
                    case GAME_TIED:
                    case GAME_WON:
                    case SCORE:
                    case ERROR:
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
