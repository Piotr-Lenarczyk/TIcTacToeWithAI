package com.company;

public class HardAI extends AI {

    public HardAI(char[] grid) {
        super(grid);
    }

    @Override
    public void makeAMove(char player) {
        int bestScore = Integer.MIN_VALUE;
        int bestPosition = -1;
        char opponent;
        if (player == 'X') {
            opponent = 'O';
        } else  {
            opponent = 'X';
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getGridElement(this.grid, 3 * i + j) == ' ') {
                    setGridElement(this.grid, 3 * i + j, player);
                    int score = minimax(player, 0, false, opponent);
                    setGridElement(this.grid, 3 * i + j, ' ');
                    if (score > bestScore) {
                        bestScore = score;
                        bestPosition = 3 * i + j;
                    }
                }
            }
        }
        setGridElement(this.grid, bestPosition, player);
    }

    public int minimax(char player, int recursionLevel, boolean isMaximizer, char opponent) {
        int result = evaluateMove(player, opponent);
        if (result == 10 || result == -10) {
            return result;
        } else if (isGridFull()) {
            return 0;
        }
        int bestScore;
        if (isMaximizer) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (getGridElement(this.grid, 3 * i + j) == ' ') {
                        setGridElement(this.grid, 3 * i + j, player);
                        int score = minimax(player, recursionLevel + 1, false, opponent);
                        setGridElement(this.grid, 3 * i + j, ' ');
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (getGridElement(this.grid, 3 * i + j) == ' ') {
                        setGridElement(this.grid, 3 * i + j, opponent);
                        int score = minimax(player, recursionLevel + 1, true, opponent);
                        setGridElement(this.grid, 3 * i + j, ' ');
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    public int evaluateMove(char player, char opponent) {
        //Checking for own win
        //Checking for rows
        if (getGridElement(this.grid, 0) == player && getGridElement(this.grid, 1) == player && getGridElement(this.grid, 2) == player) {
            return 10;
        } else if (getGridElement(this.grid, 3) == player && getGridElement(this.grid, 4) == player && getGridElement(this.grid, 5) == player) {
            return 10;
        } else if (getGridElement(this.grid, 6) == player && getGridElement(this.grid, 7) == player && getGridElement(this.grid, 8) == player) {
            return 10;
        } else if (getGridElement(this.grid, 0) == player && getGridElement(this.grid, 3) == player && getGridElement(this.grid, 6) == player) { //Checking for columns
            return 10;
        } else if (getGridElement(this.grid, 1) == player && getGridElement(this.grid, 4) == player && getGridElement(this.grid, 7) == player) {
            return 10;
        } else if (getGridElement(this.grid, 2) == player && getGridElement(this.grid, 5) == player && getGridElement(this.grid, 8) == player) {
            return 10;
        } else if (getGridElement(this.grid, 0) == player && getGridElement(this.grid, 4) == player && getGridElement(this.grid, 8) == player) { //Checking for diagonals
            return 10;
        } else if (getGridElement(this.grid, 2) == player && getGridElement(this.grid, 4) == player && getGridElement(this.grid, 6) == player) {
            return 10;
        }
        //Checking for opponent win
        //Checking for rows
        if (getGridElement(this.grid, 0) == opponent && getGridElement(this.grid, 1) == opponent && getGridElement(this.grid, 2) == opponent) {
            return -10;
        } else if (getGridElement(this.grid, 3) == opponent && getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 5) == opponent) {
            return -10;
        } else if (getGridElement(this.grid, 6) == opponent && getGridElement(this.grid, 7) == opponent && getGridElement(this.grid, 8) == opponent) {
            return -10;
        } else if (getGridElement(this.grid, 0) == opponent && getGridElement(this.grid, 3) == opponent && getGridElement(this.grid, 6) == opponent) { //Checking for columns
            return -10;
        } else if (getGridElement(this.grid, 1) == opponent && getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 7) == opponent) {
            return -10;
        } else if (getGridElement(this.grid, 2) == opponent && getGridElement(this.grid, 5) == opponent && getGridElement(this.grid, 8) == opponent) {
            return -10;
        } else if (getGridElement(this.grid, 0) == opponent && getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 8) == opponent) { //Checking for diagonals
            return -10;
        } else if (getGridElement(this.grid, 2) == opponent && getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 6) == opponent) {
            return -10;
        }
        return 0;
    }

    public boolean isGridFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getGridElement(this.grid, 3 * i + j) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
