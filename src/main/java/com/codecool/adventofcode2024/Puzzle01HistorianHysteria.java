package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle01HistorianHysteria {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Historian Hysteria.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        List<Integer> leftSide = new ArrayList<>();
        List<Integer> rightSide = new ArrayList<>();
        for (String line : inputLines) {
            leftSide.add(Integer.parseInt(line.split("\\s+")[0]));
            rightSide.add(Integer.parseInt(line.split("\\s+")[1]));
        }
        Collections.sort(leftSide);
        Collections.sort(rightSide);

        int distance = 0;
        for (int i = 0; i < leftSide.size(); i++) {
            distance += Math.abs(leftSide.get(i) - rightSide.get(i));
        }
        System.out.println("The total distance between the lists is: " + distance);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        List<Integer> leftSide = new ArrayList<>();
        Map<Integer ,Integer> rightSide = new HashMap<>();
        for (String line : inputLines) {
            leftSide.add(Integer.parseInt(line.split("\\s+")[0]));
            Integer key = Integer.parseInt(line.split("\\s+")[1]);
            rightSide.put(key, rightSide.getOrDefault(key, 0) + 1);
        }
        int similarity = 0;
        for (Integer leftNum : leftSide) {
            similarity += leftNum * rightSide.getOrDefault(leftNum, 0);
        }
        System.out.println("The similarity score is: " + similarity);
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
