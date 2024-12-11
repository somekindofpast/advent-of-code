package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Puzzle11PlutonianPebbles {
    static int FIRST_REPETITION = 25;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Plutonian Pebbles.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        Queue<Long> nums = Arrays.stream(inputLines.get(0).split("\\s+"))
                .map(Long::parseLong)
                .collect(Collectors.toCollection(LinkedList::new));

        for (int i = 0; i < FIRST_REPETITION; i++) {
            Queue<Long> newNums = new LinkedList<>();
            for (Long num : nums) {
                String numStr = "" + num;
                if (num == 0) {
                    newNums.add(num + 1);
                } else if (numStr.length() % 2 == 0) {
                    newNums.add(Long.parseLong(numStr.substring(0, numStr.length() / 2)));
                    newNums.add(Long.parseLong(numStr.substring(numStr.length() / 2)));
                } else {
                    newNums.add(num * 2024);
                }
            }
            nums = newNums;
            //System.out.println("After " + (i + 1) + " blink(s):");
            //System.out.println(nums);
        }

        System.out.println("The number of stones after " + FIRST_REPETITION + " blinks is: " + nums.size());
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
