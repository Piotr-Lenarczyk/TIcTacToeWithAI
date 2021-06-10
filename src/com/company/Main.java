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
            
            //Check for horizontal wins
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
            
            //Check for vertical win
            if (characters[i] == 'X' && characters[i + 3] == 'X' && characters[i + 6] == 'X') {
                xWin = true;
            } else if (characters[i] == 'O' && characters[i + 3] == 'O' && characters[i + 6] == 'O') {
                oWin = true;
            }
        }
        
        //Check for diagonal win
        if (characters[0] == 'X' && characters[4] == 'X' && characters[8] == 'X') {
            xWin = true;
        } else if (characters[2] == 'X' && characters[4] == 'X' && characters[6] == 'X') {
            xWin = true;
        } else if (characters[0] == 'O' && characters[4] == 'O' && characters[8] == 'O') {
            oWin = true;
        } else if (characters[2] == 'O' && characters[4] == 'O' && characters[6] == 'O') {
            oWin = true;
        }
        
        //Print gamestate
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
            
            //If input parameters are not numbers
            if (coordinate.length == 1 || (!isInteger(coordinate[0], 10) && !isInteger(coordinate[1], 10))) {
                System.out.println("You should enter numbers!");
                
            //If coordinates are different from <1; 3> range
            } else if (Integer.parseInt(coordinate[0]) < 1 || Integer.parseInt(coordinate[0]) > 3 || Integer.parseInt(coordinate[1]) < 1 || Integer.parseInt(coordinate[1]) > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                int index = 3 * (Integer.parseInt(coordinate[0]) - 1) + (Integer.parseInt(coordinate[1]) - 1);
                
                //If cell is already taken
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

    
    //Function passed to mediumAI
    public static void easyAIMove(char player) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        
        //Add each empty cell to the list
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (characters[i * 3 + j] != 'X' && characters[i * 3 + j] != 'O') {
                    linkedList.add(i * 3 + j);
                }
            }
        }
        
        //Choose random empty cell
        Random random = new Random();
        int index = random.nextInt(linkedList.size());
        characters[linkedList.get(index)] = player;
    }

    public static void main(String[] args) {
        boolean repeatPrompt;
        //Generate AIs
        EasyAI easyAI = new EasyAI(characters);
        MediumAI mediumAI = new MediumAI(characters);
        HardAI hardAI = new HardAI(characters);
        
        //Repeat until forced to stop
        do {
            repeatPrompt = false;
            initGame();
            printGrid();
            while (!analyzeWinCondition()) {
                if (moves % 2 == 0) {
                    //Choose a player
                    if ("user".equalsIgnoreCase(players[0])) {
                        makeAMove('X');
                    } else if ("easy".equalsIgnoreCase(players[0])) {
                        System.out.println("Making move level \"easy\"");
                        easyAI.makeAMove('X');
                    } else if ("medium".equalsIgnoreCase(players[0])) {
                        System.out.println("Making move level \"medium\"");
                        mediumAI.makeAMove('X');
                    } else if ("hard".equalsIgnoreCase(players[0])) {
                        System.out.println("Making move level \"hard\"");
                        hardAI.makeAMove('X');
                    } else {
                        repeatPrompt = true;
                        break;
                    }
                } else {
                    //Choose a player
                    if ("user".equalsIgnoreCase(players[1])) {
                        makeAMove('O');
                    } else if ("easy".equalsIgnoreCase(players[1])) {
                        System.out.println("Making move level \"easy\"");
                        easyAI.makeAMove('O');
                    } else if ("medium".equalsIgnoreCase(players[1])) {
                        System.out.println("Making move level \"medium\"");
                        mediumAI.makeAMove('O');
                    } else if ("hard".equalsIgnoreCase(players[1])) {
                        System.out.println("Making move level \"hard\"");
                        hardAI.makeAMove('O');
                    } else {
                        repeatPrompt = true;
                        break;
                    }
                }
                printGrid();
                moves++;
            }
        } while (repeatPrompt);
    }
}
