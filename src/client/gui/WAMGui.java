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
/**
 * A JavaFX GUI for the networked Connect Four game.
 * @author John Baxley(jmb3471)
 * @author Peter Mastropaolo(pjm8331)
 * */
public class WAMGui extends Application implements Observer<WAMBoard> {

    //board to be represented
    private WAMBoard whackBoard;

    //connection to network
    private WAMClient whackClient;

    //2d array of buttons that represent holes
    private Button[][] buttons;

    //Label to represent the time and display info
    private Label label;

    //The amount of rows in WAM game
    private int row;

    //The amount of columns in WAM game
    private int col;

    /**
     * Init function to start WAMGui
     * equivalent to constructor
     * @throws RuntimeException if any errors
     */
    @Override
    public void init() throws RuntimeException{
        try{
            List<String> args = getParameters().getRaw();

            String host = args.get(0);

            int port = Integer.parseInt(args.get(1));


            this.whackClient = new WAMClient(host ,port);

            this.whackBoard = this.whackClient.getWamBoard();

            this.whackBoard.addObserver(this);

            this.row = this.whackBoard.getRows();

            this.col = this.whackBoard.getCols();

            this.buttons = new Button[row][col];

            this.label = new Label("SCORE: 0" );

        }
        catch (WAMException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.err.println(e);
            throw new RuntimeException();
        }
    }

    /**
     * Start function sets up scene for javaFx to represent the WAM game
     * @param stage
     * @throws Exception
     */
    public void start(Stage stage) throws Exception{
        GridPane gridPane = new GridPane();

        Image down = new Image(getClass().getResourceAsStream("Moledown.jpg"));

        for (int i = 0; i < this.col; i++){
            for (int j = 0; j < this.row; j++){
                Button button = new Button();
                button.setPrefSize(80, 80);

                final int col = i;
                final int row = j;

                button.setOnAction((ActionEvent) -> {
                    this.whackClient.sendWhack(row, col);
                });
                ImageView view = new ImageView(down);
                view.setFitWidth(80);
                view.setFitHeight(80);
                button.setGraphic(view);

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

    /**
     * Stop function
     * Closes client when the application is closed
     */
    @Override
    public void stop(){
        this.whackClient.close();
    }

    /**
     * Refreshes visuals of the application whenever called by update
     * keeps track of mole up, time, and score visually
     */
    private void refresh(){

        Image up = new Image(getClass().getResourceAsStream("Moleup.png"));
        Image down = new Image(getClass().getResourceAsStream("Moledown.jpg"));

        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.row; j++) {
                WAMBoard.Spot spot = this.whackBoard.isDown(i, j);

                ImageView view1 = new ImageView(up);
                view1.setFitWidth(80);
                view1.setFitHeight(80);

                ImageView view2 = new ImageView(down);
                view2.setFitWidth(80);
                view2.setFitHeight(80);
                if (spot == WAMBoard.Spot.UP){
                    this.buttons[j][i].setGraphic(view1);
                }
                else{
                    this.buttons[j][i].setGraphic(view2);
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
                this.label.setText("Score: " + this.whackBoard.getScore());
                //do points here later
        }

    }

    /**
     * Called by WAMBoard whenever an event occurs that visuals need to be updated
     * @param whackBoard
     */
    @Override
    public void update(WAMBoard whackBoard){
        if (Platform.isFxApplicationThread()){
            this.refresh();
        }
        else{
            Platform.runLater(() -> this.refresh());
        }
    }

    /**
     * Main function called to start the game
     * Takes gameport, rows, columns, players, and game duration as input in the args in that order
     * @param args
     */
    public static void main(String[] args){
        if (args.length != 2){
            System.out.println("Usage: java WAMGui host game-port# .");
            System.exit(-1);
        }
        Application.launch(args);
    }

}
