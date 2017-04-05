package _Game;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class GUI {

    public Controller controller;
    public Stage stage;


    public void RandomButton() {
        //Lager en ny random array for hver gang start er trykket.
        Controller Con = new Controller();


        for (int i = 0; i < Con.HEIGHT; i++) {
            for (int j = 0; j < Con.WIDTH; j++) {
                Con.board[i][j] = (int) (Math.random() * 2);
            }
        }

        System.out.print("2");

        Con.drawCells();
        Con.drawLines();

        System.out.print("3");
    }


    public void ClearButton() {
        controller.gen = 0;
        controller.timeline.stop();
        controller.initialize();
    }

    public void StartButton() {
        controller.timeline.play();
    }

    public void ColorPicker() {
        Color color = controller.colorPicker.getValue();
        if (color != null) {
            controller.cellColor = controller.colorPicker.getValue();
        }
    }

    public void StopButton() {
        controller.timeline.stop();
    }

    public void fpsSlider() {
        controller.FPS = (int) controller.sliderFPS.getValue();
    }

    public void cellSlider() {
        controller.cellSize = (int) controller.cellSlider.getValue();
    }
}