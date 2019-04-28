package server;

import client.gui.WAMException;
import common.WAMProtocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class WAMPlayer implements WAMProtocol, Closeable {

    private Socket socket;

    private Scanner scanner;

    private PrintStream printStream;

    private int score;

    private WAM wamGame;

    public WAMPlayer(Socket socket, WAM wamGame) throws WAMException{
        this.socket = socket;
        this.score = 0;
        this.wamGame = wamGame;
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
                this.wamGame.getCols() + " " + 1 + " " + 100);


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

    public void makeWhack() throws WAMException{
        String response = scanner.nextLine();

        System.out.println(response);
        if (response.startsWith(WHACK)){
            String[] tokens = response.split(" ");
            if(tokens.length == 3) {

                if (this.wamGame.getSpot(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])) == WAM.Mole.UP){
                    this.score += 1;
                    printStream.println(MOLE_DOWN + " " + Integer.parseInt(tokens[1]) * Integer.parseInt(tokens[2]));
                    printStream.println(SCORE + " " + score);
                }
                else{
                    this.score -= 1;
                    printStream.println(SCORE + " " + score);
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
