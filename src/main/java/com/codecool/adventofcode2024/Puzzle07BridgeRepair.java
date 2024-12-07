package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle07BridgeRepair {
    static char ADD_CHAR = '+';
    static char MULT_CHAR = '*';

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Bridge Repair.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        long calibrationResult = 0L;
        for (String line : inputLines) {
            long testVal = Long.parseLong(line.split(":")[0]);
            String[] numStrings = line.split(": ")[1].split("\\s+");
            long[] nums = new long[numStrings.length];
            for (int i = 0; i < numStrings.length; i++) {
                nums[i] = Long.parseLong(numStrings[i].strip());
            }

            long operations = (long) Math.pow(2, nums.length - 1);

            for (long i = 0; i < operations; i++) {
                char[] operators = decToBinaryArray(i, nums.length - 1);
                long result = nums[0];
                for (int j = 1; j < nums.length; j++) {
                    if (operators[j - 1] == ADD_CHAR) {
                        result += nums[j];
                    } else {
                        result *= nums[j];
                    }
                }
                if (result == testVal) {
                    calibrationResult += testVal;
                    break;
                }
            }
        }
        System.out.println("The total calibration result is: " + calibrationResult);
    }

    private static char[] decToBinaryArray(long dec, int length) {
        char[] binArray = new char[length];
        Arrays.fill(binArray, ADD_CHAR);
        int i = 0;
        while (0 < dec) {
            binArray[i] = dec % 2 == 0 ? ADD_CHAR : MULT_CHAR;
            dec /= 2;
            i++;
        }
        return binArray;
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
