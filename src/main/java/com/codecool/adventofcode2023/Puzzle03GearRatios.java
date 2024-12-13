package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle03GearRatios {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Gear Ratios.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        int sumPartNumbers = 0;
        int sumGearRatios = 0;
        for (int row = 0; row < inputLines.size(); row++) {
            String currentRow = inputLines.get(row);
            for (int i = 0; i < currentRow.length(); i++) {
                char c = currentRow.charAt(i);
                if (c != '.' && !Character.isDigit(c)) {
                    ArrayList<Integer> adjacentNumbers = new ArrayList<>();
                    if (0 < row) {
                        String previousRow = inputLines.get(row - 1);
                        RowResult previousRowResult = readFromRow(previousRow, i);
                        sumPartNumbers += previousRowResult.sumPartNumbers;
                        adjacentNumbers.addAll(previousRowResult.adjacentNumbers);
                    }
                    RowResult currentRowResult = readFromRow(currentRow, i);
                    sumPartNumbers += currentRowResult.sumPartNumbers;
                    adjacentNumbers.addAll(currentRowResult.adjacentNumbers);
                    if (row < inputLines.size() - 1) {
                        String nextRow = inputLines.get(row + 1);
                        RowResult nextRowResult = readFromRow(nextRow, i);
                        sumPartNumbers += nextRowResult.sumPartNumbers;
                        adjacentNumbers.addAll(nextRowResult.adjacentNumbers);
                    }
                    if (adjacentNumbers.size() == 2) {
                        sumGearRatios += adjacentNumbers.get(0) * adjacentNumbers.get(1);
                    }
                }
            }
        }
        System.out.println("Sum of all part numbers: " + sumPartNumbers);
        System.out.println("Sum of all gear ratios: " + sumGearRatios);
    }

    private static class RowResult {
        public int sumPartNumbers = 0;
        public ArrayList<Integer> adjacentNumbers = new ArrayList<>();
    }

    private static RowResult readFromRow(String row, int readIndex) {
        RowResult rowResult = new RowResult();
        if (Character.isDigit(row.charAt(readIndex))) {
            int num = readNumberFromString(row, readIndex);
            rowResult.sumPartNumbers += num;
            rowResult.adjacentNumbers.add(num);
        } else {
            if (0 < readIndex - 1 && Character.isDigit(row.charAt(readIndex - 1))) {
                int num = readNumberFromString(row, readIndex - 1);
                rowResult.sumPartNumbers += num;
                rowResult.adjacentNumbers.add(num);
            }
            if (readIndex + 1 < row.length() && Character.isDigit(row.charAt(readIndex + 1))) {
                int num = readNumberFromString(row, readIndex + 1);
                rowResult.sumPartNumbers += num;
                rowResult.adjacentNumbers.add(num);
            }
        }
        return rowResult;
    }

    private static int readNumberFromString(String readRow, int readIndex) {
        for (; 0 < readIndex; readIndex--) {
            if (!Character.isDigit(readRow.charAt(readIndex - 1))) {
                break;
            }
        }
        StringBuilder numberBuilder = new StringBuilder();
        for (; readIndex < readRow.length(); readIndex++) {
            char c = readRow.charAt(readIndex);
            if (Character.isDigit(c)) {
                numberBuilder.append(c);
            } else {
                break;
            }
        }
        return Integer.parseInt(numberBuilder.toString());
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
