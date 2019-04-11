package client.gui;

import javafx.application.Application;
import javafx.stage.Stage;


public class WAMGUI extends Application implements Observer<WAMBoard> {

    private WAMBoard whackBoard;

    private WAMClient whackClient;


    @Override
    public void init() throws RuntimeException{}


    public void start(Stage stage){}

    @Override
    public void stop(){}

    private void refresh(){}

    @Override
    public void update(WAMBoard whackBoard){}

    public static void main(String[] args){
        if (args.length != 5){}
    }

}
