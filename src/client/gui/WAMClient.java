package client.gui;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

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
        //todo
    }
}
