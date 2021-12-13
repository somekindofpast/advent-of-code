package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle13TransparentOrigami {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Transparent Origami.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        List<Integer[]> coordinates = new ArrayList<>();
        List<Integer> folds = new ArrayList<>();

        boolean endOfCoordinates = false;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int row = 0; row < inputLines.size(); row++) {
            if(inputLines.get(row).equals("")) {
                endOfCoordinates = true;
            } else if (!endOfCoordinates) {
                coordinates.add(new Integer[]{Integer.valueOf(inputLines.get(row).split(",")[1]), Integer.valueOf(inputLines.get(row).split(",")[0])});
                if(maxX < coordinates.get(coordinates.size()-1)[1])
                    maxX = coordinates.get(coordinates.size()-1)[1];
                if(maxY < coordinates.get(coordinates.size()-1)[0])
                    maxY = coordinates.get(coordinates.size()-1)[0];
            } else {
                if(inputLines.get(row).split(" ")[2].split("=")[0].equals("x")) {
                    folds.add(Integer.parseInt(inputLines.get(row).split(" ")[2].split("=")[1]));
                } else {
                    folds.add(-(Integer.parseInt(inputLines.get(row).split(" ")[2].split("=")[1])));
                }
            }
        }

        Character[][] coordinateSystem = new Character[maxY+1][maxX+1];
        for (int row = 0; row < coordinateSystem.length; row++) {
            Arrays.fill(coordinateSystem[row], '.');
        }

        for (int i = 0; i < coordinates.size(); i++) {
            coordinateSystem[coordinates.get(i)[0]][coordinates.get(i)[1]] = '#';
        }

        int dotsAfterFirstFold = 0;

        for (int i = 0; i < folds.size(); i++) {
            printCoordinateSystem(coordinateSystem);

            if(folds.get(i) < 0) {
                int foldRow = -folds.get(i);
                Arrays.fill(coordinateSystem[foldRow], '-');
                printCoordinateSystem(coordinateSystem);

                for (int j = 0; j < coordinates.size(); j++) {
                    if(foldRow < coordinates.get(j)[0]) {
                        int coordinateX = coordinates.get(j)[1];
                        int coordinateY = coordinates.get(j)[0];
                        coordinateSystem[foldRow - (coordinateY - foldRow)][coordinateX] = '#';
                    }
                }
                coordinateSystem = splitCoordinateSystem(coordinateSystem, folds.get(i));

            } else {
                int foldColumn = folds.get(i);
                for (int row = 0; row < coordinateSystem.length; row++) {
                    coordinateSystem[row][foldColumn] = '|';
                }
                printCoordinateSystem(coordinateSystem);

                for (int j = 0; j < coordinates.size(); j++) {
                    if(coordinates.get(j)[1] < foldColumn) {
                        int coordinateX = coordinates.get(j)[1];
                        int coordinateY = coordinates.get(j)[0];
                        coordinateSystem[coordinateY][(coordinateSystem[0].length-1) - coordinateX] = '#';
                    }
                }
                coordinateSystem = splitCoordinateSystem(coordinateSystem, folds.get(i));
            }

            coordinates.clear();
            for (int row = 0; row < coordinateSystem.length; row++) {
                for (int column = 0; column < coordinateSystem[row].length; column++) {
                    if(coordinateSystem[row][column] == '#')
                        coordinates.add(new Integer[]{row, column});
                }
            }
            if(i == 0)
                dotsAfterFirstFold = coordinates.size();
        }

        printCoordinateSystem(coordinateSystem);

        System.out.println("Puzzle 1: Dots visible after completing just the first fold instruction on the transparent paper: " + dotsAfterFirstFold);
        System.out.println("Puzzle 2: The eight capital letters are displayed after the final fold.");
    }

    private static void printCoordinateSystem(Character[][] coordinateSystem) {
        for (int row = 0; row < coordinateSystem.length; row++) {
            System.out.println(Arrays.toString(coordinateSystem[row]));
        }
        System.out.println();
    }

    private static Character[][] splitCoordinateSystem(Character[][] coordinateSystem, int foldAxis) {
        Character[][] newCoordinateSystem = null;
        if(foldAxis < 0) {
            int foldRow = -foldAxis;
            newCoordinateSystem = new Character[foldRow][coordinateSystem[0].length];
            for (int row = 0; row < newCoordinateSystem.length; row++) {
                for (int column = 0; column < newCoordinateSystem[row].length; column++) {
                    newCoordinateSystem[row][column] = coordinateSystem[row][column];
                }
            }
        } else {
            int foldColumn = foldAxis;
            newCoordinateSystem = new Character[coordinateSystem.length][coordinateSystem[0].length - foldColumn-1];
            for (int row = 0; row < newCoordinateSystem.length; row++) {
                for (int column = 0; column < newCoordinateSystem[row].length; column++) {
                    newCoordinateSystem[row][column] = coordinateSystem[row][column + foldColumn+1];
                }
            }
        }

        return newCoordinateSystem;
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
