package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle15Chiton {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Chiton.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        final GridNode[][] riskLevelGrid = new GridNode[inputLines.size()][inputLines.get(0).length()];
        for (int row = 0; row < inputLines.size(); row++) {
            for (int column = 0; column < inputLines.get(row).length(); column++) {
                riskLevelGrid[row][column] = new GridNode(row, column, inputLines.get(row).charAt(column) - '0');
            }
        }

        List<List<GridNode>> paths = new ArrayList<>();

        paths.add(new ArrayList<>());
        GridNode startNode = riskLevelGrid[0][0];
        GridNode endNode = riskLevelGrid[riskLevelGrid.length-1][riskLevelGrid[0].length-1];
        paths.get(0).add(startNode);

        List<GridNode> lowestRiskPath = null;
        int minRisk = Integer.MAX_VALUE;

        while (paths.size() != 0) {
            final List<GridNode> currentPath = paths.get(paths.size()-1);
            paths.remove(paths.size()-1);
            final GridNode lastNode = currentPath.get(currentPath.size()-1);

            int currentRisk = getPathRiskValue(currentPath);

            if(minRisk <= currentRisk)
                continue;

            if(!lastNode.equals(endNode)) {
                if(0 <= lastNode.row - 1 && !currentPath.contains(riskLevelGrid[lastNode.row - 1][lastNode.column])) {
                    paths.add(new ArrayList<>(currentPath));
                    paths.get(paths.size()-1).add(riskLevelGrid[lastNode.row - 1][lastNode.column]);
                }
                if(0 <= lastNode.column - 1 && !currentPath.contains(riskLevelGrid[lastNode.row][lastNode.column - 1])) {
                    paths.add(new ArrayList<>(currentPath));
                    paths.get(paths.size()-1).add(riskLevelGrid[lastNode.row][lastNode.column - 1]);
                }
                if(lastNode.row + 1 < riskLevelGrid.length && !currentPath.contains(riskLevelGrid[lastNode.row + 1][lastNode.column])) {
                    paths.add(new ArrayList<>(currentPath));
                    paths.get(paths.size()-1).add(riskLevelGrid[lastNode.row + 1][lastNode.column]);
                }
                if(lastNode.column + 1 < riskLevelGrid[0].length && !currentPath.contains(riskLevelGrid[lastNode.row][lastNode.column + 1])) {
                    paths.add(new ArrayList<>(currentPath));
                    paths.get(paths.size()-1).add(riskLevelGrid[lastNode.row][lastNode.column + 1]);
                }
            } else  {
                lowestRiskPath = currentPath;
                minRisk = currentRisk;
            }
        }

        if(lowestRiskPath != null) {
            System.out.println("Lowest risk path: (" + lowestRiskPath.size() + " nodes)");
            for (GridNode node : lowestRiskPath) {
                System.out.println(node);
            }
            System.out.println();
            System.out.println("Puzzle 1: The lowest total risk of any path from the top left to the bottom right: " + minRisk);
        }
    }

    private static class GridNode {
        public int row;
        public int column;
        public int value;

        public GridNode(int row, int column, int value) {
            this.row = row;
            this.column = column;
            this.value = value;
        }

        public boolean equals(GridNode toCompare) {
            return toCompare != null && row == toCompare.row && column == toCompare.column;
        }

        @Override
        public String toString() {
            return "row: " + row + " column: " + column + " value: " + value;
        }
    }

    private static int getPathRiskValue(List<GridNode> path) {
        int value = 0;
        for (GridNode node : path) {
            value += node.value;
        }
        return value - path.get(0).value;
    }

    private static boolean isBlockedOff(GridNode[][] riskLevelGrid, List<GridNode> currentPath) {
        GridNode lastNode = currentPath.get(currentPath.size()-1);

        boolean topBlocked = false;
        boolean bottomBlocked = false;
        boolean leftBlocked = false;
        boolean rightBlocked = false;

        for (GridNode node : currentPath) {
            if(node.row == 0 && lastNode.column < node.column)
                topBlocked = true;
            else if(node.row == riskLevelGrid.length-1 && lastNode.column < node.column)
                bottomBlocked = true;

            if(topBlocked && bottomBlocked)
                return true;

            if(node.column == 0 && lastNode.row < node.row)
                leftBlocked = true;
            else if(node.column == riskLevelGrid[0].length-1 && lastNode.row < node.row)
                rightBlocked = true;

            if(leftBlocked && rightBlocked)
                return true;
        }

        return false;
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
