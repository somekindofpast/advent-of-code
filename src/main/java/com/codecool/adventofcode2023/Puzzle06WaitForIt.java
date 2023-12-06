package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle06WaitForIt {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Wait For It.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        ArrayList<String> maxTimesStr = new ArrayList<>(Arrays.asList(inputLines.get(0).split(":")[1].trim().split("\\s+")));
        ArrayList<String> minDistancesStr = new ArrayList<>(Arrays.asList(inputLines.get(1).split(":")[1].trim().split("\\s+")));

        int numberOfWaysToBeatRecord = 1;
        for (int i = 0; i < maxTimesStr.size(); i++) {
            int maxTime = Integer.parseInt(maxTimesStr.get(i));
            int minDistance = Integer.parseInt(minDistancesStr.get(i));
            int winningAttempts = 0;
            for (int milliSec = 1; milliSec <= maxTime; milliSec++) {
                int distance = (maxTime - milliSec) * milliSec;
                if (minDistance < distance) {
                    winningAttempts++;
                }
            }
            numberOfWaysToBeatRecord *= winningAttempts;
        }

        System.out.println("number of ways to beat record: " + numberOfWaysToBeatRecord);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        ArrayList<String> maxTimesStr = new ArrayList<>(Arrays.asList(inputLines.get(0).split(":")[1].trim().split("\\s+")));
        ArrayList<String> minDistancesStr = new ArrayList<>(Arrays.asList(inputLines.get(1).split(":")[1].trim().split("\\s+")));

        String maxTimeStr = "";
        String minDistanceStr = "";
        for (int i = 0; i < maxTimesStr.size(); i++) {
            maxTimeStr += maxTimesStr.get(i);
            minDistanceStr += minDistancesStr.get(i);
        }

        maxTimesStr.clear();
        maxTimesStr.add(maxTimeStr);

        minDistancesStr.clear();
        minDistancesStr.add(minDistanceStr);

        long numberOfWaysToBeatRecord = 1;
        for (int i = 0; i < maxTimesStr.size(); i++) {
            long maxTime = Long.parseLong(maxTimesStr.get(i));
            long minDistance = Long.parseLong(minDistancesStr.get(i));
            long winningAttempts = 0;
            for (long milliSec = 1; milliSec <= maxTime; milliSec++) {
                long distance = (maxTime - milliSec) * milliSec;
                if (minDistance < distance) {
                    winningAttempts++;
                }
            }
            numberOfWaysToBeatRecord *= winningAttempts;
        }

        System.out.println("number of ways to beat record: " + numberOfWaysToBeatRecord);
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
