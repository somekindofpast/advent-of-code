package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle15WarehouseWoes {
    private static final char UP = '^';
    private static final char DOWN = 'v';
    private static final char LEFT = '<';
    private static final char RIGHT = '>';
    private static final char ROBOT = '@';
    private static final char BOX = 'O';
    private static final char BOX_LEFT = '[';
    private static final char BOX_RIGHT = ']';
    private static final char EMPTY = '.';
    private static final char WALL = '#';

    private static char[][] grid = null;
    private static int robotRow = -1;
    private static int robotCol = -1;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Warehouse Woes.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        grid = null;
        robotRow = -1;
        robotCol = -1;
        for (int i = 0; i < inputLines.size(); i++) {
            if (inputLines.get(i).strip().isEmpty()) {
                grid = new char[i][inputLines.get(i - 1).length()];
                for (int row = 0; row < grid.length; row++) {
                    String line = inputLines.get(row);
                    grid[row] = line.toCharArray();
                    int possible_col = line.indexOf(ROBOT);
                    if (possible_col != -1) {
                        robotRow = row;
                        robotCol = possible_col;
                    }
                    //System.out.println(grid[row]);
                }
            } else if (grid != null) {
                for (char c : inputLines.get(i).toCharArray()) {
                    if (c == UP) {
                        moveUp();
                    } else if (c == DOWN) {
                        moveDown();
                    } else if (c == LEFT) {
                        moveLeft();
                    } else if (c == RIGHT) {
                        moveRight();
                    }
                }
            }
        }

        assert grid != null;
        long boxCoordSum = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == BOX) {
                    boxCoordSum += 100L * row + col;
                }
            }
            System.out.println(grid[row]);
        }
        System.out.println("The sum of all boxes' GPS coordinates is: " + boxCoordSum);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        grid = null;
        robotRow = -1;
        robotCol = -1;
        for (int i = 0; i < inputLines.size(); i++) {
            if (inputLines.get(i).strip().isEmpty()) {
                grid = new char[i][inputLines.get(i - 1).length() * 2];
                for (int row = 0; row < grid.length; row++) {
                    String line = inputLines.get(row);
                    for (int col = 0; col < line.length(); col++) {
                        if (line.charAt(col) == WALL) {
                            grid[row][col * 2] = WALL;
                            grid[row][col * 2 + 1] = WALL;
                        } else if (line.charAt(col) == BOX) {
                            grid[row][col * 2] = BOX_LEFT;
                            grid[row][col * 2 + 1] = BOX_RIGHT;
                        } else if (line.charAt(col) == EMPTY) {
                            grid[row][col * 2] = EMPTY;
                            grid[row][col * 2 + 1] = EMPTY;
                        } else if (line.charAt(col) == ROBOT) {
                            grid[row][col * 2] = ROBOT;
                            grid[row][col * 2 + 1] = EMPTY;
                            robotRow = row;
                            robotCol = col * 2;
                        }
                    }
                    //System.out.println(grid[row]);
                }
            } else if (grid != null) {
                for (char c : inputLines.get(i).toCharArray()) {

                    /*for (char[] gridRow : grid) {
                        System.out.println(gridRow);
                    }

                    System.out.println("next move: " + c);
                    System.out.println();*/

                    moveV2(c);
                }
            }
        }

        assert grid != null;
        long boxCoordSum = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == BOX_LEFT) {
                    boxCoordSum += 100L * row + col;
                }
            }
            System.out.println(grid[row]);
        }
        System.out.println("The sum of all large boxes' GPS coordinates is: " + boxCoordSum);
    }

    private static void moveUp() {
        int space = -1;
        for (int row = robotRow - 1; 0 <= row; row--) {
            if (grid[row][robotCol] == EMPTY) {
                space = row;
                break;
            } else if (grid[row][robotCol] == WALL) {
                break;
            }
        }
        if (space != -1) {
            grid[robotRow][robotCol] = EMPTY;
            robotRow--;
            grid[robotRow][robotCol] = ROBOT;
            if (space != robotRow) {
                grid[space][robotCol] = BOX;
            }
        }
    }

    private static void moveDown() {
        int space = -1;
        for (int row = robotRow + 1; row < grid.length; row++) {
            if (grid[row][robotCol] == EMPTY) {
                space = row;
                break;
            } else if (grid[row][robotCol] == WALL) {
                break;
            }
        }
        if (space != -1) {
            grid[robotRow][robotCol] = EMPTY;
            robotRow++;
            grid[robotRow][robotCol] = ROBOT;
            if (space != robotRow) {
                grid[space][robotCol] = BOX;
            }
        }
    }

    private static void moveLeft() {
        int space = -1;
        for (int col = robotCol - 1; 0 <= col; col--) {
            if (grid[robotRow][col] == EMPTY) {
                space = col;
                break;
            } else if (grid[robotRow][col] == WALL) {
                break;
            }
        }
        if (space != -1) {
            grid[robotRow][robotCol] = EMPTY;
            robotCol--;
            grid[robotRow][robotCol] = ROBOT;
            if (space != robotCol) {
                grid[robotRow][space] = BOX;
            }
        }
    }

    private static void moveRight() {
        int space = -1;
        for (int col = robotCol + 1; col < grid[robotRow].length; col++) {
            if (grid[robotRow][col] == EMPTY) {
                space = col;
                break;
            } else if (grid[robotRow][col] == WALL) {
                break;
            }
        }
        if (space != -1) {
            grid[robotRow][robotCol] = EMPTY;
            robotCol++;
            grid[robotRow][robotCol] = ROBOT;
            if (space != robotCol) {
                grid[robotRow][space] = BOX;
            }
        }
    }

    private static void moveV2(char direction) {
        int newRow = robotRow;
        int newCol = robotCol;

        if (direction == UP) {
            newRow--;
        } else if (direction == DOWN) {
            newRow++;
        } else if (direction == LEFT) {
            newCol--;
        } else if (direction == RIGHT) {
            newCol++;
        }

        if (grid[newRow][newCol] == WALL) {
            return;
        }
        if (grid[newRow][newCol] == EMPTY) {
            grid[robotRow][robotCol] = EMPTY;
            robotRow = newRow;
            robotCol = newCol;
            grid[robotRow][robotCol] = ROBOT;
            return;
        }

        if (direction == UP && !moveUpV2(newRow, newCol)) {
            return;
        } else if (direction == DOWN && !moveDownV2(newRow, newCol)) {
            return;
        } else if (direction == LEFT && !moveLeftV2(newRow, newCol)) {
            return;
        } else if (direction == RIGHT && !moveRightV2(newRow, newCol)) {
            return;
        }

        grid[robotRow][robotCol] = EMPTY;
        robotRow = newRow;
        robotCol = newCol;
        grid[robotRow][robotCol] = ROBOT;
    }

    private static boolean moveUpV2(int newRow, int newCol) {
        Stack<RowCol> boxes = new Stack<>();
        List<RowCol> boxGroup = new ArrayList<>();
        if (grid[newRow][newCol] == BOX_LEFT) {
            boxGroup.add(new RowCol(newRow, newCol));
        } else {
            boxGroup.add(new RowCol(newRow, newCol - 1));
        }
        while (!boxGroup.isEmpty()) {
            List<RowCol> nextGroup = new ArrayList<>();
            for (RowCol box : boxGroup) {
                boxes.push(box);
                if (grid[box.row - 1][box.col] == WALL || grid[box.row - 1][box.col + 1] == WALL) {
                    return false;
                }
                if (grid[box.row - 1][box.col] == BOX_RIGHT) {
                    nextGroup.add(new RowCol(box.row - 1, box.col - 1));
                } else if (grid[box.row - 1][box.col] == BOX_LEFT) {
                    nextGroup.add(new RowCol(box.row - 1, box.col));
                }
                if (grid[box.row - 1][box.col + 1] == BOX_LEFT) {
                    nextGroup.add(new RowCol(box.row - 1, box.col + 1));
                }
            }
            boxGroup = nextGroup;
        }
        while (!boxes.empty()) {
            RowCol box = boxes.pop();
            grid[box.row - 1][box.col] = BOX_LEFT;
            grid[box.row - 1][box.col + 1] = BOX_RIGHT;
            grid[box.row][box.col] = EMPTY;
            grid[box.row][box.col + 1] = EMPTY;
        }
        return true;
    }

    private static boolean moveDownV2(int newRow, int newCol) {
        Stack<RowCol> boxes = new Stack<>();
        List<RowCol> boxGroup = new ArrayList<>();
        if (grid[newRow][newCol] == BOX_LEFT) {
            boxGroup.add(new RowCol(newRow, newCol));
        } else {
            boxGroup.add(new RowCol(newRow, newCol - 1));
        }
        while (!boxGroup.isEmpty()) {
            List<RowCol> nextGroup = new ArrayList<>();
            for (RowCol box : boxGroup) {
                boxes.push(box);
                if (grid[box.row + 1][box.col] == WALL || grid[box.row + 1][box.col + 1] == WALL) {
                    return false;
                }
                if (grid[box.row + 1][box.col] == BOX_RIGHT) {
                    nextGroup.add(new RowCol(box.row + 1, box.col - 1));
                } else if (grid[box.row + 1][box.col] == BOX_LEFT) {
                    nextGroup.add(new RowCol(box.row + 1, box.col));
                }
                if (grid[box.row + 1][box.col + 1] == BOX_LEFT) {
                    nextGroup.add(new RowCol(box.row + 1, box.col + 1));
                }
            }
            boxGroup = nextGroup;
        }
        while (!boxes.empty()) {
            RowCol box = boxes.pop();
            grid[box.row + 1][box.col] = BOX_LEFT;
            grid[box.row + 1][box.col + 1] = BOX_RIGHT;
            grid[box.row][box.col] = EMPTY;
            grid[box.row][box.col + 1] = EMPTY;
        }
        return true;
    }

    private static boolean moveLeftV2(int newRow, int newCol) {
        int startCol = -1;
        for (int col = newCol; 0 <= col; col--) {
            if (grid[newRow][col] == WALL) {
                return false;
            }
            if (grid[newRow][col] == EMPTY) {
                startCol = col;
                break;
            }
        }
        for (int col = startCol; col < newCol; col++) {
            if (grid[newRow][col] == BOX_LEFT) {
                grid[newRow][col - 1] = BOX_LEFT;
                grid[newRow][col] = BOX_RIGHT;
                grid[newRow][col + 1] = EMPTY;
                col++;
            }
        }
        return true;
    }

    private static boolean moveRightV2(int newRow, int newCol) {
        int startCol = -1;
        for (int col = newCol; col < grid[newRow].length; col++) {
            if (grid[newRow][col] == WALL) {
                return false;
            }
            if (grid[newRow][col] == EMPTY) {
                startCol = col;
                break;
            }
        }
        for (int col = startCol; newCol < col; col--) {
            if (grid[newRow][col] == BOX_RIGHT) {
                grid[newRow][col + 1] = BOX_RIGHT;
                grid[newRow][col] = BOX_LEFT;
                grid[newRow][col - 1] = EMPTY;
                col--;
            }
        }
        return true;
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
