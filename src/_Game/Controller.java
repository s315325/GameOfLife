package _Game;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Game Of Life program created for HIOA final project
 * The Controller class is the fx for fxml, all the properties in fxml are declared here.
 * The class is also implementing Initializable interface.
 *
 * @version 1.0
 * @since   2017-01-14
 * @author  Sivert Allergodt Borgeteien & Gajaen Chandrasegaram
 * Studentnr : S315325 & S315285
 * */


public class Controller implements Initializable {

    @FXML private Canvas CanvasId;
    @FXML private ColorPicker colorPicker;
    @FXML private ChoiceBox patternChoiceBox, musicChoiceBox;
    @FXML private Slider sliderFPS, cellSlider;
    @FXML private Button musicStartButton, drawPattern;
    @FXML private ToolBar Toolbar;
    @FXML private TextField textBox;
    @FXML private ToggleButton toggleButton;
    @FXML private Text tekst;

          private CanvasFrame canvasFrame;
          private Sounds sounds;
          private Timeline timeline;
          private ReadGameBoard readGameBoard;


    /**
     * Constructs and initializes the canvasFrame.
     *
     * @param location  unused.
     * @param resources unused.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        canvasFrame = new CanvasFrame((int) CanvasId.getHeight(), (int) CanvasId.getWidth(), CanvasId.getGraphicsContext2D());
        sounds = new Sounds();
        tekst.setText("");
        patternChoiceBox();
        musicChoiceBox();
        key();

    }


    @FXML
    private void key() {
        CanvasId.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            public void handle(javafx.scene.input.KeyEvent event) {
                canvasFrame.key(event);
            }
        });
    }

    @FXML
    private void musicChoiceBox(){

        sounds.startClick();
        musicChoiceBox.getItems().add("Take On Me");
        musicChoiceBox.getItems().add("Through The Fire and Flames");
        musicChoiceBox.getItems().add("Shooting Stars");
        musicChoiceBox.getItems().add("Radioactive");
        musicChoiceBox.getItems().add("Knights of Cydonia");
        musicChoiceBox.getItems().add("Shape Of You");
        musicChoiceBox.setValue("Shape Of You");
        musicStartButton.setOnAction(event -> getMusicChoice(musicChoiceBox));

    }

    @FXML
    private void getMusicChoice(ChoiceBox<String>musicChoiceBox) {
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



    @FXML
    private void clickedMusicPauseButton(){
        sounds.click();
        sounds.Pause();
    }

    @FXML
    private void clickedMusicStopButton(){
        sounds.click();
        sounds.Stop();
    }


    @FXML
    private void clickedToolbar(){
        final double xOffset = 640;
        final double yOffset = 20;

        Toolbar.setOnMouseDragged(event ->
        {
            Window window = ((Node)event.getTarget()).getScene().getWindow();
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void clickedStartButton() {
       int TIME = 1000/canvasFrame.getFPS();


        timeline = new Timeline(new KeyFrame(Duration.millis(TIME), e -> {

            canvasFrame.getDynamicBoard().nextGeneration();
            canvasFrame.clearCanvas();

//            try{Thread.sleep(100);} catch (Exception a){}
            canvasFrame.pressedCanvas();
            timeline.playFromStart();


        }));
    timeline.play();

    }

    @FXML
    private void clickedClearButton() {
        canvasFrame.clearCanvas();
        canvasFrame.clearArray();
        timeline.stop();
    }


    @FXML
    private void patternChoiceBox(){
        patternChoiceBox.getItems().add("Random");
        patternChoiceBox.getItems().add("None");
        patternChoiceBox.setValue("Random");
        drawPattern.setOnAction(event -> getPatternChoice(patternChoiceBox));
    }

    @FXML
    private void getPatternChoice(ChoiceBox<String>patternChoiceBox) {
        switch (patternChoiceBox.getValue()) {
            case "Random":
                System.out.println(patternChoiceBox.getValue());
                canvasFrame.RandomButtonAction();
                break;
            case "None":
                System.out.println("None");
                clickedClearButton();
                break;
        }
    }

    @FXML
    private void clickedToggleButton(ActionEvent event){
        if (toggleButton.isSelected()) {
            System.out.println("Toggled");
        }
        else {
            System.out.println("UnToggled");
        }

    }

    @FXML
    private void clickedExitButton(){
        sounds.click();
        Platform.exit();
    }

    @FXML
    private void colorPickerClicked() {
        sounds.click();
        canvasFrame.colorPicker(colorPicker);
    }

    @FXML
    private void clickedStopButton() {
        timeline.stop();
        sounds.click();
    }

    @FXML
    private void FPSClicked() {
        int a = (int) sliderFPS.getValue();
        canvasFrame.setFPS(a);
        canvasFrame.drawCanvas();
    }

    @FXML
    private void CellSizeClicked() {
        double a = cellSlider.getValue();
        canvasFrame.cellSize(a);
        canvasFrame.drawCanvas();
    }



    @FXML
    private void CanvasPressed(MouseEvent a)  {
        canvasFrame.CanvasPressed(a);
    }


    /**
     * This method is reading the RLE file
     *
     * @return Nothing.
     * @throws IOException On input error.
     * @see IOException
     */

    @FXML
    private void openFile()  {

        try {

                readGameBoard = new ReadGameBoard(canvasFrame.getHEIGHT(), canvasFrame.getWIDTH());
                canvasFrame.drawPattern(readGameBoard.pattern);
                tekst.setText(" Created on: " + readGameBoard.getCreationDetails(readGameBoard.file) + " File name: " + readGameBoard.file.getName() +
                        "  Created by: " + readGameBoard.file.getParent() +
                        "  Pattern name: " + readGameBoard.getPatterName());
                if (readGameBoard.getCell() > 0) {
                    canvasFrame.cellSize(readGameBoard.getCell());
                }

                canvasFrame.clearCanvas();

                canvasFrame.pressedCanvas();

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File Not Choosen");
            alert.setHeaderText(null);
            alert.setContentText("Have you forgot to select the file?");

            alert.showAndWait();
        }
        }

    /**
     * This method is closing the window.
     */
    @FXML
    private void closeWindow() {Platform.exit();}

    @FXML
    private void saveBoard() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/_Game/SaveBoard.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 800, 500);
            SaveGame controller = fxmlLoader.<SaveGame>getController();
            controller.setBoard(canvasFrame.getBoard());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }

    }

    @FXML
    private void patternLoad() throws IOException {

        URL url = new URL(textBox.getText());
        InputStream in = url.openStream();
        Scanner scan = new Scanner(in);

        while (scan.hasNextLine())
        {
            String str = scan.nextLine();
            System.out.println(str);
        }

        scan.close();

    }



}

