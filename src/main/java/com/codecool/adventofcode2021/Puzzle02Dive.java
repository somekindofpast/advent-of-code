package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle02Dive {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Dive.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        long horizontalPosition = 0, depth = 0;
        for (String line : inputLines) {
            String command = line.split(" ")[0];
            long value = Long.parseLong(line.split(" ")[1]);
            if(command.equals("forward")) {
                horizontalPosition += value;
            }
            else if(command.equals("up")) {
                depth -= value;
            }
            else {
                depth += value;
            }
        }
        System.out.println("Puzzle 1: Multiplying the final horizontal position by the final depth gives: " + (horizontalPosition*depth));

        long aim = 0;
        horizontalPosition = 0;
        depth = 0;
        for (String line : inputLines) {
            String command = line.split(" ")[0];
            long value = Long.parseLong(line.split(" ")[1]);
            if(command.equals("forward")) {
                horizontalPosition += value;
                depth += aim*value;
            }
            else if(command.equals("up")) {
                aim -= value;
            }
            else {
                aim += value;
            }
        }
        System.out.println("Puzzle 2: Multiplying the final horizontal position by the final depth gives: " + (horizontalPosition*depth));
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
