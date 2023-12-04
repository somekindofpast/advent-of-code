package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle04Scratchcards {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Scratchcards.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        int totalPoints = 0;
        for (String currentRow : inputLines) {
            String[] numberRows = currentRow.split(":")[1].split("\\|");
            ArrayList<Integer> ourNumbers = new ArrayList<>();
            int cardPoints = 0;
            String[] numberStrings = numberRows[1].trim().split("\\s+");
            for (String numberString : numberStrings) {
                ourNumbers.add(Integer.parseInt(numberString));
            }
            numberStrings = numberRows[0].trim().split("\\s+");
            for (String numberString : numberStrings) {
                int winningNumber = Integer.parseInt(numberString);
                if (ourNumbers.contains(winningNumber)) {
                    if (cardPoints == 0) {
                        cardPoints++;
                    } else {
                        cardPoints *= 2;
                    }
                }
            }
            totalPoints += cardPoints;
        }
        System.out.println("total points on cards: " + totalPoints);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        int[] cardGroups = new int[inputLines.size()];
        Arrays.fill(cardGroups, 1);
        int row = 0;
        for (String currentRow : inputLines) {
            String[] numberRows = currentRow.split(":")[1].split("\\|");
            ArrayList<Integer> ourNumbers = new ArrayList<>();
            int matches = 0;
            String[] numberStrings = numberRows[1].trim().split("\\s+");
            for (String numberString : numberStrings) {
                ourNumbers.add(Integer.parseInt(numberString));
            }
            numberStrings = numberRows[0].trim().split("\\s+");
            for (String numberString : numberStrings) {
                int winningNumber = Integer.parseInt(numberString);
                if (ourNumbers.contains(winningNumber)) {
                    matches++;
                }
            }
            for (int cardRepeats = 1; cardRepeats <= cardGroups[row]; cardRepeats++) {
                for (int i = 1; i <= matches && row + i < cardGroups.length; i++) {
                    cardGroups[row + i]++;
                }
            }
            row++;
        }
        int totalNumberOfScratchCards = 0;
        for (int cardPerGroup : cardGroups) {
            totalNumberOfScratchCards += cardPerGroup;
        }
        System.out.println("total number of scratchcards: " + totalNumberOfScratchCards);
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
