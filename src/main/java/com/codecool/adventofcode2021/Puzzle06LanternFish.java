package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle06LanternFish {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Lanternfish.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        String[] input = inputLines.get(0).split(",");
        /*List<Integer> lanternFish = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            lanternFish.add(Integer.parseInt(input[i]));
        }

        int dayMax = 85;
        for (int day = 0; day <= dayMax; day++) {
            if(day == 80 && 80 < dayMax)
                System.out.println("Puzzle 1: number of lanternfish after 80 days: " + lanternFish.size());

            int n = lanternFish.size();
            for (int i = 0; i < n; i++) {
                if(day == dayMax)
                    continue;
                if(lanternFish.get(i) == 0) {
                    lanternFish.set(i, 6);
                    lanternFish.add(8);
                } else {
                    lanternFish.set(i, lanternFish.get(i)-1);
                }
            }
        }
        System.out.println("Puzzle 2: number of lanternfish after " + dayMax + " days: " + lanternFish.size());*/



        List<List<Integer>> lanternFish = new ArrayList<>();
        lanternFish.add(new ArrayList<>());

        for (int i = 0; i < input.length; i++) {
            lanternFish.get(0).add(Integer.parseInt(input[i]));
        }

        int dayMax = 150;
        for (int day = 0; day <= dayMax; day++) {
            if(day == 80 && 80 < dayMax)
                System.out.println("Puzzle 1: number of lanternfish after 80 days: " + lanternFish.get(0).size());

            long lanternFishNumber = 0;
            List<Integer> listSizes = new ArrayList<>();
            for (int listNumber = 0; listNumber < lanternFish.size(); listNumber++) {
                lanternFishNumber += lanternFish.get(listNumber).size();
                listSizes.add(lanternFish.get(listNumber).size());
            }
            System.out.println("day " + day + " fish number: " + lanternFishNumber);

            int arrayMax = lanternFish.size();
            for (int listNumber = 0; listNumber < arrayMax; listNumber++) {
                int n = listSizes.get(listNumber);
                for (int i = 0; i < n; i++) {
                    if(day == dayMax)
                        continue;
                    if(lanternFish.get(listNumber).get(i) == 0) {
                        lanternFish.get(listNumber).set(i, 6);
                        if(100000000 < lanternFish.get(lanternFish.size()-1).size()) {
                            lanternFish.add(new ArrayList<>());
                        }
                        lanternFish.get(lanternFish.size()-1).add(8);
                    } else {
                        lanternFish.get(listNumber).set(i, lanternFish.get(listNumber).get(i)-1);
                    }
                }
            }
            System.out.println("list size: " + lanternFish.size() + ", day: " + day);
        }

        long lanternFishNumber = 0;
        for (int listNumber = 0; listNumber < lanternFish.size(); listNumber++) {
            lanternFishNumber += lanternFish.get(listNumber).size();
        }

        System.out.println("Puzzle 2: number of lanternfish after " + dayMax + " days: " + lanternFishNumber);
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
