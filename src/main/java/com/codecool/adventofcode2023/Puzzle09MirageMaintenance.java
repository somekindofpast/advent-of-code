package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle09MirageMaintenance {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Mirage Maintenance.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        long sumOfExtrapolatedValues = 0;
        for (String currentRow : inputLines) {
            String[] numbers = currentRow.split(" ");
            ArrayList<ArrayList<String>> numberRows = new ArrayList<>();
            numberRows.add(new ArrayList<>(Arrays.asList(numbers)));
            for (int i = 0; i < numberRows.size(); i++) {
                boolean isZeroRowReached = true;
                ArrayList<String> nextRow = new ArrayList<>();
                for (int j = 1; j < numberRows.get(i).size(); j++) {
                    long number = Long.parseLong(numberRows.get(i).get(j));
                    long numberPrevious = Long.parseLong(numberRows.get(i).get(j - 1));
                    long result = number - numberPrevious;
                    nextRow.add("" + result);
                    if (result != 0) {
                        isZeroRowReached = false;
                    }
                }
                numberRows.add(nextRow);
                if (isZeroRowReached) {
                    for (int k = numberRows.size() - 2; 0 <= k; k--) {
                        long number = Long.parseLong(numberRows.get(k + 1).get(numberRows.get(k + 1).size() - 1));
                        long numberPrevious = Long.parseLong(numberRows.get(k).get(numberRows.get(k).size() - 1));
                        numberRows.get(k).add("" + (number + numberPrevious));
                    }
                    break;
                }
            }
            sumOfExtrapolatedValues += Long.parseLong(numberRows.get(0).get(numberRows.get(0).size() - 1));
        }
        System.out.println("sum of extrapolated values: " + sumOfExtrapolatedValues);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        long sumOfExtrapolatedValues = 0;
        for (String currentRow : inputLines) {
            String[] numbers = currentRow.split(" ");
            ArrayList<ArrayList<String>> numberRows = new ArrayList<>();
            numberRows.add(new ArrayList<>(Arrays.asList(numbers)));
            for (int i = 0; i < numberRows.size(); i++) {
                boolean isZeroRowReached = true;
                ArrayList<String> nextRow = new ArrayList<>();
                for (int j = 1; j < numberRows.get(i).size(); j++) {
                    long number = Long.parseLong(numberRows.get(i).get(j));
                    long numberPrevious = Long.parseLong(numberRows.get(i).get(j - 1));
                    long result = number - numberPrevious;
                    nextRow.add("" + result);
                    if (result != 0) {
                        isZeroRowReached = false;
                    }
                }
                numberRows.add(nextRow);
                if (isZeroRowReached) {
                    for (int k = numberRows.size() - 2; 0 <= k; k--) {
                        long number = Long.parseLong(numberRows.get(k + 1).get(0));
                        long numberPrevious = Long.parseLong(numberRows.get(k).get(0));
                        numberRows.get(k).add(0, "" + ( numberPrevious - number));
                    }
                    break;
                }
            }
            sumOfExtrapolatedValues += Long.parseLong(numberRows.get(0).get(0));
        }
        System.out.println("sum of extrapolated values: " + sumOfExtrapolatedValues);
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
