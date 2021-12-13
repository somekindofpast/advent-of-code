package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle09SmokeBasin {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Smoke Basin Example.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        List<Integer[]> lowPointIndexes = new ArrayList<>();
        int sumRiskLevels = 0;
        for (int row = 0; row < inputLines.size(); row++) {
            for (int column = 0; column < inputLines.get(row).length(); column++) {
                int center = inputLines.get(row).charAt(column) - 48;
                int neighbour;
                int sameNeighbour = 0;
                if(0 <= row - 1) {
                    neighbour = inputLines.get(row - 1).charAt(column) - 48;
                    if(neighbour < center)
                        continue;
                    if(neighbour == center)
                        sameNeighbour++;
                }
                if(column + 1 < inputLines.get(row).length()) {
                    neighbour = inputLines.get(row).charAt(column + 1) - 48;
                    if(neighbour < center)
                        continue;
                    if(neighbour == center)
                        sameNeighbour++;
                }
                if(row + 1 < inputLines.size()) {
                    neighbour = inputLines.get(row + 1).charAt(column) - 48;
                    if(neighbour < center)
                        continue;
                    if(neighbour == center)
                        sameNeighbour++;
                }
                if(0 <= column - 1) {
                    neighbour = inputLines.get(row).charAt(column - 1) - 48;
                    if(neighbour < center)
                        continue;
                    if(neighbour == center)
                        sameNeighbour++;
                }

                if(0 < sameNeighbour)
                    continue;

                sumRiskLevels += 1 + center;
                lowPointIndexes.add(new Integer[]{row,column});
            }
        }
        System.out.println("Puzzle 1: the sum of the risk levels of all low points on the heightmap is: " + sumRiskLevels);

        /*List<Set<Integer>> basins = new ArrayList<>();

        for (Integer[] lowPointIndex : lowPointIndexes) {
            basins.add(new ArrayList<>());
            int row = lowPointIndex[0], column = lowPointIndex[1];

            int lowPoint = inputLines.get(row).charAt(column) - 48;
            basins.get(basins.size()-1).add(lowPoint);

            int last = lowPoint;
            while(0 <= row - 1) {

                int neighbour = inputLines.get(row - 1).charAt(column) - 48;
                if(neighbour == last - 1){
                    basins.get(basins.size()-1).add(neighbour);
                }

                row--;
                last = basins.get(basins.size()-1).get(basins.get(basins.size()-1).size()-1);
            }
        }

        System.out.println(basins);*/
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
