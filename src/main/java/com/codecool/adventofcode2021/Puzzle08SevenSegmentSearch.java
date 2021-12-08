package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle08SevenSegmentSearch {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Seven Segment Search.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");


        int digitCounter = 0;
        for (int i = 0; i < inputLines.size(); i++) {
            String[] digits = inputLines.get(i).split(" \\| ")[1].split(" ");
            for (String digitString : digits) {
                if(digitString.length() == 2 || digitString.length() == 3 || digitString.length() == 4 || digitString.length() == 7)
                    digitCounter++;
            }
        }
        System.out.println("Puzzle 1: digits 1, 4, 7, or 8 appear: " + digitCounter + " times.");
    }

    private static boolean stringConsistsOfSameCharacters(String toCompare, String characters) {
        if(toCompare.length() != characters.length())
            return false;

        char[] charArray = characters.toCharArray();

        for (char c : charArray) {
            boolean containsCharacter = false;
            for (int i = 0; i < toCompare.length(); i++) {
                if(toCompare.charAt(i) == c) {
                    containsCharacter = true;
                    break;
                }
            }
            if (!containsCharacter)
                return false;
        }
        return true;
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
