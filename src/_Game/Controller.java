package _Game;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Game Of Life program created for HIOA final project
 * The Controller class is the fx for fxml, all the properties in fxml are assign here.
 * The class is also implementing Initializable interface.
 *
 * @version 1.0
 * @since   2017-01-14
 */


public class Controller implements Initializable {
    public Canvas CanvasId;
    @FXML
    private CanvasFrame canvasFrame;
    private Sounds sounds;
    public ColorPicker colorPicker;
    public ChoiceBox choiceBox, musicChoiceBox;
    private GUI gui;
    private Timeline timeline;


    public Slider sliderFPS, cellSlider;
    public StaticBoard sBoard;
    public DynamicBoard dynamicBoard;
    public Button musicStartButton;
    public ToolBar Toolbar;
    String line;
    TextField textBox;
    private Timeline tl;



    int user_id = 2;
    ReadGameBoard readGameBoard;
    public Text tekst;

    /**
     * Constructs and initializes the canvas and gui.
     *
     * @param location  unused.
     * @param resources unused.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvasFrame = new CanvasFrame((int) CanvasId.getHeight(), (int) CanvasId.getWidth(), CanvasId.getGraphicsContext2D());
        this.gui = new GUI(canvasFrame);
        sounds = new Sounds();
        key();
        tekst.setText("");
        ChoiceBox();
        MusicChoiceBox();
        sounds.startClick();
    }


    public void key() {
        CanvasId.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            public void handle(javafx.scene.input.KeyEvent event) {
                canvasFrame.key(event);
            }
        });
    }

    public void MusicChoiceBox(){
        musicChoiceBox.getItems().add("Take On Me");
        musicChoiceBox.getItems().add("Through The Fire and Flames");
        musicChoiceBox.getItems().add("Shooting Stars");
        musicChoiceBox.getItems().add("Radioactive");
        musicChoiceBox.getItems().add("Knights of Cydonia");
        musicChoiceBox.getItems().add("Shape Of You");
        musicChoiceBox.setValue("Shape Of You");

        musicStartButton.setOnAction(event -> getChoice(musicChoiceBox));
    }

    public void getChoice(ChoiceBox<String>musicChoiceBox) {
        switch (musicChoiceBox.getValue()) {
            case "Take On Me":
                sounds.TakeOnMe();
                break;
            case "Through The Fire and Flames":
                sounds.FireAndFlames();
                break;
            case "Shooting Stars":
                sounds.ShootingStars();
                break;
            case "Radioactive":
                sounds.Radioactive();
                break;
            case "Knights of Cydonia":
                sounds.KnightsofCydonia();
                break;
            case "Shape Of You":
                sounds.ShapeOfYou();
                break;

        }
    }





    
//test
    public void clickedMusicStartButton(){

    }
    public void clickedMusicPauseButton(){
        sounds.click();
        sounds.Pause();

    }
    public void clickedMusicStopButton(){
        sounds.click();
        sounds.Stop();

    }
    public void clickedToolbar(){
    }
    public void clickedStartButton() {
       int TIME = 1000/canvasFrame.getFPS();

       /* tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        KeyFrame moveBall = new KeyFrame(Duration.millis(TIME),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        canvasFrame.getDynamicBoard().nextGeneration();
                        canvasFrame.clearCanvas();
                        canvasFrame.pressedCanvas();
                    }
                });

        tl.getKeyFrames().add(moveBall);
        tl.play();*/

        timeline = new Timeline(new KeyFrame(Duration.millis(TIME), e -> {

            canvasFrame.clickNoise();
            canvasFrame.getDynamicBoard().nextGeneration();
            canvasFrame.clearCanvas();

//            try{Thread.sleep(100);} catch (Exception a){}
            canvasFrame.pressedCanvas();
            timeline.playFromStart();


        }));
    timeline.play();

    }

    public void clickedClearButton() {
        timeline.stop();
        sounds.click();
        gui.ClearButton();
    }

    public void clickedRandomButton() {
        sounds.click();
        gui.RandomButton();
    }

    public void ChoiceBox(){
        choiceBox.getItems().add("Random");
        choiceBox.setValue("Random");
    }

    public void clickedExitButton(){
        sounds.click();
        Platform.exit();
    }

    public void colorPickerClicked() {
        sounds.click();
        canvasFrame.colorPicker(colorPicker);
    }

    public void clickedStopButton() {
        timeline.stop();
        sounds.click();
    }

    public void FPSClicked() {
        int a = (int) sliderFPS.getValue();
        canvasFrame.setFPS(a);
        //canvasFrame.SetTimeline();
        canvasFrame.drawCanvas();
    }

    public void CellSizeClicked() {
        double a = cellSlider.getValue();
        canvasFrame.cellSize(a);
        canvasFrame.drawCanvas();
    }

    public void CanvasReleased() {
    }

    public void CanvasPressed(MouseEvent a) throws Exception {
        canvasFrame.CanvasPressed(a);
    }


    /**
     * This method is reading the RLE file
     *
     * @return Nothing.
     * @throws IOException On input error.
     * @see IOException
     */
    public void openFile() throws IOException {


        readGameBoard = new ReadGameBoard(canvasFrame.getHEIGHT(), canvasFrame.getWIDTH());

        readGameBoard.readFile(line);

        canvasFrame.drawPattern(readGameBoard.pattern);

        tekst.setText(" Created on: " + readGameBoard.getCreationDetails(readGameBoard.file) + " File name: " + readGameBoard.file.getName() +
                "  Created by: " + readGameBoard.file.getParent() +
                "  Pattern name: " + readGameBoard.getPatterName());

    }

    /**
     * This method is closing the window.
     */
    public void closeWindow() {
        Platform.exit();
    }

    public void saveBoard() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/_Game/SaveBoard.fxml"));

            //  fxmlLoader.setController(saveBoardController.class);
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);
            SaveGame controller = fxmlLoader.<SaveGame>getController();
            controller.setUser(user_id);
            controller.setBoard(canvasFrame.getBoard());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }

    }

    public void patternLoad() throws IOException {

        URL url = new URL(textBox.getText());
        InputStream in = url.openStream();
        Scanner scan = new Scanner(in);

        while (scan.hasNext())
        {
            String str = scan.nextLine();
//            readGameBoard.readFile(str);
            System.out.println(str);
        }
        scan.close();

        /*
        String in;


        System.out.println(textBox.getText());

        URL url = new URL(textBox.getText());
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));


        while ((in = br.readLine()) != null) {

            System.out.println(in);
            //readGameBoard.readFile(in);


        }

        br.close();
*/

        //   readGameBoard.readFile(in);


    }
    

}

