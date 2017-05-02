package _Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class DynamicBoard {

    private StaticBoard staticBoard;
    private Controller controller;
    private CanvasFrame canvasFrame;
    private Cell cell;
    private int canvasHeight;
    private int canvasWidth;
    private byte[][] sBoard;
    public ArrayList<ArrayList<Byte>> dBoard;

   public DynamicBoard(int height, int width, byte[][] board) {
        this.canvasHeight = height;
        this.canvasWidth = width;
        this.sBoard = board;
        this.cell = new Cell();

        //byte[][] sBoard = new staticBoard.getBoard();
    }


    public void DynamicTest() {



        /*System.out.println(sBoard);
        int x = 10;
        int y = 10;

        int x1 = 10; //Testtall
        int y1 = 10; //Testtall

        for (int i = 0; i < x; i++) {
            List<Byte> inner = new ArrayList<Byte>();
            for (int j = 0; j < y; j++)
                inner.add((byte)0);
            dBoard.add(inner);
        }

        dBoard.get(1).set(1, (byte)1); // replace fordi Integer er immutable

        System.out.println("\nDynamisk 2D tabell:");
        dBoard.forEach((l) -> System.out.println(l));

        dBoard.get(2).add((byte)1);
        System.out.println("\nJagged 2D tabell:");
        dBoard.forEach((l) -> System.out.println(l));
        */
        cleanArrayTest();

    }

    public void cleanArray() {


        ArrayList<ArrayList<Byte>> dBoard = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Byte> inner = new ArrayList<>();
            for (int j = 0; j < 10; j++)
                inner.add((byte)0);
            dBoard.add(inner);
        }
        dBoard.get(1).set(1, (byte)1); // replace fordi Integer er immutable

        dBoard.forEach((l) -> System.out.println(l));
    }


    public void cleanArrayTest(){
       int x = canvasHeight;
       int y = canvasWidth;
       ArrayList<ArrayList<Byte>> cleanArray = new ArrayList<ArrayList<Byte>>();
       for( int i = 0; i < x ; i++){
           ArrayList<Byte> rowArrayList = new ArrayList<Byte>();
           for(int j = 0; j < y; j++){
               rowArrayList.add((byte)0);
           }
           cleanArray.add(rowArrayList);
       }
        cleanArray.forEach((l) -> System.out.println(l));
    }

    public void cleanArrayTest2() {

        ArrayList[][] table = new ArrayList[10][10];
        table[0][0] = new ArrayList(); // add another ArrayList object to [0,0]
        table[0][0].add((byte )1);

        for(ArrayList[] inner : table)
            System.out.println(Arrays.toString(inner));
        for( int i = 0; i < 11 ; i++){
            for(int j = 0; j < 11; j++) {
                table[i][j].add((byte)1);
            }}


    }


            /**
             * This method is used to make a new board with rules.
             *
             * @return Nothing.
             */


        public void nextGeneration () {
/*
        ArrayList<ArrayList<Byte>> nextdBoard = new ArrayList<ArrayList<Byte>>();
        for (int x = 1; x < canvasHeight - 1; x++) {
            ArrayList<Byte> rowArrayList = new ArrayList<Byte>();
            for (int y = 1; y < canvasWidth - 1; y++)

            {
                int cellNeighbors = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        cellNeighbors += dBoard[x + i][y + j];
                    }
                }

                cellNeighbors -= dBoard[x][y];
                if ((dBoard[x][y] == 1) && (cellNeighbors < 2)) nextdBoard[x][y].add((byte)0);           // Mindre enn 2 rundt
                else if ((dBoard[x][y] == 1)) && (cellNeighbors > 3)) nextdBoard[x][y].add((byte)0);           // Fler enn 3 rundt seg
                else if ((dBoard[x][y] == 0) && (cellNeighbors == 3)) nextdBoard[x][y].add((byte)0);           // Akkurat 3 rundt seg
                else nextdBoard[x][y] = dBoard[x][y];
            }
        }

        dBoard = nextdBoard;
        */

        }

    /*public List<List<Byte>> getdBoard() {
        return dBoard;
    }

    public void setdBoard(List<List<Byte>> dBoard) {
        this.dBoard = dBoard;
    }

    public int setdBoardRandom(int i, int j) {

        return  dBoard[i][j].add((byte) (Math.random() * 2));

    }*/


}



