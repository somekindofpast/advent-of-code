package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle02CubeConundrum {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Cube Conundrum.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        final int RED = 12;
        final int GREEN = 13;
        final int BLUE = 14;
        int sumOfGameIDs = 0;
        for (String currentRow : inputLines) {
            boolean gameOK = true;
            int gameID = Integer.parseInt(currentRow.split(":")[0].split(" ")[1]);
            ArrayList<String> sets = new ArrayList<>(Arrays.asList(currentRow.split(": ")[1].split("; ")));
            for (String set : sets) {
                ArrayList<String> groups = new ArrayList<>(Arrays.asList(set.split(", ")));
                for (String group : groups) {
                    int sameColoredCubesInGroup = Integer.parseInt(group.split(" ")[0]);
                    if (
                            (group.contains("red") && RED < sameColoredCubesInGroup) ||
                            (group.contains("green") && GREEN < sameColoredCubesInGroup) ||
                            (group.contains("blue") && BLUE < sameColoredCubesInGroup)
                    ) {
                        gameOK = false;
                        break;
                    }
                }
                if (!gameOK) {
                    break;
                }
            }
            if (gameOK) {
                sumOfGameIDs += gameID;
            }
        }
        System.out.println("Sum of game IDs: " + sumOfGameIDs);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        int sumOfPowerOfSets = 0;
        for (String currentRow : inputLines) {
            int redMin = 0;
            int blueMin = 0;
            int greenMin = 0;
            ArrayList<String> sets = new ArrayList<>(Arrays.asList(currentRow.split(": ")[1].split("; ")));
            for (String set : sets) {
                ArrayList<String> groups = new ArrayList<>(Arrays.asList(set.split(", ")));
                for (String group : groups) {
                    int sameColoredCubesInGroup = Integer.parseInt(group.split(" ")[0]);
                    if (group.contains("red") && redMin < sameColoredCubesInGroup) {
                        redMin = sameColoredCubesInGroup;
                    } else if (group.contains("green") && greenMin < sameColoredCubesInGroup) {
                        greenMin = sameColoredCubesInGroup;
                    } else if (group.contains("blue") && blueMin < sameColoredCubesInGroup) {
                        blueMin = sameColoredCubesInGroup;
                    }
                }
            }
            sumOfPowerOfSets += redMin * greenMin * blueMin;
        }
        System.out.println("Sum of the power of sets: " + sumOfPowerOfSets);
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
