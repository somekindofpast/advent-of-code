package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle10PipeMaze {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Pipe Maze.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        Maze maze = new Maze();
        maze.rowMax = inputLines.size();
        maze.colMax = inputLines.get(0).length();
        maze.rowStart = -1;
        maze.colStart = -1;
        maze.grid = new char[maze.rowMax][maze.colMax];
        for (int row = 0; row < maze.rowMax; row++) {
            String inputLine = inputLines.get(row);
            for (int col = 0; col < maze.colMax; col++) {
                char c = inputLine.charAt(col);
                maze.grid[row][col] = c;
                if (c == 'S') {
                    maze.rowStart = row;
                    maze.colStart = col;
                }
                //System.out.print(maze.grid[row][col]);
            }
            //System.out.println();
        }

        maze.rowA = -1;
        maze.colA = -2;
        maze.rowB = -3;
        maze.colB = -4;
        maze.rowAPrevious = maze.rowStart;
        maze.colAPrevious = maze.colStart;
        maze.rowBPrevious = maze.rowStart;
        maze.colBPrevious = maze.colStart;
        int stepsToFarthestPosition = 0;
        while (!(maze.rowA == maze.rowB && maze.colA == maze.colB)) {
            if (stepsToFarthestPosition == 0) {
                init(maze);
            } else {
                RowCol rowCol = getNextRowCol(maze.rowA, maze.colA, maze.rowAPrevious, maze.colAPrevious, maze.grid[maze.rowA][maze.colA]);
                maze.rowAPrevious = maze.rowA;
                maze.colAPrevious = maze.colA;
                maze.rowA = rowCol.row;
                maze.colA = rowCol.col;
                rowCol = getNextRowCol(maze.rowB, maze.colB, maze.rowBPrevious, maze.colBPrevious, maze.grid[maze.rowB][maze.colB]);
                maze.rowBPrevious = maze.rowB;
                maze.colBPrevious = maze.colB;
                maze.rowB = rowCol.row;
                maze.colB = rowCol.col;
            }
            stepsToFarthestPosition++;
        }
        System.out.println("steps to farthest position: " + stepsToFarthestPosition);
    }

    private static void partTwo(ArrayList<String> inputLines) {
    }

    static class Maze {
        public char[][] grid;
        public int rowMax;
        public int colMax;
        public int rowStart;
        public int colStart;
        public int rowA;
        public int colA;
        public int rowB;
        public int colB;
        public int rowAPrevious;
        public int colAPrevious;
        public int rowBPrevious;
        public int colBPrevious;
    }

    static class RowCol {
        public int row;
        public int col;
    }

    private static void init(Maze maze) {
        maze.rowA = maze.rowStart;
        maze.colA = maze.colStart;
        maze.rowB = maze.rowStart;
        maze.colB = maze.colStart;

        boolean isCoordinateAFound = false;
        if (0 < maze.rowA) {
            char pipe = maze.grid[maze.rowA - 1][maze.colA];
            if (pipe == '|' || pipe == '7' || pipe == 'F') {
                maze.rowA--;
                isCoordinateAFound = true;
            }
        }
        if (!isCoordinateAFound && maze.colA < maze.colMax - 1) {
            char pipe = maze.grid[maze.rowA][maze.colA + 1];
            if (pipe == '-' || pipe == 'J' || pipe == '7') {
                maze.colA++;
                isCoordinateAFound = true;
            }
        }
        if (!isCoordinateAFound && maze.rowA < maze.rowMax - 1) {
            char pipe = maze.grid[maze.rowA + 1][maze.colA];
            if (pipe == '|' || pipe == 'L' || pipe == 'J') {
                maze.rowA++;
            }
        }

        boolean isCoordinateBFound = false;
        if (0 < maze.colB) {
            char pipe = maze.grid[maze.rowB][maze.colB - 1];
            if (pipe == '-' || pipe == 'L' || pipe == 'F') {
                maze.colB--;
                isCoordinateBFound = true;
            }
        }
        if (!isCoordinateBFound && maze.rowB < maze.rowMax - 1) {
            char pipe = maze.grid[maze.rowB + 1][maze.colB];
            if (pipe == '|' || pipe == 'L' || pipe == 'J') {
                maze.rowB++;
                isCoordinateBFound = true;
            }
        }
        if (!isCoordinateBFound && maze.colB < maze.colMax - 1) {
            char pipe = maze.grid[maze.rowB][maze.colB + 1];
            if (pipe == '-' || pipe == 'J' || pipe == '7') {
                maze.colB++;
            }
        }
    }

    private static RowCol getNextRowCol(int row, int col, int rowPrevious, int colPrevious, char pipe) {
        RowCol rowCol = new RowCol();
        switch (pipe) {
            case '|' -> {
                rowCol.row = row - 1 != rowPrevious ? row - 1 : row + 1;
                rowCol.col = col;
            }
            case '-' -> {
                rowCol.row = row;
                rowCol.col = col - 1 != colPrevious ? col - 1 : col + 1;
            }
            case 'L' -> {
                if (row - 1 != rowPrevious) {
                    rowCol.row = row - 1;
                    rowCol.col = col;
                } else {
                    rowCol.row = row;
                    rowCol.col = col + 1;
                }
            }
            case 'J' -> {
                if (row - 1 != rowPrevious) {
                    rowCol.row = row - 1;
                    rowCol.col = col;
                } else {
                    rowCol.row = row;
                    rowCol.col = col - 1;
                }
            }
            case '7' -> {
                if (col - 1 != colPrevious) {
                    rowCol.row = row;
                    rowCol.col = col - 1;
                } else {
                    rowCol.row = row + 1;
                    rowCol.col = col;
                }
            }
            case 'F' -> {
                if (col + 1 != colPrevious) {
                    rowCol.row = row;
                    rowCol.col = col + 1;
                } else {
                    rowCol.row = row + 1;
                    rowCol.col = col;
                }
            }
        }
        return rowCol;
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
