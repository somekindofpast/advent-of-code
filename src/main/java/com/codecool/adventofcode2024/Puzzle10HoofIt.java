package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle10HoofIt {
    static int MAX_HEIGHT = 9;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Hoof It.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        int[][] grid = new int[inputLines.size()][inputLines.get(0).length()];
        Map<RowCol, Set<RowCol>> trailHeads = new HashMap<>();
        for (int row = 0; row < inputLines.size(); row++) {
            for (int col = 0; col < inputLines.get(row).length(); col++) {
                int num = Integer.parseInt("" + inputLines.get(row).charAt(col));
                grid[row][col] = num;
                if (num == 0) {
                    RowCol key = new RowCol(row, col);
                    trailHeads.put(key, trailHeads.getOrDefault(key, new HashSet<>()));
                }
            }
            //System.out.println(Arrays.toString(grid[row]));
        }
        long trailHeadScore = 0;
        for (Map.Entry<RowCol, Set<RowCol>> entry : trailHeads.entrySet()) {
            Stack<RowCol> neighbours = new Stack<>();
            neighbours.push(entry.getKey());
            while (!neighbours.empty()) {
                RowCol cell = neighbours.pop();
                if (grid[cell.row][cell.col] == MAX_HEIGHT) {
                    entry.getValue().add(cell);
                    continue;
                }
                for (RowCol neighbour : getValidNeighbours(cell, grid)) {
                    neighbours.push(neighbour);
                }
            }
            //System.out.println(entry + " (" + entry.getValue().size() + ")");
            trailHeadScore += entry.getValue().size();
        }
        System.out.println("The sum of the scores of all trailheads is: " + trailHeadScore);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        int[][] grid = new int[inputLines.size()][inputLines.get(0).length()];
        Map<RowCol, List<RowCol>> trailHeads = new HashMap<>();
        for (int row = 0; row < inputLines.size(); row++) {
            for (int col = 0; col < inputLines.get(row).length(); col++) {
                int num = Integer.parseInt("" + inputLines.get(row).charAt(col));
                grid[row][col] = num;
                if (num == 0) {
                    RowCol key = new RowCol(row, col);
                    trailHeads.put(key, trailHeads.getOrDefault(key, new ArrayList<>()));
                }
            }
            //System.out.println(Arrays.toString(grid[row]));
        }
        long trailHeadRating = 0;
        for (Map.Entry<RowCol, List<RowCol>> entry : trailHeads.entrySet()) {
            Stack<RowCol> neighbours = new Stack<>();
            neighbours.push(entry.getKey());
            while (!neighbours.empty()) {
                RowCol cell = neighbours.pop();
                if (grid[cell.row][cell.col] == MAX_HEIGHT) {
                    entry.getValue().add(cell);
                    continue;
                }
                for (RowCol neighbour : getValidNeighbours(cell, grid)) {
                    neighbours.push(neighbour);
                }
            }
            //System.out.println(entry + " (" + entry.getValue().size() + ")");
            trailHeadRating += entry.getValue().size();
        }
        System.out.println("The sum of the ratings of all trailheads is: " + trailHeadRating);
    }

    private static class RowCol {
        public int row;
        public int col;

        RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return row + ":" + col;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + row;
            hash = 31 * hash + col;
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            RowCol cell = (RowCol) o;
            return row == cell.row && col == cell.col;
        }
    }

    private static List<RowCol> getValidNeighbours(RowCol cell, int[][] grid) {
        int targetValue = grid[cell.row][cell.col] + 1;
        List<RowCol> neighbours = new ArrayList<>();
        if (0 < cell.row && grid[cell.row - 1][cell.col] == targetValue) {
            neighbours.add(new RowCol(cell.row - 1, cell.col));
        }
        if (cell.row + 1 < grid.length && grid[cell.row + 1][cell.col] == targetValue) {
            neighbours.add(new RowCol(cell.row + 1, cell.col));
        }
        if (0 < cell.col && grid[cell.row][cell.col - 1] == targetValue) {
            neighbours.add(new RowCol(cell.row, cell.col - 1));
        }
        if (cell.col + 1 < grid[cell.row].length && grid[cell.row][cell.col + 1] == targetValue) {
            neighbours.add(new RowCol(cell.row, cell.col + 1));
        }
        return neighbours;
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
