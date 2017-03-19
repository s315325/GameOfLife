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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Controller   {
    private Stage stage;
    public Canvas CanvasId;
    public GraphicsContext gc;
    public Button startButton, stopButton, randomButton, clearButton;
    public int cellSize, TIME, cellGap;
    public double lineWidth;
    public int[][] board, cleanBoard;
    private final int HEIGHT, WIDTH;
    public Color cell, line, background;

    public int FPS = 15;

    public Controller()
    {
        //FARGER
        cell = Color.GREENYELLOW;
        line = Color.BLACK;
        background = Color.GREY;


        //Variabler til spillbrettet
        cellSize = 10;
        cellGap = 1;
        lineWidth = 0.7;
        HEIGHT = 720 / cellSize; //Manuelt plottet inn CanvasHeight
        WIDTH = 1280 / cellSize; //Manuelt plottet inn CanvasWidth


    }

    public void cleanBoard()
    {
        gc.clearRect(0, 0, CanvasId.getWidth(), CanvasId.getHeight());
        gc.setFill(Color.GREY);
        gc.fillRect(0, 0, CanvasId.getWidth(), CanvasId.getHeight());

    }

    public void drawLines()
    {
        gc.setStroke(line);
        //gc.setLineWidth(2*cellSize);
        gc.strokeRect(0, 0, CanvasId.getWidth(), CanvasId.getHeight());
        gc.setLineWidth(lineWidth);
        int a = cellSize;
        int b = cellSize;

        for (int i = 0; i < HEIGHT; i++) {
            gc.strokeLine(0, a, CanvasId.getWidth(), a);
            a += cellSize;
        }
        for (int i = 0; i < WIDTH; i++) {
            gc.strokeLine(b, 0, b, CanvasId.getHeight());
            b += cellSize;
        }
    }

    public void initialize()
    {
        gc = CanvasId.getGraphicsContext2D();
        gc.setFill(background);
        gc.fillRect(0, 0, CanvasId.getWidth(), CanvasId.getHeight());

        board = new int[HEIGHT][WIDTH];
        cleanBoard = new int[HEIGHT][WIDTH];

        //Starter spillet med å med å lage et brett
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                cleanBoard[i][j] = (1);
            }
        }
        System.out.println("CanvasHeight = " + (int)CanvasId.getHeight());
        System.out.println("CanvasWidth = " + (int)CanvasId.getWidth());
        System.out.println("Current FPS = " + FPS);

        draw();
        drawLines();

    }


    public  void nextGeneration() {
        cleanBoard();
        int[][] nextBoard = new int[HEIGHT][WIDTH];

        for (int x = 1; x < HEIGHT - 1; x++) {
            for (int y = 1; y < WIDTH - 1; y++)

            {
                int cellNeighbors = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        cellNeighbors += board[x + i][y + j];
                    }
                }

                cellNeighbors -= board[x][y];
                if ((board[x][y] == 1) && (cellNeighbors < 2)) nextBoard[x][y] = 0;           // Mindre enn 2 rundt
                else if ((board[x][y] == 1) && (cellNeighbors > 3)) nextBoard[x][y] = 0;           // Fler enn 3 rundt seg
                else if ((board[x][y] == 0) && (cellNeighbors == 3)) nextBoard[x][y] = 1;           // Akkurat 3 rundt seg
                else nextBoard[x][y] = board[x][y];
            }
        }
        board = nextBoard;
        draw();
        drawLines();


    }


    public void draw()
    {
        cleanBoard();
        gc = CanvasId.getGraphicsContext2D();
        gc.setFill(cell);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if( board[i][j] == 1) gc.fillRect(cellSize*j, cellSize*i, cellSize - cellGap, cellSize- cellGap);
            }
        }
    }

    public void clickedRandomButton()
    {
        System.out.println("You Clicked RANDOM");

        //Lager en ny random array for hver gang start er trykket.
        for (int i = 0;i < HEIGHT;i++) {
            for (int j = 0;j < WIDTH;j++) {
                board[i][j] = (byte)(Math.random()*2);
            }
        }

        draw();
        drawLines();

    }

    public void mouseClicked(){


    }

    public void init(Stage primaryStage)
    {

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

        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("null");
        }


    }



    public void readGameBoardFromDisk(File file) throws IOException
    {

        readGameBoard(new FileReader(file));
    }

    private void readGameBoard(FileReader fileReader)
    {
    }

    public void closeWindow()
    {
        Platform.exit();
    }

    public void clickedClearButton()
    {
        System.out.println("You Clicked CLEAR");
        timeline.stop();
        initialize();

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
        TIME = 1000/FPS;
        timeline = new Timeline(new KeyFrame(Duration.millis(TIME), e -> {
            nextGeneration();
            timeline.playFromStart();

        }));
    }



}