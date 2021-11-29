package com.codecool.adventofcode2015;

import java.util.ArrayList;
import java.util.HashMap;

public class Puzzle20InfiniteElvesAndInfiniteHouses {
    public static void main(String[] args) {
        puzzleA();
    }

    private static void puzzleA() {
        /*What is the lowest house number of the house to get at least as many presents as the number in your puzzle input?
        Your puzzle input is 36000000.*/

        final long presentGoal = 36000000;
        final long presentMultiplier = 10;
        long houseNumber = 0;
        long presents, elfNumber;
        do {
            houseNumber++;
            presents = 0;

            for (elfNumber = 1; elfNumber <= houseNumber/2; elfNumber++) {
                if(houseNumber % elfNumber == 0) {
                    presents += elfNumber * presentMultiplier;
                }
            }
            presents += houseNumber * presentMultiplier;

            //System.out.println("House " + houseNumber + " got " + presents + " presents.");

        }while(presents < presentGoal);

        System.out.println();
        System.out.println("The present goal of " + presentGoal + " has been reached (" + presents + "). The current house number is: " + houseNumber);
    }
}
