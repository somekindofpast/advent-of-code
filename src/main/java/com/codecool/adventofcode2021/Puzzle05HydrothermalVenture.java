package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle05HydrothermalVenture {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Hydrothermal Venture.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        List<Integer[]> coordinatesLeft = new ArrayList<>();
        List<Integer[]> coordinatesRight = new ArrayList<>();
        int maxColumns = 0, maxRows = 0;
        for (int i = 0; i < inputLines.size(); i++) {
            String[] line = inputLines.get(i).split(" ");
            String[] coordinateString1 = line[0].split(",");
            String[] coordinateString2 = line[2].split(",");
            coordinatesLeft.add (new Integer[]{Integer.parseInt(coordinateString1[0]), Integer.parseInt(coordinateString1[1])});
            coordinatesRight.add(new Integer[]{Integer.parseInt(coordinateString2[0]), Integer.parseInt(coordinateString2[1])});
            if( maxColumns  < coordinatesLeft.get(i)[0])
                maxColumns  = coordinatesLeft.get(i)[0];
            if( maxColumns  < coordinatesRight.get(i)[0])
                maxColumns  = coordinatesRight.get(i)[0];
            if( maxRows     < coordinatesLeft.get(i)[1])
                maxRows     = coordinatesLeft.get(i)[1];
            if( maxRows     < coordinatesRight.get(i)[1])
                maxRows     = coordinatesRight.get(i)[1];
        }

        Puzzle1(maxRows, maxColumns, coordinatesLeft, coordinatesRight);
        Puzzle2(maxRows, maxColumns, coordinatesLeft, coordinatesRight);
    }

    private static void Puzzle1(final int maxRows, final int maxColumns, final List<Integer[]> coordinatesLeft, final List<Integer[]> coordinatesRight) {
        Integer[][] coordinateSystem = new Integer[maxRows+1][maxColumns+1];

        for (int i = 0; i < coordinatesLeft.size(); i++) {
            int x1 = coordinatesLeft.get(i)[0];
            int y1 = coordinatesLeft.get(i)[1];
            int x2 = coordinatesRight.get(i)[0];
            int y2 = coordinatesRight.get(i)[1];

            if(x1 == x2 && y1 < y2) {
                for (int yNow = y1; yNow <= y2; yNow++) {
                    changeCoordinateValue(coordinateSystem, x1, yNow);
                }
            }
            else if(x1 == x2 && y2 < y1) {
                for (int yNow = y1; y2 <= yNow; yNow--) {
                    changeCoordinateValue(coordinateSystem, x1, yNow);
                }
            }
            else if(y1 == y2 && x1 < x2) {
                for (int xNow = x1; xNow <= x2; xNow++) {
                    changeCoordinateValue(coordinateSystem, xNow, y1);
                }
            }
            else if(y1 == y2 && x2 < x1) {
                for (int xNow = x1; x2 <= xNow; xNow--) {
                    changeCoordinateValue(coordinateSystem, xNow, y1);
                }
            }
        }

        long overlapNumber = 0;
        for (int row = 0; row < coordinateSystem.length; row++) {
            for (int column = 0; column < coordinateSystem[0].length; column++) {
                if(coordinateSystem[row][column] != null && 1 < coordinateSystem[row][column])
                    overlapNumber++;
            }
        }

        System.out.println("Puzzle 1: Number of points where at least two lines overlap: " + overlapNumber);
    }

    private static void Puzzle2(final int maxRows, final int maxColumns, final List<Integer[]> coordinatesLeft, final List<Integer[]> coordinatesRight) {
        Integer[][] coordinateSystem = new Integer[maxRows+1][maxColumns+1];

        for (int i = 0; i < coordinatesLeft.size(); i++) {
            int x1 = coordinatesLeft.get(i)[0];
            int y1 = coordinatesLeft.get(i)[1];
            int x2 = coordinatesRight.get(i)[0];
            int y2 = coordinatesRight.get(i)[1];

            if(x1 == x2 && y1 < y2) {
                for (int yNow = y1; yNow <= y2; yNow++) {
                    changeCoordinateValue(coordinateSystem, x1, yNow);
                }
            }
            else if(x1 == x2 && y2 < y1) {
                for (int yNow = y1; y2 <= yNow; yNow--) {
                    changeCoordinateValue(coordinateSystem, x1, yNow);
                }
            }
            else if(y1 == y2 && x1 < x2) {
                for (int xNow = x1; xNow <= x2; xNow++) {
                    changeCoordinateValue(coordinateSystem, xNow, y1);
                }
            }
            else if(y1 == y2 && x2 < x1) {
                for (int xNow = x1; x2 <= xNow; xNow--) {
                    changeCoordinateValue(coordinateSystem, xNow, y1);
                }
            }
            else {
                if(x1 < x2 && y1 < y2) {
                    for (int xNow = x1, yNow = y1; xNow <= x2; xNow++, yNow++) {
                        changeCoordinateValue(coordinateSystem, xNow, yNow);
                    }
                }
                else if(x1 < x2 && y2 < y1) {
                    for (int xNow = x1, yNow = y1; xNow <= x2; xNow++, yNow--) {
                        changeCoordinateValue(coordinateSystem, xNow, yNow);
                    }
                }
                if(x2 < x1 && y1 < y2) {
                    for (int xNow = x1, yNow = y1; x2 <= xNow; xNow--, yNow++) {
                        changeCoordinateValue(coordinateSystem, xNow, yNow);
                    }
                }
                else if(x2 < x1 && y2 < y1) {
                    for (int xNow = x1, yNow = y1; x2 <= xNow; xNow--, yNow--) {
                        changeCoordinateValue(coordinateSystem, xNow, yNow);
                    }
                }
            }
        }

        long overlapNumber = 0;
        for (int row = 0; row < coordinateSystem.length; row++) {
            for (int column = 0; column < coordinateSystem[0].length; column++) {
                if(coordinateSystem[row][column] != null && 1 < coordinateSystem[row][column])
                    overlapNumber++;
            }
        }

        System.out.println("Puzzle 2: Number of points where at least two lines overlap: " + overlapNumber);
    }

    private static void changeCoordinateValue(Integer[][] coordinateSystem, int x, int y) {
        if(coordinateSystem[y][x] == null) {
            coordinateSystem[y][x] = 1;
        }
        else {
            coordinateSystem[y][x] = coordinateSystem[y][x] + 1;
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
