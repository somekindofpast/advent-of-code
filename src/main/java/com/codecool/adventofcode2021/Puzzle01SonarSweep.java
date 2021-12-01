package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle01SonarSweep {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Sonar Sweep.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        int previousNumber = Integer.parseInt(inputLines.get(0));
        int increases = 0;
        for (int i = 1; i < inputLines.size(); i++) {
            int number = Integer.parseInt(inputLines.get(i));
            if(previousNumber < number) {
                increases++;
            }
            previousNumber = number;
        }
        System.out.println("Measurements larger than the previous measurement: " + increases);

        //inputLines = new ArrayList<>(Arrays.asList(new String[]{"199", "200", "208", "210", "200", "207", "240", "269", "260", "263"}));
        int sumPrevious = Integer.parseInt(inputLines.get(0)) + Integer.parseInt(inputLines.get(1)) + Integer.parseInt(inputLines.get(2));
        increases = 0;
        for (int i = 3; i < inputLines.size(); i++) {
            int sum = Integer.parseInt(inputLines.get(i-2)) + Integer.parseInt(inputLines.get(i-1)) + Integer.parseInt(inputLines.get(i));
            if(sumPrevious < sum) {
                increases++;
            }

            sumPrevious = sum;
        }
        System.out.println("Sum measurements larger than the previous measurement: " + increases);
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
