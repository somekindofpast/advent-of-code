package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle14RestroomRedoubt {
    private static int POSITION = 0;
    private static int VELOCITY = 1;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Restroom Redoubt.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines, new Coord(11, 7), 100);
    }

    private static void partOne(ArrayList<String> inputLines, Coord dimension, int seconds) {
        List<List<Coord>> robots = new ArrayList<>();
        for (String line : inputLines) {
            List<Coord> robot = new ArrayList<>();
            String posStr = line.split("\\s+")[0].split("=")[1];
            robot.add(new Coord(Integer.parseInt(posStr.split(",")[0]), Integer.parseInt(posStr.split(",")[1])));
            String velStr = line.split("\\s+")[1].split("=")[1];
            robot.add(new Coord(Integer.parseInt(velStr.split(",")[0]), Integer.parseInt(velStr.split(",")[1])));
            robots.add(robot);
        }

        for (int sec = 0; sec < seconds; sec++) {
            for (List<Coord> robot : robots) {
                robot.set(0, robot.get(POSITION).add(robot.get(VELOCITY)));
            }
        }

        String[][] grid = new String[dimension.y][dimension.x];
        for (List<Coord> robot : robots) {

            int col = robot.get(0).x;
            if (col < 0) {
                col = dimension.x - (Math.abs(col) % dimension.x);
                if (col == dimension.x) {
                    col = 0;
                }
            } else {
                col = col % dimension.x;
            }

            int row = robot.get(0).y;
            if (row < 0) {
                row = dimension.y - (Math.abs(row) % dimension.y);
                if (row == dimension.y) {
                    row = 0;
                }
            } else {
                row = row % dimension.y;
            }
            
            if (grid[row][col] == null) {
                grid[row][col] = "1";
            } else {
                grid[row][col] = "" + (Integer.parseInt(grid[row][col]) + 1);
            }
        }

        int quadrantUpLeft = 0;
        int quadrantUpRight = 0;
        int quadrantDownLeft = 0;
        int quadrantDownRight = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    grid[row][col] = ".";
                } else {
                    int num = Integer.parseInt(grid[row][col]);
                    if (row < dimension.y / 2) {
                        if (col < dimension.x / 2) {
                            quadrantUpLeft += num;
                        } else if (dimension.x / 2 < col) {
                            quadrantUpRight += num;
                        }
                    } else if (dimension.y / 2 < row) {
                        if (col < dimension.x / 2) {
                            quadrantDownLeft += num;
                        } else if (dimension.x / 2 < col) {
                            quadrantDownRight += num;
                        }
                    }
                }
            }
            System.out.println(Arrays.toString(grid[row]));
        }
        System.out.println("After " + seconds + " seconds, the safety factor will be: " + (quadrantUpLeft * quadrantUpRight * quadrantDownLeft * quadrantDownRight));
    }

    private static class Coord {
        public int x;
        public int y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + x;
            hash = 31 * hash + y;
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Coord pair = (Coord) o;
            return x == pair.x && y == pair.y;
        }

        public Coord add(Coord other) {
            return new Coord(x + other.x, y + other.y);
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
