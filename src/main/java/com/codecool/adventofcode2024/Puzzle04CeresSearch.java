package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle04CeresSearch {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Ceres Search.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines, "XMAS");
        partTwo(inputLines, 'M', 'A', 'S');
    }

    private static void partOne(ArrayList<String> inputLines, String searchStr) {

        String[] vertical = new String[inputLines.get(0).length()];
        Arrays.fill(vertical, "");
        String[] diagonal1 = new String[inputLines.size() * 2 - 1];
        Arrays.fill(diagonal1, "");
        String[] diagonal2 = new String[inputLines.size() * 2 - 1];
        Arrays.fill(diagonal2, "");

        int occurrences = 0;
        int diag1Index = 0;
        int diag2Index = 0;
        for (int lineIndex = 0; lineIndex < inputLines.size(); lineIndex++) {
            String line = inputLines.get(lineIndex);

            // horizontal:
            occurrences += subStringCounter(line, searchStr);
            occurrences += subStringCounter(reverseString(line), searchStr);

            // vertical:
            for (int i = 0; i < line.length(); i++) {
                vertical[i] += line.charAt(i);
            }

            // diagonal1:
            if (vertical[0].length() == 1) {
                for (int startCol = 0; startCol < line.length(); startCol++) {
                    int col = startCol;
                    int row = 0;
                    while (col < line.length() && row < inputLines.size()) {
                        diagonal1[diag1Index] += inputLines.get(row).charAt(col);
                        col++;
                        row++;
                    }
                    diag1Index++;
                }
            } else {
                int col = 0;
                int row = lineIndex;
                while (col < line.length() && row < inputLines.size()) {
                    diagonal1[diag1Index] += inputLines.get(row).charAt(col);
                    col++;
                    row++;
                }
                diag1Index++;
            }

            // diagonal2:
            if (vertical[0].length() == 1) {
                for (int startCol = line.length() - 1; 0 <= startCol; startCol--) {
                    int col = startCol;
                    int row = 0;
                    while (0 <= col && row < inputLines.size()) {
                        diagonal2[diag2Index] += inputLines.get(row).charAt(col);
                        col--;
                        row++;
                    }
                    diag2Index++;
                }
            } else {
                int col = line.length() - 1;
                int row = lineIndex;
                while (0 <= col && row < inputLines.size()) {
                    diagonal2[diag2Index] += inputLines.get(row).charAt(col);
                    col--;
                    row++;
                }
                diag2Index++;
            }
        }
        for (String line : vertical) {
            occurrences += subStringCounter(line, searchStr);
            occurrences += subStringCounter(reverseString(line), searchStr);
        }
        for (String line : diagonal1) {
            occurrences += subStringCounter(line, searchStr);
            occurrences += subStringCounter(reverseString(line), searchStr);
        }
        for (String line : diagonal2) {
            occurrences += subStringCounter(line, searchStr);
            occurrences += subStringCounter(reverseString(line), searchStr);
        }

        System.out.println("The search string appears: " + occurrences + " times.");
    }

    private static void partTwo(ArrayList<String> inputLines, char letterStart, char letterMid, char letterEnd) {
        int occurrences = 0;
        for (int lineIndex = 1; lineIndex < inputLines.size() - 1; lineIndex++) {
            String line = inputLines.get(lineIndex);
            for (int columnIndex = 1; columnIndex < line.length() - 1; columnIndex++) {

                if (inputLines.get(lineIndex).charAt(columnIndex) == letterMid) {

                    boolean firstMatch = false;
                    if (inputLines.get(lineIndex - 1).charAt(columnIndex - 1) == letterStart &&
                            inputLines.get(lineIndex + 1).charAt(columnIndex + 1) == letterEnd) {
                        firstMatch = true;
                    } else if (inputLines.get(lineIndex - 1).charAt(columnIndex - 1) == letterEnd &&
                            inputLines.get(lineIndex + 1).charAt(columnIndex + 1) == letterStart) {
                        firstMatch = true;
                    }

                    if (firstMatch) {

                        if (inputLines.get(lineIndex - 1).charAt(columnIndex + 1) == letterStart &&
                                inputLines.get(lineIndex + 1).charAt(columnIndex - 1) == letterEnd) {
                            occurrences++;
                        } else if (inputLines.get(lineIndex - 1).charAt(columnIndex + 1) == letterEnd &&
                                inputLines.get(lineIndex + 1).charAt(columnIndex - 1) == letterStart) {
                            occurrences++;
                        }

                    }
                }
            }
        }
        System.out.println("The cross shaped pattern appears: " + occurrences + " times.");
    }

    private static int subStringCounter(String inputStr, String subStr) {
        int occurrences = 0;
        int index = 0;
        while ((index = inputStr.indexOf(subStr, index)) != -1) {
            occurrences++;
            index++;
        }
        return occurrences;
    }

    private static String reverseString(String inputStr) {
        StringBuilder reverse = new StringBuilder();
        int index = inputStr.length() - 1;
        while (0 <= index) {
            reverse.append(inputStr.charAt(index));
            index--;
        }
        return reverse.toString();
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
