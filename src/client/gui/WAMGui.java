package client.gui;

import javafx.application.Application;
import javafx.application.Platform;
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

    private Label label;

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
            long time = Long.parseLong(args.get(4));

            this.whackBoard = new WAMBoard(row, col, players, time);

            this.whackBoard.addObserver(this);

            this.whackClient = new WAMClient(host,port, this.whackBoard );

            this.buttons = new Button[row][col];

            this.label = new Label(time + " time left");

        }
        catch (WAMException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.err.println(e);
            throw new RuntimeException();
        }
    }

    public void start(Stage stage) throws Exception{
        GridPane gridPane = new GridPane();

        Image down = new Image(getClass().getResourceAsStream("Moledown.jpg"));

        for (int i = 0; i < this.col; i++){
            for (int j = 0; j < this.row; j++){
                Button button = new Button();
                button.setPrefSize(80, 80);
                button.setGraphic(new ImageView(down));

                this.buttons[j][i] = button;

                gridPane.add(this.buttons[j][i], i, j);
            }
        }

        BorderPane borderPane = new BorderPane();

        HBox hBox = new HBox(this.label);

        borderPane.setBottom(hBox);
        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane);

        stage.setTitle("Whack a mole");
        stage.setScene(scene);
        stage.show();

        this.whackClient.startListener();

    }

    @Override
    public void stop(){
        this.whackClient.close();
    }

    private void refresh(){
        Image up = new Image(getClass().getResourceAsStream("Moleup.png"));
        Image down = new Image(getClass().getResourceAsStream("Moledown.jpg"));

        this.whackBoard.popUp();

        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.row; j++) {
                WAMBoard.Spot spot = this.whackBoard.isDown(i, j);

                if (spot == WAMBoard.Spot.UP){
                    this.buttons[i][j].setGraphic(new ImageView(up));
                }
                else if (spot == WAMBoard.Spot.DOWN){
                    this.buttons[i][j].setGraphic(new ImageView(down));
                }
            }
        }

        WAMBoard.Status status = this.whackBoard.getStatus();

        switch (status) {
            case TIE:
                this.label.setText("You tied");
                break;
            case WIN:
                this.label.setText("You win");
                break;
            case LOSE:
                this.label.setText("You lose");
                break;
            case ERROR:
                this.label.setText(status.toString());
                break;

            default:
                this.label.setText(this.whackBoard.getTimeLeft() + " time left");
                //do points here later
        }

    }

    @Override
    public void update(WAMBoard whackBoard){
        if (Platform.isFxApplicationThread()){
            this.refresh();
        }
        else{
            Platform.runLater(() -> this.refresh());
        }
    }

    public static void main(String[] args){
        if (args.length != 5){
            System.out.println("Usage: java WAMGui game-port# #rows #columns #players game-duration-seconds.");
            System.exit(-1);
        }
        Application.launch(args);
    }

}
