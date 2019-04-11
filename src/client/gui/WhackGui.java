package client.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;


public class WhackGui extends Application implements Observer<WAMBoard> {

    private WAMBoard whackBoard;

    private WAMClient whackClient;


    @Override
    public void init() throws RuntimeException{
        try{
            List<String> args = getParameters().getRaw();

            String host = args.get(0);

            this.whackBoard = new WAMBoard();



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
            System.out.println("Usage: java WhackGui game-port# #rows #columns #players game-duration-seconds.");
            System.exit(-1);
        }
        Application.launch(args);
    }

}
