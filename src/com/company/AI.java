package com.company;

abstract public class AI {
    char[] grid;

    public AI(char[] grid) {
        this.grid = grid;
    }

    public char[] getGrid() {
        return grid;
    }

    public void setGrid(char[] grid) {
        this.grid = grid;
    }

    public char getGridElement(char[] grid, int index) {
        return grid[index];
    }

    public void setGridElement(char[] grid, int index, char value) {
        grid[index] = value;
    }

    abstract public void makeAMove(char player);
}
