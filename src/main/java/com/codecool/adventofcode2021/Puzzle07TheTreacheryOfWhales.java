package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle07TheTreacheryOfWhales {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/The Treachery of Whales.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        String[] input = inputLines.get(0).split(",");
        int minPosition = Integer.MAX_VALUE;
        int maxPosition = Integer.MIN_VALUE;
        List<Integer> crabsInit = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            crabsInit.add(Integer.parseInt(input[i]));
            if(crabsInit.get(i) < minPosition)
                minPosition = crabsInit.get(i);
            if(maxPosition < crabsInit.get(i))
                maxPosition = crabsInit.get(i);
        }

        List<Integer> crabs;
        int minFuelCostVersion1 = Integer.MAX_VALUE;
        int minFuelCostVersion2 = Integer.MAX_VALUE;
        for (int position = minPosition; position <= maxPosition; position++) {
            crabs = new ArrayList<>(crabsInit);
            int fuelCostVersion1 = costToArrangeNumbersToPositionVersion1(position, crabs);
            int fuelCostVersion2 = costToArrangeNumbersToPositionVersion2(position, crabs);
            if(fuelCostVersion1 < minFuelCostVersion1)
                minFuelCostVersion1 = fuelCostVersion1;
            if(fuelCostVersion2 < minFuelCostVersion2)
                minFuelCostVersion2 = fuelCostVersion2;
        }
        System.out.println("Puzzle 1: The amount of fuel they must spend to align to that position: " + minFuelCostVersion1);
        System.out.println("Puzzle 2: The amount of fuel they must spend to align to that position: " + minFuelCostVersion2);
    }

    private static int costToArrangeNumbersToPositionVersion1(int position, List<Integer> numbers) {
        int fuelCost = 0;
        for (int i = 0; i < numbers.size(); i++) {
            fuelCost += Math.abs(position - numbers.get(i));
        }
        return fuelCost;
    }

    private static int costToArrangeNumbersToPositionVersion2(int position, List<Integer> numbers) {
        int fuelCost = 0;
        for (int i = 0; i < numbers.size(); i++) {
            int endPosition = Math.abs(position - numbers.get(i));
            for (int j = 0; j <= endPosition; j++) {
                fuelCost += j;
            }
        }
        return fuelCost;
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
