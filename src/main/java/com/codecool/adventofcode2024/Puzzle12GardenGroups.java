package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle12GardenGroups {
    private static String[][] grid;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Garden Groups.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        grid = new String[inputLines.size()][inputLines.get(0).length()];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = "" + inputLines.get(row).charAt(col);
            }
        }

        List<String> typeVersions = new ArrayList<>();
        Map<String, List<RowCol>> regions = new HashMap<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                String s = grid[row][col];
                if (s.length() == 1) {
                    typeVersions.add(setTypeVersion(new RowCol(row, col), typeVersions));
                    s = grid[row][col];
                }
                List<RowCol> region = regions.getOrDefault(s, new ArrayList<>());
                region.add(new RowCol(row, col));
                regions.put(s, region);
            }
            //System.out.println(Arrays.toString(grid[row]));
        }

        long price = 0;
        for (List<RowCol> region : regions.values()) {
            long perimeter = 0;
            for (RowCol cell : region) {
                perimeter += getCellPerimeter(cell);
            }
            long region_price = region.size() * perimeter;
            //System.out.println("Region of " + grid[region.get(0).row][region.get(0).col] + " plants with price " + region.size() + " * " + perimeter + " = " + region_price);
            price += region_price;
        }
        System.out.println("The total price of fencing all regions on the map is: " + price);
    }

    private static int getCellPerimeter(RowCol cell) {
        int perimeter = 0;
        String type = grid[cell.row][cell.col];
        if (cell.row == 0 || !grid[cell.row - 1][cell.col].equals(type)) {
            perimeter++;
        }
        if (cell.row == grid.length - 1 || !grid[cell.row + 1][cell.col].equals(type)) {
            perimeter++;
        }
        if (cell.col == 0 || !grid[cell.row][cell.col - 1].equals(type)) {
            perimeter++;
        }
        if (cell.col == grid[cell.row].length - 1 || !grid[cell.row][cell.col + 1].equals(type)) {
            perimeter++;
        }
        return perimeter;
    }

    private static String setTypeVersion(RowCol startCell, List<String> typeVersions) {
        String baseType = grid[startCell.row][startCell.col];
        int maxVersion = 0;
        for (String type : typeVersions) {
            if (type.charAt(0) == baseType.charAt(0)) {
                int version = Integer.parseInt(type.substring(1));
                if (maxVersion < version) {
                    maxVersion = version;
                }
            }
        }

        String version = baseType + (maxVersion + 1);
        Stack<RowCol> toProcess = new Stack<>();
        toProcess.push(startCell);
        while (!toProcess.empty()) {
            RowCol cell = toProcess.pop();
            grid[cell.row][cell.col] = version;
            if (0 < cell.row && grid[cell.row - 1][cell.col].equals(baseType)) {
                toProcess.push(new RowCol(cell.row - 1, cell.col));
            }
            if (cell.row < grid.length - 1 && grid[cell.row + 1][cell.col].equals(baseType)) {
                toProcess.push(new RowCol(cell.row + 1, cell.col));
            }
            if (0 < cell.col && grid[cell.row][cell.col - 1].equals(baseType)) {
                toProcess.push(new RowCol(cell.row, cell.col - 1));
            }
            if (cell.col < grid[cell.row].length - 1 && grid[cell.row][cell.col + 1].equals(baseType)) {
                toProcess.push(new RowCol(cell.row, cell.col + 1));
            }
        }
        return version;
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
