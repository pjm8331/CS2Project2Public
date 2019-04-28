package server;

import client.gui.WAMException;
import common.WAMProtocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class WAMPlayer implements WAMProtocol, Closeable{

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

    public synchronized void makeWhack() throws WAMException{
       if (scanner.hasNextLine()) {
           String response = scanner.nextLine();


        if (response.startsWith(WHACK)){
            String[] tokens = response.split(" ");
            if(tokens.length == 3) {
                if (Integer.parseInt(tokens[2]) == this.playerNum) {

                    int[] spot = this.wamGame.getSpotNum(Integer.parseInt(tokens[1]));
                    if (this.wamGame.getSpot((spot[0]), (spot[1])) == WAM.Mole.UP) {
                        this.score += 1;
                        printStream.println(MOLE_DOWN + " " + Integer.parseInt(tokens[1]) * Integer.parseInt(tokens[2]));
                        printStream.println(SCORE + " " + score);
                    } else {
                        this.score -= 1;
                        printStream.println(SCORE + " " + score);
                    }
                }
            }
            else {
                throw new WAMException("Invalid response");
            }

        }
        else {
            throw new WAMException("Invalid response");
        }
       }
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
