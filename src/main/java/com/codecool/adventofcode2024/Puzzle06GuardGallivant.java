package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;



public class Puzzle06GuardGallivant {
    static char UP_DIR = '^';
    static char RIGHT_DIR = '>';
    static char DOWN_DIR = 'v';
    static char LEFT_DIR = '<';
    static char OBSTACLE = '#';
    static char MARKED = 'X';
    static char[][] map;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Guard Gallivant.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        int row = 0;
        int col = 0;
        char direction = UP_DIR;
        int posNum = 0;
        map = new char[inputLines.size()][inputLines.get(0).length()];
        for (int iRow = 0; iRow < inputLines.size();iRow++) {
            for (int iCol = 0; iCol < inputLines.get(0).length();iCol++) {
                char c = inputLines.get(iRow).charAt(iCol);
                if (posNum == 0 && (c == UP_DIR || c == LEFT_DIR || c == DOWN_DIR || c == RIGHT_DIR)) {
                    row = iRow;
                    col = iCol;
                    direction = c;
                    c = MARKED;
                    posNum++;
                }
                map[iRow][iCol] = c;
            }
        }

        boolean isPatrolling = true;
        while (isPatrolling) {
            if (direction == UP_DIR && 0 <= row - 1) {
                if (map[row - 1][col] == OBSTACLE) {
                    direction = RIGHT_DIR;
                } else {
                    row--;
                    posNum += markPosition(row, col);
                }
            } else if (direction == RIGHT_DIR && col + 1 < map[0].length) {
                if (map[row][col + 1] == OBSTACLE) {
                    direction = DOWN_DIR;
                } else {
                    col++;
                    posNum += markPosition(row, col);
                }
            } else if (direction == DOWN_DIR && row + 1 < map.length) {
                if (map[row + 1][col] == OBSTACLE) {
                    direction = LEFT_DIR;
                } else {
                    row++;
                    posNum += markPosition(row, col);
                }
            } else if (direction == LEFT_DIR && 0 <= col - 1) {
                if (map[row][col - 1] == OBSTACLE) {
                    direction = UP_DIR;
                } else {
                    col--;
                    posNum += markPosition(row, col);
                }
            } else {
                isPatrolling = false;
            }
        }
        for (char[] chars : map) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(chars[j]);
            }
            System.out.println();
        }
        System.out.println("The number of distinct positions the guard will visit is: " + posNum);
    }

    private static int markPosition(int row, int col) {
        if (map[row][col] != MARKED) {
            map[row][col] = MARKED;
            return 1;
        }
        return 0;
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
