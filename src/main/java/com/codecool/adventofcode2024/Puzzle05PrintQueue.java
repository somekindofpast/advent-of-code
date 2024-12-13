package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle05PrintQueue {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Print Queue.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        List<Pair> rules = new ArrayList<>();
        boolean isRulesRead = false;
        int midPageSum = 0;
        for (String line : inputLines) {
            if (line.equals("")) {
                isRulesRead = true;
                continue;
            }
            if (!isRulesRead) {
                rules.add(new Pair(Integer.parseInt(line.split("\\|")[0]), Integer.parseInt(line.split("\\|")[1])));
                continue;
            }

            Map<Integer, Integer> numOrder = new HashMap<>();
            String[] nums = line.split(",");
            int midNum = 0;
            for (int i = 0; i < nums.length; i++) {
                int num = Integer.parseInt(nums[i]);
                numOrder.put(num, i);
                if (i == nums.length / 2) {
                    midNum = num;
                }
            }

            boolean passed = true;
            for (Pair rule : rules) {
                int left = numOrder.getOrDefault(rule.left, -1);
                int right = numOrder.getOrDefault(rule.right, -1);
                if (left < 0 || right < 0) {
                    continue;
                }
                if (right <= left) {
                    passed = false;
                    break;
                }
            }
            if (passed) {
                midPageSum += midNum;
            }
        }
        System.out.println("The sum of the middle page numbers from the correctly-ordered updates is: " + midPageSum);
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

    private static class Pair {
        public int left;
        public int right;

        Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return left + "|" + right;
        }
    }
}


