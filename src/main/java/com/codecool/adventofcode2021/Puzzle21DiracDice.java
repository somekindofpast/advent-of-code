package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle21DiracDice {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Dirac Dice.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        int p1Pos = 4;
        int p2Pos = 8;
        int p1Score = 0;
        int p2Score = 0;
        final int timesToRoll = 3;
        final int scoreToReach = 1000;
        final int trackLength = 10;
        int counter = 0;
        int dieRolls = 0;

        DeterministicDie die = new DeterministicDie(100);

        while(p1Score < scoreToReach && p2Score < scoreToReach) {

            int stepsToMove = 0;
            System.out.print("Rolls: ");
            for (int i = 0; i < timesToRoll; i++) {
                int roll = die.roll();
                System.out.print(roll + " ");
                stepsToMove += roll;
                if(counter % 2 == 0) {
                    p1Pos += roll;
                    if(trackLength < p1Pos)
                        p1Pos = p1Pos % trackLength;
                } else {
                    p2Pos += roll;
                    if(trackLength < p2Pos)
                        p2Pos = p2Pos % trackLength;
                }
                dieRolls++;
            }

            if(counter % 2 == 0) {
                p1Score += p1Pos;
                System.out.print("and Player 1 moves to " + p1Pos + " for a total score of " + p1Score);
            } else {
                p2Score += p2Pos;
                System.out.print("and Player 2 moves to " + p2Pos + " for a total score of " + p2Score);
            }
            System.out.println();

            counter++;
        }

        System.out.println("p1 points: " + p1Score);
        System.out.println("p2 points: " + p2Score);
        System.out.println("die rolls: " + dieRolls);
    }

    private static class DeterministicDie {
        private final int sides;
        private int currentSide;

        public DeterministicDie(int sides) {
            this.sides = sides;
            this.currentSide = 0;
        }

        public int roll() {
            currentSide++;
            if(100 < currentSide)
                currentSide = 1;
            return currentSide;
        }
    }

    private static String[] readLines(String pathName) {
        File file = new File(pathName);
        List<String> lines = new ArrayList<>();

        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                lines.add(fileReader.nextLine());
            }
            fileReader.close();
        } catch (IOException e) {
            return new String[0];
        }

        String[] linesArray = new String[lines.size()];
        linesArray = lines.toArray(linesArray);
        return linesArray;
    }

    private static void writeLinesToFile(String[] lines, String pathName) {
        File file = new File(pathName);

        try {
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);

            for (String line : lines) {
                fileWriter.append(line).append("\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("There was an error!");
        }
    }
}
