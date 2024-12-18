package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle18RAMRun {
    private static final char EMPTY = '.';
    private static final char WALL = '#';
    private static final char STEP = 'O';

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/RAM Run.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines, 71);
        partTwo(inputLines, 71);
    }

    private static void partOne(ArrayList<String> inputLines, int gridSize) {
        Pair[][] grid = new Pair[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                grid[row][col] = new Pair(EMPTY, 0);
            }
        }
        int count = 0;
        for (String line : inputLines) {
            if (count == 1024) {
                break;
            }
            count++;
            int col = Integer.parseInt(line.split(",")[0]);
            int row = Integer.parseInt(line.split(",")[1]);
            grid[row][col] = new Pair(WALL, -1);
        }

        RowCol start = new RowCol(0, 0);
        grid[0][0].n = 1;
        Deque<RowCol> deque = new ArrayDeque<>();
        deque.addLast(start);
        while (!deque.isEmpty()) {
            RowCol cell = deque.removeFirst();
            int val = grid[cell.row][cell.col].n;
            if (0 < cell.row && grid[cell.row - 1][cell.col].n == 0) {
                grid[cell.row - 1][cell.col].n = val + 1;
                deque.addLast(new RowCol(cell.row - 1, cell.col));
            }
            if (cell.row < gridSize - 1 && grid[cell.row + 1][cell.col].n == 0) {
                grid[cell.row + 1][cell.col].n = val + 1;
                deque.addLast(new RowCol(cell.row + 1, cell.col));
            }
            if (0 < cell.col && grid[cell.row][cell.col - 1].n == 0) {
                grid[cell.row][cell.col - 1].n = val + 1;
                deque.addLast(new RowCol(cell.row, cell.col - 1));
            }
            if (cell.col < gridSize - 1 && grid[cell.row][cell.col + 1].n == 0) {
                grid[cell.row][cell.col + 1].n = val + 1;
                deque.addLast(new RowCol(cell.row, cell.col + 1));
            }
        }

        RowCol end = new RowCol(gridSize - 1, gridSize - 1);
        Stack<RowCol> stack = new Stack<>();
        stack.push(end);
        int steps = 0;
        while (!stack.empty()) {
            RowCol cell = stack.pop();
            grid[cell.row][cell.col].c = STEP;
            int val = grid[cell.row][cell.col].n;

            if (val == 1) {
                break;
            }
            steps++;

            if (0 < cell.row && grid[cell.row - 1][cell.col].n == val - 1) {
                stack.push(new RowCol(cell.row - 1, cell.col));
            } else if (cell.row < gridSize - 1 && grid[cell.row + 1][cell.col].n == val - 1) {
                stack.push(new RowCol(cell.row + 1, cell.col));
            } else if (0 < cell.col && grid[cell.row][cell.col - 1].n == val - 1) {
                stack.push(new RowCol(cell.row, cell.col - 1));
            } else if (cell.col < gridSize - 1 && grid[cell.row][cell.col + 1].n == val - 1) {
                stack.push(new RowCol(cell.row, cell.col + 1));
            }
        }

        for (Pair[] gridRow : grid) {
            System.out.println(Arrays.toString(gridRow));
        }
        System.out.println("The minimum number of steps needed to reach the exit is: " + steps);
    }

    private static void partTwo(ArrayList<String> inputLines, int gridSize) {
        Pair[][] grid = new Pair[gridSize][gridSize];

        for (int i = 1025; i < inputLines.size(); i++) {
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    grid[row][col] = new Pair(EMPTY, 0);
                }
            }
            int count = 0;
            for (String line : inputLines) {
                if (count == i) {
                    break;
                }
                count++;
                int col = Integer.parseInt(line.split(",")[0]);
                int row = Integer.parseInt(line.split(",")[1]);
                grid[row][col] = new Pair(WALL, -1);
            }

            RowCol start = new RowCol(0, 0);
            grid[0][0].n = 1;
            Deque<RowCol> deque = new ArrayDeque<>();
            deque.addLast(start);
            while (!deque.isEmpty()) {
                RowCol cell = deque.removeFirst();
                int val = grid[cell.row][cell.col].n;
                if (0 < cell.row && grid[cell.row - 1][cell.col].n == 0) {
                    grid[cell.row - 1][cell.col].n = val + 1;
                    deque.addLast(new RowCol(cell.row - 1, cell.col));
                }
                if (cell.row < gridSize - 1 && grid[cell.row + 1][cell.col].n == 0) {
                    grid[cell.row + 1][cell.col].n = val + 1;
                    deque.addLast(new RowCol(cell.row + 1, cell.col));
                }
                if (0 < cell.col && grid[cell.row][cell.col - 1].n == 0) {
                    grid[cell.row][cell.col - 1].n = val + 1;
                    deque.addLast(new RowCol(cell.row, cell.col - 1));
                }
                if (cell.col < gridSize - 1 && grid[cell.row][cell.col + 1].n == 0) {
                    grid[cell.row][cell.col + 1].n = val + 1;
                    deque.addLast(new RowCol(cell.row, cell.col + 1));
                }
            }

            if (grid[gridSize - 1][gridSize - 1].n == 0) {
                System.out.println("The coordinates of the first byte that will prevent the exit from being reachable from your starting position is: " + inputLines.get(count - 1));
                break;
            }
        }
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
