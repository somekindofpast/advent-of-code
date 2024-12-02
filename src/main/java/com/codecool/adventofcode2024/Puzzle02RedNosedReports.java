package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle02RedNosedReports {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Red-Nosed Reports.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines, 1, 3);
    }

    private static void partOne(ArrayList<String> inputLines, int minChange, int maxChange) {
        int safeReportNum = 0;
        for (String line : inputLines) {
            String[] numRow = line.split("\\s+");
            boolean isIncreasing = false;
            if (numRow[0].equals(numRow[1])) {
                continue;
            }
            if (Integer.parseInt(numRow[0]) < Integer.parseInt(numRow[1])) {
                isIncreasing = true;
            }
            boolean isSafe = true;
            for (int i = 1; i < numRow.length; i++) {
                int change = Integer.parseInt(numRow[i]) - Integer.parseInt(numRow[i - 1]);
                if (        (isIncreasing && change < 0)
                        ||  (!isIncreasing && 0 < change)
                        ||  !(minChange <= Math.abs(change) && Math.abs(change) <= maxChange)
                ) {
                    isSafe = false;
                    break;
                }
            }
            if (isSafe) {
                safeReportNum++;
            }
        }
        System.out.println("The number of safe reports is: " + safeReportNum);
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
