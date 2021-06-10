package com.company;

import static com.company.Main.easyAIMove;

public class MediumAI extends AI {

    public MediumAI(char[] grid) {
        super(grid);
    }

    @Override
    public void makeAMove(char player) {
        int ownRowOccurrences;
        int opponentRowOccurrences;
        char opponent;
        
        //Declare opponent
        if (player == 'X') {
            opponent = 'O';
        } else  {
            opponent = 'X';
        }
        //If AI already has 2 characters in a line, it moves to win
        for (int i = 0; i < 3; i++) {
            ownRowOccurrences = 0;
            for (int j = 0; j < 3; j++) {
                if (getGridElement(this.grid, i * 3 + j) == player) {
                    ownRowOccurrences++;
                }
            }
            
            //Check for vertical win
            if (getGridElement(this.grid, i) == player && getGridElement(this.grid, i + 3) == player && getGridElement(this.grid, i + 6) == ' ') {
                setGridElement(this.grid, i * 6, player);
                return;
            } else if (getGridElement(this.grid, i) == player && getGridElement(this.grid, i + 6) == player && getGridElement(this.grid, i + 3) == ' ') {
                setGridElement(this.grid, i * 3, player);
                return;
            } else if (getGridElement(this.grid, i + 3) == player && getGridElement(this.grid, i + 6) == player && getGridElement(this.grid, i) == ' ') {
                setGridElement(this.grid, i, player);
                return;
            }
            
            //Check for horizontal win
            if (ownRowOccurrences == 2) {
                for (int j = 0; j < 3; j++) {
                    if (getGridElement(this.grid, i * 3 + j) == ' ') {
                        setGridElement(this.grid, i * 3 + j, player);
                        return;
                    }
                }
            }
        }
        
        //Check for diagonal win
        if (getGridElement(this.grid, 0) == player && getGridElement(this.grid, 4) == player && getGridElement(this.grid, 8) == ' ') {
            setGridElement(this.grid, 8, player);
            return;
        } else if (getGridElement(this.grid, 4) == player && getGridElement(this.grid, 8) == player && getGridElement(this.grid, 0) == ' ') {
            setGridElement(this.grid, 0, player);
            return;
        } else if (getGridElement(this.grid, 2) == player && getGridElement(this.grid, 4) == player && getGridElement(this.grid, 6) == ' ') {
            setGridElement(this.grid, 6, player);
            return;
        } else if (getGridElement(this.grid, 4) == player && getGridElement(this.grid, 6) == player && getGridElement(this.grid, 2) == ' ') {
            setGridElement(this.grid, 2, player);
            return;
        } else if (((getGridElement(this.grid, 0) == player && getGridElement(this.grid, 8) == player) || (getGridElement(this.grid, 2) == player && getGridElement(this.grid, 6) == player)) && getGridElement(this.grid, 4) == ' ') {
            setGridElement(this.grid, 4, player);
            return;
        }
        //Else if opponent is about to win, AI blocks their move
        for (int i = 0; i < 3; i++) {
            opponentRowOccurrences = 0;
            for (int j = 0; j < 3; j++) {
                if (getGridElement(this.grid, i * 3 + j) == opponent) {
                    opponentRowOccurrences++;
                }
            }
            
            //Prevent enemy from getting a vertical win
            if (getGridElement(this.grid, i) == player && getGridElement(this.grid, i + 3) == opponent && getGridElement(this.grid, i + 6) == ' ') {
                setGridElement(this.grid, i * 6, player);
                return;
            } else if (getGridElement(this.grid, i) == opponent && getGridElement(this.grid, i + 6) == opponent && getGridElement(this.grid, i + 3) == ' ') {
                setGridElement(this.grid, i * 3, player);
                return;
            } else if (getGridElement(this.grid, i + 3) == opponent && getGridElement(this.grid, i + 6) == opponent && getGridElement(this.grid, i) == ' ') {
                setGridElement(this.grid, i, player);
                return;
            }
            
            //Prevent enemy from getting a horizontal win
            if (opponentRowOccurrences == 2) {
                for (int j = 0; j < 3; j++) {
                    if (getGridElement(this.grid, i * 3 + j) == ' ') {
                        setGridElement(this.grid, i * 3 + j, player);
                        return;
                    }
                }
            }
        }
        
        //Prevent enemy from getting a diagonal win
        if (getGridElement(this.grid, 0) == opponent && getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 8) == ' ') {
            setGridElement(this.grid, 8, player);
            return;
        } else if (getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 8) == opponent && getGridElement(this.grid, 0) == ' ') {
            setGridElement(this.grid, 0, player);
            return;
        } else if (getGridElement(this.grid, 2) == opponent && getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 6) == ' ') {
            setGridElement(this.grid, 6, player);
            return;
        } else if (getGridElement(this.grid, 4) == opponent && getGridElement(this.grid, 6) == opponent && getGridElement(this.grid, 2) == ' ') {
            setGridElement(this.grid, 2, player);
            return;
        } else if (((getGridElement(this.grid, 0) == opponent && getGridElement(this.grid, 8) == opponent) || (getGridElement(this.grid, 2) == opponent && getGridElement(this.grid, 6) == opponent)) && getGridElement(this.grid, 4) == ' ') {
            setGridElement(this.grid, 4, player);
            return;
        }
        //Otherwise make a random move
        easyAIMove(player);

    }
}
