package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle04GiantSquid {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Giant Squid.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        String[] bingoNumbers = inputLines.get(0).split(",");
        List<BingoSheet> bingoSheets = new ArrayList<>();
        BingoSheet bingoSheet = new BingoSheet();

        for (int i = 2; i < inputLines.size(); i++) {
            String[] line = inputLines.get(i).trim().replaceAll(" +", " ").split(" ");

            if(!inputLines.get(i).equals("")) {
                bingoSheet.addRow(line);
            }

            if(inputLines.get(i).equals("") || i == inputLines.size()-1) {
                bingoSheets.add(bingoSheet);
                bingoSheet = new BingoSheet();
            }
        }

        int maxSheets = bingoSheets.size();
        for (int i = 0; i < bingoNumbers.length; i++) {
            int number = Integer.parseInt(bingoNumbers[i]);
            for (int j = 0; j < bingoSheets.size(); j++) {
                bingoSheets.get(j).markNumber(number);
                if(bingoSheets.get(j).isBingo()) {
                    if(maxSheets == bingoSheets.size()) {
                        System.out.println("Puzzle 1: The final score of the winning board is: " + (number * bingoSheets.get(j).getUnmarkedSum()));
                    }
                    if(1 == bingoSheets.size()) {
                        System.out.println("Puzzle 2: The final score of the last board to win is: " + (number * bingoSheets.get(j).getUnmarkedSum()));
                    }
                    bingoSheets.remove(j);
                    j--;
                }
            }
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

    private static class BingoSheet {
        List<Integer[]> sheet = new ArrayList<>();
        List<Boolean[]> booleanSheet = new ArrayList<>();

        public void addRow(String[] row) {
            Integer[] newRow = new Integer[row.length];
            Boolean[] newBooleanRow = new Boolean[row.length];
            for (int i = 0; i < row.length; i++) {
                newRow[i] = Integer.parseInt(row[i]);
                newBooleanRow[i] = false;
            }
            sheet.add(newRow);
            booleanSheet.add(newBooleanRow);
        }

        public void markNumber(int number) {
            for (int i = 0; i < sheet.size(); i++) {
                for (int j = 0; j < sheet.get(i).length; j++) {
                    if(sheet.get(i)[j] == number) {
                        booleanSheet.get(i)[j] = true;
                        return;
                    }
                }
            }
        }

        public boolean isBingo() {
            for (int row = 0; row < sheet.size(); row++) {
                int markedNumbers = 0;
                for (int column = 0; column < sheet.get(row).length; column++) {
                    if(booleanSheet.get(row)[column])
                        markedNumbers++;
                }
                if(markedNumbers == sheet.get(row).length)
                    return true;
            }
            for (int column = 0; column < sheet.get(0).length; column++) {
                int markedNumbers = 0;
                for (int row = 0; row < sheet.size(); row++) {
                    if(booleanSheet.get(row)[column])
                        markedNumbers++;
                }
                if(markedNumbers == sheet.size())
                    return true;
            }
            return false;
        }

        public int getUnmarkedSum() {
            int sum = 0;
            for (int i = 0; i < sheet.size(); i++) {
                for (int j = 0; j < sheet.get(i).length; j++) {
                    if(!booleanSheet.get(i)[j]) {
                        sum += sheet.get(i)[j];
                    }
                }
            }
            return sum;
        }
    }
}


