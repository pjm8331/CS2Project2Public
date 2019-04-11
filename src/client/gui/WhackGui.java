package client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.util.List;


public class WhackGui extends Application implements Observer<WhackBoard> {

    private WhackBoard whackBoard;

    private WhackClient whackClient;

    private int time;

    private int players;

    @Override
    public void init() throws RuntimeException{}


    public void start(Stage stage){}

    @Override
    public void stop(){}

    private void refresh(){}

    @Override
    public void update(WhackBoard whackBoard){}

    public static void main(String[])

}
