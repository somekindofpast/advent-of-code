package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle11DumboOctopus {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Dumbo Octopus.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        System.out.println("Before any steps:");
        List<Octopus[]> octopuses = new ArrayList<>();
        for (int row = 0; row < inputLines.size(); row++) {
            octopuses.add(new Octopus[inputLines.get(row).length()]);
            for (int column = 0; column < inputLines.get(row).length(); column++) {
                octopuses.get(row)[column] = new Octopus(Integer.parseInt(String.valueOf(inputLines.get(row).charAt(column))));
            }
            System.out.println(Arrays.toString(octopuses.get(row)));
        }

        int totalFlashes = 0;
        int flashesUntilStepNumber = 0;
        int stepNumber = 0;
        int stepMax = 100;
        int firstFullGridFlash = -1;
        boolean isFirstFullGridFlashFound = false;
        for (stepNumber = 0; !isFirstFullGridFlashFound; stepNumber++) {
            for (int row = 0; row < octopuses.size(); row++) {
                for (int column = 0; column < octopuses.get(row).length; column++) {
                    increaseEnergy(octopuses, row, column);
                }
            }
            System.out.println();
            System.out.println("After step " + (stepNumber+1) + ":");
            for (int row = 0; row < octopuses.size(); row++) {
                System.out.println(Arrays.toString(octopuses.get(row)));
            }
            int octopusFlashes = 0;
            for (int row = 0; row < octopuses.size(); row++) {
                for (int column = 0; column < octopuses.get(row).length; column++) {
                    if(octopuses.get(row)[column].flashed) {
                        totalFlashes++;
                        octopusFlashes++;
                        if(stepNumber < stepMax)
                            flashesUntilStepNumber++;
                        octopuses.get(row)[column].flashed = false;
                    }
                }
            }
            if(!isFirstFullGridFlashFound && octopusFlashes == octopuses.size() * octopuses.get(0).length) {
                isFirstFullGridFlashFound = true;
                firstFullGridFlash = stepNumber+1;
            }
        }
        System.out.println("Puzzle 1: After step " + stepMax + " there have been a total of " + flashesUntilStepNumber + " flashes.");
        System.out.println("Puzzle 2: The first step during which all octopuses flash is " + firstFullGridFlash);
    }

    private static class Octopus {
        int energy;
        boolean flashed;

        public Octopus(int energy) {
            this.energy = energy;
            this.flashed = false;
        }

        @Override
        public String toString() {
            return energy + "";
        }
    }

    private static void increaseEnergy(List<Octopus[]> octopuses, int row, int column) {
        if(!octopuses.get(row)[column].flashed)
            octopuses.get(row)[column].energy++;
        if(9 < octopuses.get(row)[column].energy) {
            octopuses.get(row)[column].energy = 0;
            octopuses.get(row)[column].flashed = true;
            for (int rowNeighbour = row - 1; rowNeighbour <= row + 1; rowNeighbour++) {
                if(rowNeighbour < 0 || octopuses.size() <= rowNeighbour)
                    continue;
                for (int columnNeighbour = column - 1; columnNeighbour <= column + 1; columnNeighbour++) {
                    if(columnNeighbour < 0 || octopuses.get(row).length <= columnNeighbour)
                        continue;
                    if(row == rowNeighbour && column == columnNeighbour)
                        continue;
                    increaseEnergy(octopuses, rowNeighbour, columnNeighbour);
                }
            }
        }
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
