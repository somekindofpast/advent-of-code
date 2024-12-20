package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle20RaceCondition {
    private static final char EMPTY = '.';
    private static final char WALL = '#';
    private static final char START = 'S';
    private static final char END = 'E';
    private static final char STEP = 'O';

    private static int UNEXPLORED = -1;
    private static int NEVER_REACHABLE = -999;

    private static Pair[][] grid;
    private static RowCol start, end;

    private static int bypassWallsStep = NEVER_REACHABLE;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Race Condition.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        bypassWallsStep = NEVER_REACHABLE;
        initGrid(inputLines);
        calcRoute();

        /*for (Pair[] gridRows : grid) {
            for (Pair gridRow : gridRows) {
                System.out.print(gridRow.c + " ");
                //System.out.print(gridRow.n + " ");
            }
            System.out.println();
        }*/

        int maxSteps = grid[end.row][end.col].n;
        System.out.println("Maximum steps to complete the maze: " + maxSteps);

        Map<Integer, Integer> timeSavers = new HashMap<>();
        int saves100PlusPicoSeconds = 0;
        for (bypassWallsStep = 0; bypassWallsStep < maxSteps; bypassWallsStep++) {
            initGrid(inputLines);
            calcRoute();
            int stepsToReachEnd = grid[end.row][end.col].n;
            if (stepsToReachEnd < maxSteps) {
                int picoseconds = maxSteps - stepsToReachEnd;
                timeSavers.put(picoseconds, timeSavers.getOrDefault(picoseconds, 0) + 1);
                if (100 <= picoseconds) {
                    saves100PlusPicoSeconds++;
                }
            }
        }
        for (Map.Entry<Integer, Integer> entry : timeSavers.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println("There is one cheat that saves " + entry.getKey() + " picoseconds");
            } else {
                System.out.println("There are " + entry.getValue() + " cheats that save " + entry.getKey() + " picoseconds");
            }
        }
        System.out.println("The number of cheats that save at least 100 picoseconds is: " + saves100PlusPicoSeconds);
    }

    private static void partTwo(ArrayList<String> inputLines) {
    }

    private static void initGrid(ArrayList<String> inputLines) {
        grid = new Pair[inputLines.size()][inputLines.get(0).length()];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                char c = inputLines.get(row).charAt(col);
                grid[row][col] = new Pair(c, UNEXPLORED);
                if (c == START) {
                    start = new RowCol(row, col);
                    grid[row][col].n = 0;
                } else if (c == END) {
                    end = new RowCol(row, col);
                }
            }
        }
    }

    private static void calcRoute() {
        Deque<RowCol> deque = new ArrayDeque<>();
        deque.addLast(start);
        while (!deque.isEmpty()) {
            RowCol cell = deque.removeFirst();
            if (grid[cell.row][cell.col].c == END) {
                break;
            }
            if (0 < cell.row && step(cell, grid[cell.row - 1][cell.col])) {
                deque.addLast(new RowCol(cell.row - 1, cell.col));
            }
            if (cell.row < grid.length - 1 && step(cell, grid[cell.row + 1][cell.col])) {
                deque.addLast(new RowCol(cell.row + 1, cell.col));
            }
            if (0 < cell.col && step(cell, grid[cell.row][cell.col - 1])) {
                deque.addLast(new RowCol(cell.row, cell.col - 1));
            }
            if (cell.col < grid[cell.row].length - 1 && step(cell, grid[cell.row][cell.col + 1])) {
                deque.addLast(new RowCol(cell.row, cell.col + 1));
            }
        }
        grid[end.row][end.col].c = END;
    }

    private static boolean step(RowCol cell, Pair neighbour) {
        if ((neighbour.c != WALL || grid[cell.row][cell.col].n == bypassWallsStep) && neighbour.n == -1) {
            neighbour.c = STEP;
            neighbour.n = grid[cell.row][cell.col].n + 1;
            return true;
        }
        return false;
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

    private static class Pair {
        public char c;
        public int n;

        Pair(char c, int n) {
            this.c = c;
            this.n = n;
        }

        @Override
        public String toString() {
            return "" + c;
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
