package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle01Trebuchet {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Trebuchet.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        int sumOfValues = 0;
        for (String currentRow : inputLines) {
            Integer firstDigit = null;
            Integer lastDigit = null;
            for (int charIndex = 0; charIndex <= currentRow.length(); charIndex++) {
                char firstChar = currentRow.charAt(charIndex);
                if (firstDigit == null && Character.isDigit(firstChar)) {
                    firstDigit = Character.digit(firstChar, 10);
                }
                char lastChar = currentRow.charAt(currentRow.length() - (charIndex + 1));
                if (lastDigit == null && Character.isDigit(lastChar)) {
                    lastDigit = Character.digit(lastChar, 10);
                }
                if (firstDigit != null && lastDigit != null) {
                    sumOfValues += firstDigit * 10 + lastDigit;
                    break;
                }
            }
        }
        System.out.println("sum of values: " + sumOfValues);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        int sumOfValues = 0;
        for (String currentRow : inputLines) {
            Integer firstDigit = null;
            int firstDigitIndex = -1;
            Integer lastDigit = null;
            int lastDigitIndex = -1;
            for (int charIndex = 0; charIndex <= currentRow.length(); charIndex++) {
                if (firstDigit == null && Character.isDigit(currentRow.charAt(charIndex))) {
                    firstDigitIndex = charIndex;
                    firstDigit = Character.digit(currentRow.charAt(firstDigitIndex), 10);
                }
                if (lastDigit == null && Character.isDigit(currentRow.charAt(currentRow.length() - (charIndex + 1)))) {
                    lastDigitIndex = currentRow.length() - (charIndex + 1);
                    lastDigit = Character.digit(currentRow.charAt(lastDigitIndex), 10);
                }
                if (firstDigit != null && lastDigit != null) {
                    break;
                }
            }

            if (firstDigit == null || lastDigit == null) {
                System.out.println("There should always be at least a digit in each line!");
                return;
            }

            Integer firstDigitFromWord = null;
            int firstWordIndex = Integer.MAX_VALUE;
            Integer lastDigitFromWord = null;
            int lastWordIndex = -1;
            for (int i = 1; i <= 9; i++) {
                String numberToWord = numberToWord(i);
                int firstIndex = currentRow.indexOf(numberToWord);
                if (-1 < firstIndex && firstIndex < firstWordIndex) {
                    firstWordIndex = firstIndex;
                    firstDigitFromWord = i;
                }
                int lastIndex = currentRow.lastIndexOf(numberToWord);
                if (-1 < lastIndex && lastWordIndex < lastIndex) {
                    lastWordIndex = lastIndex;
                    lastDigitFromWord = i;
                }
            }

            if (firstDigitFromWord != null && firstWordIndex < firstDigitIndex) {
                firstDigit = firstDigitFromWord;
            }
            if (lastDigitFromWord != null && lastDigitIndex < lastWordIndex) {
                lastDigit = lastDigitFromWord;
            }

            sumOfValues += firstDigit * 10 + lastDigit;
        }
        System.out.println("sum of values: " + sumOfValues);
    }

    private static String numberToWord(int number) {
        return switch (number) {
            case 1 -> "one";
            case 2 -> "two";
            case 3 -> "three";
            case 4 -> "four";
            case 5 -> "five";
            case 6 -> "six";
            case 7 -> "seven";
            case 8 -> "eight";
            case 9 -> "nine";
            default -> "";
        };
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
