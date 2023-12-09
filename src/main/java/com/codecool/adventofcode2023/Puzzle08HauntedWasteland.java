package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle08HauntedWasteland {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Haunted Wasteland.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        String instructions = inputLines.get(0);
        int currentInstruction = 0;
        Map<String, String[]> network = new HashMap<>();
        String element = "AAA";
        String left = "";
        String right = "";
        for (int row = 2; row < inputLines.size(); row++) {
            String currentRow = inputLines.get(row);
            String key = currentRow.split(" = \\(")[0];
            String value1 = currentRow.split(" = \\(")[1].split("\\)")[0].split(", ")[0];
            String value2 = currentRow.split(" = \\(")[1].split("\\)")[0].split(", ")[1];
            network.put(key, new String[]{ value1, value2 });
            if (key.equals("AAA")) {
                left = value1;
                right = value2;
            }
        }

        long totalSteps = 0;
        while (!element.equals("ZZZ")) {
            char instruction = instructions.charAt(currentInstruction);
            element = instruction == 'L' ? left : right;
            String[] next = network.get(element);
            left = next[0];
            right = next[1];
            totalSteps++;
            currentInstruction = currentInstruction < instructions.length() - 1 ? currentInstruction + 1 : 0;
        }

        System.out.println("total steps: " + totalSteps);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        String instructions = inputLines.get(0);
        int currentInstruction = 0;
        Map<String, String[]> network = new HashMap<>();
        ArrayList<Map.Entry<String, String[]>> entries = new ArrayList<>();
        for (int row = 2; row < inputLines.size(); row++) {
            String currentRow = inputLines.get(row);
            String key = currentRow.split(" = \\(")[0];
            String value1 = currentRow.split(" = \\(")[1].split("\\)")[0].split(", ")[0];
            String value2 = currentRow.split(" = \\(")[1].split("\\)")[0].split(", ")[1];
            network.put(key, new String[]{ value1, value2 });
            if (key.charAt(2) == 'A') {
                entries.add(Map.entry(key, new String[]{ value1, value2 }));
            }
        }

        long totalSteps = 0;
        boolean done = false;
        while (!done) {
            char instruction = instructions.charAt(currentInstruction);
            ArrayList<Map.Entry<String, String[]>> newEntries = new ArrayList<>();
            done = true;
            for (Map.Entry<String, String[]> entry : entries) {
                String left = entry.getValue()[0];
                String right = entry.getValue()[1];
                String element = instruction == 'L' ? left : right;
                String[] next = network.get(element);
                newEntries.add(Map.entry(element, new String[]{ next[0], next[1] }));
                if (element.charAt(2) != 'Z') {
                    done = false;
                }
            }
            entries = newEntries;
            totalSteps++;
            currentInstruction = currentInstruction < instructions.length() - 1 ? currentInstruction + 1 : 0;
        }

        System.out.println("total steps: " + totalSteps);
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
