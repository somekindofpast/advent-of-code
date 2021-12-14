package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle14ExtendedPolymerization {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Extended Polymerization.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        String polymerTemplate = "";
        List<String> rules = new ArrayList<>();
        for (int row = 0; row < inputLines.size(); row++) {
            if(row == 0) {
                polymerTemplate = inputLines.get(row);
            } else if (1 < row) {
                rules.add(inputLines.get(row));
            }
        }

        System.out.println("Template: " + polymerTemplate);
        int numberOfSteps = 40;
        for (int step = 1; step <= numberOfSteps; step++) {

            StringBuilder newTemplate = new StringBuilder();
            for (int i = 0; i + 1 < polymerTemplate.length(); i++) {

                String pair = polymerTemplate.substring(i, i + 2);
                for (int j = 0; j < rules.size(); j++) {
                    if(pair.equals(rules.get(j).split(" ")[0])) {
                        newTemplate.append(pair.charAt(0)).append(rules.get(j).split(" ")[2]);
                        break;
                    }
                }
            }
            newTemplate.append(polymerTemplate.charAt(polymerTemplate.length()-1));
            polymerTemplate = newTemplate.toString();
            //System.out.println("After step " + step + ": " + polymerTemplate);
        }

        Map<Character, Integer> characterOccurrence = new HashMap<>();
        for (int i = 0; i < polymerTemplate.length(); i++) {
            char c = polymerTemplate.charAt(i);
            if(!characterOccurrence.containsKey(c)) {
                characterOccurrence.put(c, 1);
            } else {
                characterOccurrence.put(c, characterOccurrence.get(c) + 1);
            }
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        Map.Entry<Character, Integer> entryMin = null;
        Map.Entry<Character, Integer> entryMax = null;
        for (Map.Entry<Character, Integer> entry: characterOccurrence.entrySet()) {
            if(max < entry.getValue()) {
                max = entry.getValue();
                entryMax = entry;
            }
            if(entry.getValue() < min) {
                min = entry.getValue();
                entryMin = entry;
            }
        }

        System.out.println();
        System.out.println("Most common element: " + entryMax.getKey() + ", " + entryMax.getValue() + " occurrences.");
        System.out.println("Least common element: " + entryMin.getKey() + ", " + entryMin.getValue() + " occurrences.");
        System.out.println("Puzzle 1: The quantity of the least common element subtracted from the quantity of the most common element is: " + (max-min));
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
