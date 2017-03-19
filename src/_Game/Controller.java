package _Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Controller   {
    private Stage stage;
    public Canvas CanvasId;
    public GraphicsContext gc;
    public Button startButton, stopButton, circleButton, randomButton, clearButton;
    public int  canvasBorder, distanceCells, cellSize, FPS;
    public int[][] board, cleanBoard;


    private final int HEIGHT = 1000;

    private final int WIDTH = 1000;


    public int[][] circleBoard = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};



    public Controller()
    {
        //Variabler for spillbrettet
        cellSize = 5;
        canvasBorder = 0;
        distanceCells = 0;
    }

    public void cleanBoard() {

        gc.clearRect(0,0, CanvasId.getWidth(), CanvasId.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(1,1, CanvasId.getWidth()-2, CanvasId.getHeight()-2);
        gc.setLineWidth(0.1);
        int a = cellSize;

        for (int i = 0; i < WIDTH; i++) {
            gc.strokeLine(a, 0, a, CanvasId.getHeight());
            gc.strokeLine(0, a, CanvasId.getWidth(), a);
            a+=cellSize;

        }
    }



    public void initialize()
    {
        gc = CanvasId.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CanvasId.getWidth(), CanvasId.getHeight());

        board = new int[WIDTH][HEIGHT];
        cleanBoard = new int[WIDTH][HEIGHT];

        //Starter spillet med å med å lage et brett
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                cleanBoard[i][j] = (1);
            }
        }


        cleanBoard();
        System.out.println((int)CanvasId.getHeight());
    }

    public  void nextGeneration() {
        cleanBoard();
        int[][] next = new int[WIDTH][HEIGHT];

        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < HEIGHT - 1; y++)

            {
                int neighbors = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        neighbors += board[x + i][y + j];
                    }
                }

                neighbors -= board[x][y];
                if ((board[x][y] == 1) && (neighbors < 2)) next[x][y] = 0;           // Mindre enn 2 rundt
                else if ((board[x][y] == 1) && (neighbors > 3)) next[x][y] = 0;           // Fler enn 3 rundt seg
                else if ((board[x][y] == 0) && (neighbors == 3)) next[x][y] = 1;           // Akkurat 3 rundt seg
                else next[x][y] = board[x][y];
            }
        }
        board = next;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if( next[i][j] == 1) gc.fillRect(cellSize*j + canvasBorder, cellSize*i + canvasBorder, cellSize + distanceCells, cellSize + distanceCells);
            }
        }
    }


    public void draw()
    {
        cleanBoard();
        gc = CanvasId.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if( board[i][j] == 1) gc.fillRect(cellSize*j + canvasBorder, cellSize*i + canvasBorder, cellSize + distanceCells, cellSize + distanceCells);
            }
        }
    }

    public void clickedRandomButton()
    {
        System.out.println("You Clicked RANDOM");

        initialize();

        //Lager en ny random array for hver gang start er trykket.
        for (int i =0;i < board.length;i++) {
            for (int j =0;j < board.length;j++) {
                board[i][j] = (byte)(Math.random()*2);
            }
        }
        draw();
    }

    public void init(Stage primaryStage) {

        this.stage = stage;

    }


    public void openFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open GOL Shape");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Run Length Encoded File", "*.RLE"),
                new FileChooser.ExtensionFilter("Text File", "*.txt"),
                new FileChooser.ExtensionFilter("All files", "*")

        );

        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            System.out.println("Choosen file " + file);
        }

        try (final Scanner scanner = new Scanner(file); ) {
            while ( scanner.hasNextLine() ) {
                String line = scanner.nextLine();
                System.out.println( line );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }



    public void readGameBoardFromDisk(File file) throws IOException {

        readGameBoard(new FileReader(file));
    }

    private void readGameBoard(FileReader fileReader) {
    }

    public void closeWindow(){

        Platform.exit();
    }

    public void clickedClearButton()
    {
        System.out.println("You Clicked CLEAR");
        initialize();

        timeline.stop();
    }

    public void clickedCircleButton()
    {
        initialize();


        System.out.println("You Clicked CIRCLE");
    }

    public void clickedStartButton()
    {
        System.out.println("You Clicked START");
        timeline.play();
        // Start Animasjon
    }

    public void clickedInvertButton()
    {
        System.out.println("You Clicked INVERT");
        nextGeneration();
    }

    public void clickedStopButton()
    {
        System.out.println("You Clicked STOP");
        timeline.stop();
        // stop Animasjon
    }

    public Timeline timeline;
    {
        FPS = 10;
        timeline = new Timeline(new KeyFrame(Duration.millis(FPS), e -> {
            nextGeneration();
            timeline.playFromStart();

        }));
    }



}