package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle19LinenLayout {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Linen Layout.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        List<String> allPatterns = new ArrayList<>();
        int possibleDesigns = 0;
        boolean isPatternsDone = false;
        for (String line : inputLines) {
            if (line.strip().length() == 0) {
                isPatternsDone = true;
                continue;
            }
            if (!isPatternsDone) {
                allPatterns.addAll(List.of(line.split(", ")));
                continue;
            }

            List<String> patterns = new ArrayList<>();
            for (String pattern : allPatterns) {
                if (line.contains(pattern)) {
                    patterns.add(pattern);
                }
            }

            Deque<String> deque = new ArrayDeque<>();
            deque.addLast(line);
            boolean isPossible = false;
            while (!deque.isEmpty()) {
                String part = deque.removeFirst();
                for (String pattern : patterns) {
                    if (part.startsWith(pattern)) {
                        if (part.length() == pattern.length()) {
                            isPossible = true;
                            break;
                        }
                        deque.addLast(part.substring(pattern.length()));
                    }
                }
                if (isPossible) {
                    possibleDesigns++;
                    System.out.println(line + " is possible");
                    break;
                }
            }
        }
        System.out.println("The number of possible designs is: " + possibleDesigns);
    }

    private static void partTwo(ArrayList<String> inputLines) {
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
