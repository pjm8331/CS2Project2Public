package client.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
//links for pics

//https://brightyellow.com.au/home/cb024artboard-1/
//http://www.notionofform.com/notion-of-form-and-colours

public class WAMGui extends Application implements Observer<WAMBoard> {

    private WAMBoard whackBoard;

    private WAMClient whackClient;

    private Button[][] buttons;

    private TextField textField;

    private int row;

    private int col;


    @Override
    public void init() throws RuntimeException{
        try{
            List<String> args = getParameters().getRaw();

            String host = "glados.cs.rit.edu";

            int port = Integer.parseInt(args.get(0));

            int row = Integer.parseInt(args.get(1));
            this.row = row;

            int col = Integer.parseInt(args.get(2));
            this.col = col;

            int players = Integer.parseInt(args.get(3));

            if (players < 1){
                throw new WAMException();
            }
            int time = Integer.parseInt(args.get(4));

            this.whackBoard = new WAMBoard(row, col, players, time);

            this.whackClient = new WAMClient(host,port, this.whackBoard );

            this.buttons = new Button[row][col];

            this.textField = new TextField(time + " time left");

        }
        catch (WAMException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.err.println(e);
            throw new RuntimeException();
        }
    }

    public void start(Stage stage){
        GridPane gridPane = new GridPane();

        Image down = new Image(getClass().getResourceAsStream("Moledown.png"));
        Image up = new Image(getClass().getResourceAsStream("Moleup.png"));

        for (int i = 0; i < this.col; i++){
            for (int j = 0; j < this.row; j++){
                Button button = new Button();
                button.setPrefSize(80, 80);
                button.setGraphic(new ImageView(down));

                button.setOnAction((actionEvent) -> {
                });

                this.buttons[j][i] = button;

                gridPane.add(this.buttons[j][i], i, j);
            }
        }

        BorderPane borderPane = new BorderPane();

        HBox hBox = new HBox(this.textField);

        borderPane.setBottom(hBox);
        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane);

        stage.setTitle("Whack a mole");
        stage.setScene(scene);

        this.whackClient.startListener();

    }

    @Override
    public void stop(){
        this.whackClient.close();
    }

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
