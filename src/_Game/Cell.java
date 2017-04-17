package _Game;


import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class Cell {
    private int cellSize;
    private int cellGap;
    private int FPS;
    public Color cellColor;
    public Controller controller;

    public Cell(){

        cellSize = 12;
        cellGap = 1;
        FPS = 10;
        cellColor = Color.YELLOW;
    }
    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }



    public Color getCellColor() {
        return cellColor;
    }

    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getCellGap() {
        return cellGap;
    }

    public void setCellGap(int cellGap) {
        this.cellGap = cellGap;
    }




}