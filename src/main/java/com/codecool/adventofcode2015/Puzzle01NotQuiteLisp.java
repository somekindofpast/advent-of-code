package com.codecool.adventofcode2015;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle01NotQuiteLisp {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2015/Not Quite Lisp.txt")));
        //ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(new String[]{"()()(())(()())"}));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");
        else {
            System.out.println("Santa arrives on floor: " + parenthesesCounter(inputLines.get(0), false));
            System.out.println("The first character that causes him to enter the basement: " + parenthesesCounter(inputLines.get(0), true));
        }
    }

    public static int parenthesesCounter(String inputStr, boolean returnBasementIndex) {
        int floorNumber = 0;

        for (int i = 0; i < inputStr.length(); i++) {
            if(inputStr.charAt(i) == '(')
                floorNumber++;
            else
                floorNumber--;

            if(returnBasementIndex && floorNumber == -1)
                return i+1;
        }

        return floorNumber;
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
