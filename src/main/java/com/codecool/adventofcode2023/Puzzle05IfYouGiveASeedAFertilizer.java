package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle05IfYouGiveASeedAFertilizer {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/If You Give A Seed A Fertilizer.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        Long[] currentNumbers = new Long[0];
        ArrayList<ArrayList<Long[]>> maps = new ArrayList<>();
        boolean isNextMapComing = false;
        for (String currentRow : inputLines) {
            if (currentRow.contains("seeds:")) {
                currentNumbers = convertStringOfNumbersToNumberArray(currentRow.split(":")[1], " ");
            } else if (currentRow.length() == 0) {
                isNextMapComing = true;
            } else if (isNextMapComing) {
                maps.add(new ArrayList<>());
                isNextMapComing = false;
            } else {
                Long[] ranges = convertStringOfNumbersToNumberArray(currentRow, " ");
                maps.get(maps.size() - 1).add(ranges);
            }
        }

        for (int numIndex = 0; numIndex < currentNumbers.length; numIndex++) {
            for (ArrayList<Long[]> map : maps) {
                long currentNumber = currentNumbers[numIndex];
                boolean isNumberMapped = false;
                for(Long[] ranges : map) {
                    if (currentNumber < ranges[1] || ranges[1] + ranges[2] - 1 < currentNumber) {
                        continue;
                    }
                    for (long offset = 0; !isNumberMapped && offset < ranges[2]; offset++) {
                        if (currentNumber == ranges[1] + offset) {
                            currentNumbers[numIndex] = ranges[0] + offset;
                            isNumberMapped = true;
                        }
                    }
                    if (isNumberMapped) {
                        break;
                    }
                }
            }
        }

        long lowestLocation = currentNumbers[0];
        for (long currentNumber : currentNumbers) {
            if (currentNumber < lowestLocation) {
                lowestLocation = currentNumber;
            }
        }

        System.out.println("lowest location: " + lowestLocation);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        ArrayList<Long> seeds = new ArrayList<>();
        ArrayList<ArrayList<Long[]>> maps = new ArrayList<>();
        boolean isNextMapComing = false;
        for (String currentRow : inputLines) {
            if (currentRow.contains("seeds:")) {
                Long[] ranges = convertStringOfNumbersToNumberArray(currentRow.split(":")[1], " ");
                for (int i = 1; i < ranges.length; i += 2) {
                    for (long j = 0; j < ranges[i]; j++) {
                        seeds.add(ranges[i - 1] + j);
                    }
                }
            } else if (currentRow.length() == 0) {
                isNextMapComing = true;
            } else if (isNextMapComing) {
                maps.add(new ArrayList<>());
                isNextMapComing = false;
            } else {
                Long[] ranges = convertStringOfNumbersToNumberArray(currentRow, " ");
                maps.get(maps.size() - 1).add(ranges);
            }
        }

        Long[] currentNumbers = new Long[seeds.size()];
        currentNumbers = seeds.toArray(currentNumbers);
        for (int numIndex = 0; numIndex < currentNumbers.length; numIndex++) {
            for (ArrayList<Long[]> map : maps) {
                long currentNumber = currentNumbers[numIndex];
                boolean isNumberMapped = false;
                for(Long[] ranges : map) {
                    if (currentNumber < ranges[1] || ranges[1] + ranges[2] - 1 < currentNumber) {
                        continue;
                    }
                    for (long offset = 0; !isNumberMapped && offset < ranges[2]; offset++) {
                        if (currentNumber == ranges[1] + offset) {
                            currentNumbers[numIndex] = ranges[0] + offset;
                            isNumberMapped = true;
                        }
                    }
                    if (isNumberMapped) {
                        break;
                    }
                }
            }
        }

        long lowestLocation = currentNumbers[0];
        for (long currentNumber : currentNumbers) {
            if (currentNumber < lowestLocation) {
                lowestLocation = currentNumber;
            }
        }

        System.out.println("lowest location: " + lowestLocation);
    }

    private static Long[] convertStringOfNumbersToNumberArray(String numberString, String separator) {
        String[] numberStrings = numberString.trim().split(separator);
        Long[] numbers = new Long[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            numbers[i] = Long.parseLong(numberStrings[i]);
        }
        return numbers;
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
