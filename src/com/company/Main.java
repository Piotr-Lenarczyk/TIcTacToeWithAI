package com.company;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static char[] characters = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    static int moves = 0;
    static String[] players = {"easy", "easy"};

    public static void initGame() {
        while (true) {
            System.out.print("Input command: ");
            String[] commands = scanner.nextLine().split(" ");
            if (commands.length != 3) {
                if ("exit".equalsIgnoreCase(commands[0])) {
                    System.exit(0);
                } else {
                    System.out.println("Bad parameters!");
                }
            } else if ("exit".equalsIgnoreCase(commands[0])) {
                System.exit(0);
            } else {
                players[0] = commands[1];
                players[1] = commands[2];
                break;
            }
        }
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == ' ') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) {
                return false;
            }
        }
        return true;
    }

    public static void printGrid() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(characters[3 * i + j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }


    public static boolean countOccurrences(char player) {
        int xOccurrences = 0;
        int oOccurrences = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (characters[i * 3 + j] == 'X') {
                    xOccurrences++;
                } else if (characters[i * 3 + j] == 'O') {
                    oOccurrences++;
                }
            }
        }
        if (xOccurrences == oOccurrences) {
            return true;
        } else if (xOccurrences > oOccurrences && player == 'X') {
            return false;
        } else return oOccurrences <= xOccurrences || player != 'O';
    }

    public static void skipMove(char player, int index) {
        if (player == 'X') {
            characters[index] = 'O';
        } else {
            characters[index] = 'X';
        }
        moves++;
        printGrid();
        if(analyzeWinCondition()) {
            System.exit(0);
        }
    }

    public static boolean analyzeWinCondition() {
        int xRowOccurrences, oRowOccurrences;
        boolean xWin = false, oWin = false, emptyFound = false;
        for (int i = 0; i < 3; i++) {
            xRowOccurrences = 0;
            oRowOccurrences = 0;
            for (int j = 0; j < 3; j++) {
                if (characters[i * 3 + j] == 'X') {
                    xRowOccurrences++;
                } else if (characters[i * 3 + j] == 'O') {
                    oRowOccurrences++;
                } else if (characters[i * 3 + j] == ' ') {
                    emptyFound = true;
                }
                if (xRowOccurrences == 3) {
                    xWin = true;
                } else if (oRowOccurrences == 3) {
                    oWin = true;
                }
            }
            if (characters[i] == 'X' && characters[i + 3] == 'X' && characters[i + 6] == 'X') {
                xWin = true;
            } else if (characters[i] == 'O' && characters[i + 3] == 'O' && characters[i + 6] == 'O') {
                oWin = true;
            }
        }
        if (characters[0] == 'X' && characters[4] == 'X' && characters[8] == 'X') {
            xWin = true;
        } else if (characters[2] == 'X' && characters[4] == 'X' && characters[6] == 'X') {
            xWin = true;
        } else if (characters[0] == 'O' && characters[4] == 'O' && characters[8] == 'O') {
            oWin = true;
        } else if (characters[2] == 'O' && characters[4] == 'O' && characters[6] == 'O') {
            oWin = true;
        }
        if (xWin) {
            System.out.println("X wins");
            return true;
        } else if (oWin) {
            System.out.println("O wins");
            return true;
        } else if (!emptyFound) {
            System.out.println("Draw");
            return true;
        } else  {
            return false;
        }
    }

    public static void makeAMove(char player) {
        boolean wrongInput = true;
        while(wrongInput) {
            System.out.print("Enter the coordinates: ");
            String line = scanner.nextLine();
            String[] coordinate = line.split(" ");
            if (coordinate.length == 1 || (!isInteger(coordinate[0], 10) && !isInteger(coordinate[1], 10))) {
                System.out.println("You should enter numbers!");
            } else if (Integer.parseInt(coordinate[0]) < 1 || Integer.parseInt(coordinate[0]) > 3 || Integer.parseInt(coordinate[1]) < 1 || Integer.parseInt(coordinate[1]) > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                int index = 3 * (Integer.parseInt(coordinate[0]) - 1) + (Integer.parseInt(coordinate[1]) - 1);
                if (characters[index] != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    if (countOccurrences(player)) {
                        wrongInput = false;
                        characters[index] = player;
                    }
                    else {
                        skipMove(player, index);
                    }
                }
            }
        }
    }

    public static void easyAIMove(char player) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (characters[i * 3 + j] != 'X' && characters[i * 3 + j] != 'O') {
                    linkedList.add(i * 3 + j);
                }
            }
        }
        Random random = new Random();
        int index = random.nextInt(linkedList.size());
        characters[linkedList.get(index)] = player;
    }

    public static void mediumAIMove(char player) {
        int ownRowOccurrences;
        int opponentRowOccurrences;
        char opponent;
        if (player == 'X') {
            opponent = 'O';
        } else  {
            opponent = 'X';
        }
        //If AI already has 2 characters in a line, it moves to win
        for (int i = 0; i < 3; i++) {
            ownRowOccurrences = 0;
            for (int j = 0; j < 3; j++) {
                if (characters[i * 3 + j] == player) {
                    ownRowOccurrences++;
                }
            }
            if (characters[i] == player && characters[i + 3] == player && characters[i + 6] == ' ') {
                characters[i + 6] = player;
                return;
            } else if (characters[i] == player && characters[i + 6] == player && characters[i + 3] == ' ') {
                characters[i + 3] = player;
                return;
            } else if (characters[i + 3] == player && characters[i + 6] == player && characters[i] == ' ') {
                characters[i] = player;
                return;
            }
            if (ownRowOccurrences == 2) {
                for (int j = 0; j < 3; j++) {
                    if (characters[i * 3 + j] == ' ') {
                        characters[i * 3 + j] = player;
                        return;
                    }
                }
            }
        }
        if (characters[0] == player && characters[4] == player && characters[8] == ' ') {
            characters[8] = player;
            return;
        } else if (characters[4] == player && characters[8] == player && characters[0] == ' ') {
            characters[0] = player;
            return;
        } else if (characters[2] == player && characters[4] == player && characters[6] == ' ') {
            characters[6] = player;
            return;
        } else if (characters[4] == player && characters[6] == player && characters[2] == ' ') {
            characters[2] = player;
            return;
        } else if (((characters[0] == player && characters[8] == player) || (characters[2] == player && characters[6] == player)) && characters[4] == ' ') {
            characters[4] = player;
            return;
        }
        //Else if opponent is about to win, AI blocks their move
        for (int i = 0; i < 3; i++) {
            opponentRowOccurrences = 0;
            for (int j = 0; j < 3; j++) {
                if (characters[i * 3 + j] == opponent) {
                    opponentRowOccurrences++;
                }
            }
            if (characters[i] == player && characters[i + 3] == opponent && characters[i + 6] == ' ') {
                characters[i + 6] = player;
                return;
            } else if (characters[i] == opponent && characters[i + 6] == opponent && characters[i + 3] == ' ') {
                characters[i + 3] = player;
                return;
            } else if (characters[i + 3] == opponent && characters[i + 6] == opponent && characters[i] == ' ') {
                characters[i] = player;
                return;
            }
            if (opponentRowOccurrences == 2) {
                for (int j = 0; j < 3; j++) {
                    if (characters[i * 3 + j] == ' ') {
                        characters[i * 3 + j] = player;
                        return;
                    }
                }
            }
        }
        if (characters[0] == opponent && characters[4] == opponent && characters[8] == ' ') {
            characters[8] = player;
            return;
        } else if (characters[4] == opponent && characters[8] == opponent && characters[0] == ' ') {
            characters[0] = player;
            return;
        } else if (characters[2] == opponent && characters[4] == opponent && characters[6] == ' ') {
            characters[6] = player;
            return;
        } else if (characters[4] == opponent && characters[6] == opponent && characters[2] == ' ') {
            characters[2] = player;
            return;
        } else if (((characters[0] == opponent && characters[8] == opponent) || (characters[2] == opponent && characters[6] == opponent)) && characters[4] == ' ') {
            characters[4] = player;
            return;
        }
        //Otherwise make a random move
        easyAIMove(player);
    }

    public static int evaluateMove(char player, char opponent) {
    //Checking for own win
        //Checking for rows
        if (characters[0] == player && characters[1] == player && characters[2] == player) {
            return 10;
        } else if (characters[3] == player && characters[4] == player && characters[5] == player) {
            return 10;
        } else if (characters[6] == player && characters[7] == player && characters[8] == player) {
            return 10;
        } else if (characters[0] == player && characters[3] == player && characters[6] == player) { //Checking for columns
            return 10;
        } else if (characters[1] == player && characters[4] == player && characters[7] == player) {
            return 10;
        } else if (characters[2] == player && characters[5] == player && characters[8] == player) {
            return 10;
        } else if (characters[0] == player && characters[4] == player && characters[8] == player) { //Checking for diagonals
            return 10;
        } else if (characters[2] == player && characters[4] == player && characters[6] == player) {
            return 10;
        }
    //Checking for opponent win
        //Checking for rows
        if (characters[0] == opponent && characters[1] == opponent && characters[2] == opponent) {
            return -10;
        } else if (characters[3] == opponent && characters[4] == opponent && characters[5] == opponent) {
            return -10;
        } else if (characters[6] == opponent && characters[7] == opponent && characters[8] == opponent) {
            return -10;
        } else if (characters[0] == opponent && characters[3] == opponent && characters[6] == opponent) { //Checking for columns
            return -10;
        } else if (characters[1] == opponent && characters[4] == opponent && characters[7] == opponent) {
            return -10;
        } else if (characters[2] == opponent && characters[5] == opponent && characters[8] == opponent) {
            return -10;
        } else if (characters[0] == opponent && characters[4] == opponent && characters[8] == opponent) { //Checking for diagonals
            return -10;
        } else if (characters[2] == opponent && characters[4] == opponent && characters[6] == opponent) {
            return -10;
        }
        return 0;
    }

    public static boolean isGridFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (characters[i * 3 + j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void hardAIMove(char player) {
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
                if (characters[3 * i + j] == ' ') {
                    characters[3 * i + j] = player;
                    int score = minimax(player, 0, false, opponent);
                    characters[3 * i + j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestPosition = 3 * i + j;
                    }
                }
            }
        }
        characters[bestPosition] = player;
    }

    public static int minimax(char player, int recursionLevel, boolean isMaximizer, char opponent) {
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
                    if (characters[3 * i + j] == ' ') {
                        characters[3 * i + j] = player;
                        int score = minimax(player, recursionLevel + 1, false, opponent);
                        characters[3 * i + j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (characters[3 * i + j] == ' ') {
                        characters[3 * i + j] = opponent;
                        int score = minimax(player, recursionLevel + 1, true, opponent);
                        characters[3 * i + j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    public static void main(String[] args) {
        initGame();
        printGrid();
        while(!analyzeWinCondition()) {
            if(moves % 2 == 0) {
                if ("user".equalsIgnoreCase(players[0])) {
                    makeAMove('X');
                } else if ("easy".equalsIgnoreCase(players[0])) {
                    System.out.println("Making move level \"easy\"");
                    easyAIMove('X');
                } else if ("medium".equalsIgnoreCase(players[0])) {
                    System.out.println("Making move level \"medium\"");
                    mediumAIMove('X');
                } else if ("hard".equalsIgnoreCase(players[0])) {
                    System.out.println("Making move level \"hard\"");
                    hardAIMove('X');
                }
            } else {
                if ("user".equalsIgnoreCase(players[1])) {
                    makeAMove('O');
                } else if ("easy".equalsIgnoreCase(players[1])) {
                    System.out.println("Making move level \"easy\"");
                    easyAIMove('O');
                } else if ("medium".equalsIgnoreCase(players[1])) {
                    System.out.println("Making move level \"medium\"");
                    mediumAIMove('O');
                } else if ("hard".equalsIgnoreCase(players[1])) {
                    System.out.println("Making move level \"hard\"");
                    hardAIMove('O');
                }
            }
            printGrid();
            moves++;
        }
    }
}
