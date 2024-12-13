package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle08ResonantCollinearity {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Resonant Collinearity.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        int antiNodeNum = 0;
        Map<Character, List<RowCol>> antennas = new HashMap<>();
        char[][] grid = new char[inputLines.size()][inputLines.get(0).length()];
        for (int row = 0; row < inputLines.size(); row++) {
            for (int col = 0; col < inputLines.get(row).length(); col++) {
                char c = inputLines.get(row).charAt(col);
                grid[row][col] = c;
                if (c != '.') {
                    List<RowCol> coords = antennas.getOrDefault(c, new ArrayList<>());
                    coords.add(new RowCol(row, col));
                    antennas.put(c, coords);
                }
            }
        }
        for (List<RowCol> coords : antennas.values()) {
            if (coords.size() < 2) {
                continue;
            }
            for (int i = 0; i < coords.size() - 1; i++) {
                for (int j = i + 1; j < coords.size(); j++) {
                    int diffX = coords.get(i).col - coords.get(j).col;
                    int diffY = coords.get(i).row - coords.get(j).row;
                    RowCol rowCol = new RowCol(coords.get(i).row + diffY, coords.get(i).col + diffX);
                    if (0 <= rowCol.row && rowCol.row < grid.length && 0 <= rowCol.col && rowCol.col < grid[0].length) {
                        grid[rowCol.row][rowCol.col] = '#';
                    }
                    rowCol = new RowCol(coords.get(j).row - diffY, coords.get(j).col - diffX);
                    if (0 <= rowCol.row && rowCol.row < grid.length && 0 <= rowCol.col && rowCol.col < grid[0].length) {
                        grid[rowCol.row][rowCol.col] = '#';
                    }
                }
            }
        }
        for (char[] row : grid) {
            System.out.println(row);
            for (char c : row) {
                if (c == '#') {
                    antiNodeNum++;
                }
            }
        }
        System.out.println("The antinode number is: " + antiNodeNum);
        System.out.println();
    }

    private static void partTwo(ArrayList<String> inputLines) {
        int antiNodeNum = 0;
        Map<Character, List<RowCol>> antennas = new HashMap<>();
        char[][] grid = new char[inputLines.size()][inputLines.get(0).length()];
        for (int row = 0; row < inputLines.size(); row++) {
            for (int col = 0; col < inputLines.get(row).length(); col++) {
                char c = inputLines.get(row).charAt(col);
                grid[row][col] = c;
                if (c != '.') {
                    List<RowCol> coords = antennas.getOrDefault(c, new ArrayList<>());
                    coords.add(new RowCol(row, col));
                    antennas.put(c, coords);
                }
            }
        }
        for (List<RowCol> coords : antennas.values()) {
            if (coords.size() < 2) {
                continue;
            }
            for (int i = 0; i < coords.size() - 1; i++) {
                for (int j = i + 1; j < coords.size(); j++) {
                    int diffX = coords.get(i).col - coords.get(j).col;
                    int diffY = coords.get(i).row - coords.get(j).row;
                    RowCol rowCol = new RowCol(coords.get(i).row + diffY, coords.get(i).col + diffX);
                    while (0 <= rowCol.row && rowCol.row < grid.length && 0 <= rowCol.col && rowCol.col < grid[0].length) {
                        grid[rowCol.row][rowCol.col] = '#';
                        rowCol = new RowCol(rowCol.row + diffY, rowCol.col + diffX);
                    }
                    rowCol = new RowCol(coords.get(j).row - diffY, coords.get(j).col - diffX);
                    while (0 <= rowCol.row && rowCol.row < grid.length && 0 <= rowCol.col && rowCol.col < grid[0].length) {
                        grid[rowCol.row][rowCol.col] = '#';
                        rowCol = new RowCol(rowCol.row - diffY, rowCol.col - diffX);
                    }
                }
            }
        }
        for (char[] row : grid) {
            System.out.println(row);
            for (char c : row) {
                if (c != '.') {
                    antiNodeNum++;
                }
            }
        }
        System.out.println("The resonant harmonics antinode number is: " + antiNodeNum);
        System.out.println();
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

    private static class RowCol {
        public int row;
        public int col;

        RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public String toString() {
            return row + ":" + col;
        }
    }
}


