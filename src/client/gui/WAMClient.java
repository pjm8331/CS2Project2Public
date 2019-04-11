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
}
