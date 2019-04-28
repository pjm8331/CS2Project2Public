package server;

import client.gui.WAMException;
import common.WAMProtocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class WAMPlayer implements WAMProtocol, Closeable, Runnable{

    private Socket socket;

    private Scanner scanner;

    private PrintStream printStream;

    private int score;

    private WAM wamGame;

    private int playerNum;

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

    public void connect(){
        printStream.println(WELCOME + " " + this.wamGame.getRows() + " " +
                this.wamGame.getCols() + " " + 1 + " " + this.playerNum);
    }

    public void moleUp(int spot){
        printStream.println(MOLE_UP + " " + spot);
    }

    public void moleDown(int spot){
        printStream.println(MOLE_DOWN + " " + spot);
    }

    public void whack(){
        printStream.println(WHACK);
    }

    public void score(){
        printStream.println(SCORE + " " +  this.score);
    }

    public void gameLost(){
        printStream.println(GAME_LOST);
    }

    public void gameWon(){
        printStream.println(GAME_WON);
    }

    public void gameTied(){
        printStream.println(GAME_TIED);
    }

    public void error(String message){
        printStream.println(ERROR + " " + message);
    }

    public Scanner getScanner(){
        return this.scanner;
    }

    public synchronized void makeWhack()throws WAMException{

    }



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

    public int getScore(){
        return this.score;
    }

    public void close(){
        try{
            socket.close();
        }
        catch (IOException e){}
    }
}
