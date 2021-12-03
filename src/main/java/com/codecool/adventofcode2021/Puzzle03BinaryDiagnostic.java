package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle03BinaryDiagnostic {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Binary Diagnostic.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (int column = 0; column < inputLines.get(0).length(); column++) {
            long zeroes = 0, ones = 0;
            for (int row = 0; row < inputLines.size(); row++) {
                if(inputLines.get(row).charAt(column) == '0') {
                    zeroes++;
                }
                else {
                    ones++;
                }
            }
            if(zeroes <= ones) {
                gamma.append("1");
                epsilon.append('0');
            }
            else {
                gamma.append("0");
                epsilon.append('1');
            }
        }

        System.out.println("Puzzle 1: The power consumption of the submarine is: " + (Integer.parseInt(gamma.toString(),2)*Integer.parseInt(epsilon.toString(),2)));

        ArrayList<String> inputLines2 = new ArrayList<>(inputLines);
        int bitToCheck = 0;
        while(1 < inputLines2.size()) {
            inputLines2 = findCommonNthBit(inputLines2, bitToCheck, true);
            bitToCheck++;
        }

        int oxygen = Integer.parseInt(inputLines2.get(0),2);

        inputLines2 = new ArrayList<>(inputLines);
        bitToCheck = 0;
        while(1 < inputLines2.size()) {
            inputLines2 = findCommonNthBit(inputLines2, bitToCheck, false);
            bitToCheck++;
        }

        int CO2 = Integer.parseInt(inputLines2.get(0),2);

        System.out.println("Puzzle 2: The life support rating of the submarine is: " + (oxygen*CO2));
    }

    private static ArrayList<String> findCommonNthBit(ArrayList<String> inputLines, int bitToCheck, boolean checkForMost) {
        long zeroes = 0, ones = 0;
        for (int row = 0; row < inputLines.size(); row++) {
            if(inputLines.get(row).charAt(bitToCheck) == '0') {
                zeroes++;
            }
            else {
                ones++;
            }
        }

        int numberToKeep;
        if(checkForMost) {
            numberToKeep = 0;
            if(zeroes <= ones) {
                numberToKeep = 1;
            }
        }
        else {
            numberToKeep = 1;
            if(zeroes <= ones) {
                numberToKeep = 0;
            }
        }

        ArrayList<String> outputLines = new ArrayList<>();
        for (int row = 0; row < inputLines.size(); row++) {
            if(inputLines.get(row).charAt(bitToCheck) == (char)(numberToKeep+48)) {
                outputLines.add(inputLines.get(row));
            }
        }

        return outputLines;
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
