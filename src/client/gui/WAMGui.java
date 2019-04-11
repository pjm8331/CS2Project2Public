package client.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;


public class WAMGui extends Application implements Observer<WAMBoard> {

    private WAMBoard whackBoard;

    private WAMClient whackClient;

    private Button[][] buttons;


    @Override
    public void init() throws RuntimeException{
        try{
            List<String> args = getParameters().getRaw();

            String host = "glados.cs.rit.edu";

            int port = Integer.parseInt(args.get(0));

            int row = Integer.parseInt(args.get(1));

            int col = Integer.parseInt(args.get(2));

            int players = Integer.parseInt(args.get(3));

            int time = Integer.parseInt(args.get(4));

            this.whackBoard = new WAMBoard(row, col, players, time);

            this.whackClient = new WAMClient(host,port, this.whackBoard );

            this.buttons = new Button[row][col];

        }
        catch (WAMException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.err.println(e);
            throw new RuntimeException();
        }
    }

    public void start(Stage stage){}

    @Override
    public void stop(){}

    private void refresh(){}

    @Override
    public void update(WAMBoard whackBoard){}

    public static void main(String[] args){
        if (args.length != 5){
            System.out.println("Usage: java WAMGui game-port# #rows #columns #players game-duration-seconds.");
            System.exit(-1);
        }
        Application.launch(args);
    }

}
