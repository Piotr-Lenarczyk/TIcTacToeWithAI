package com.company;

import java.util.LinkedList;
import java.util.Random;

public class EasyAI extends AI {

    public EasyAI(char[] grid) {
        super(grid);
    }

    @Override
    public void makeAMove(char player) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        
        //Add each empty cell to the list
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getGridElement(this.grid, i * 3 + j) != 'X' && getGridElement(this.grid, i * 3 + j) != 'O') {
                    linkedList.add(i * 3 + j);
                }
            }
        }
        
        //Choose random empty cell
        Random random = new Random();
        int index = random.nextInt(linkedList.size());
        setGridElement(this.grid, linkedList.get(index), player);
    }
}
