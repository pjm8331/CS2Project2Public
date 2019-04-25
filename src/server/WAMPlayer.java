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

    public WAMPlayer(Socket socket) throws WAMException{
        this.socket = socket;
        try {
            this.scanner = new Scanner(socket.getInputStream());
            this.printStream = new PrintStream(socket.getOutputStream());
        }
        catch (IOException e){
            throw new WAMException(e);
        }
    }

    public void connect(){
        printStream.println(CONNECT);
    }
    public void close(){}
}
