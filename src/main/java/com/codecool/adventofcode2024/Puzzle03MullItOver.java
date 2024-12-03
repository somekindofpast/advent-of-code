package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle03MullItOver {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Mull It Over.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        Pattern stringPattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        int sum = 0;
        for (String line : inputLines) {
            Matcher m = stringPattern.matcher(line);
            while (m.find()) {
                String numStr = m.group().split("\\(")[1].split("\\)")[0];
                sum += Integer.parseInt(numStr.split(",")[0]) * Integer.parseInt(numStr.split(",")[1]);
            }
        }
        System.out.println("The sum of all multiplications is: " + sum);
    }

    private static void partTwo(ArrayList<String> inputLines) {
        Pattern stringPattern = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
        int sum = 0;
        boolean mulEnabled = true;
        for (String line : inputLines) {
            Matcher m = stringPattern.matcher(line);
            while (m.find()) {
                String match = m.group();
                if (match.equals("do()")) {
                    mulEnabled = true;
                    continue;
                }
                if (match.equals("don't()")) {
                    mulEnabled = false;
                    continue;
                }
                if (!mulEnabled) {
                    continue;
                }
                String numStr = match.split("\\(")[1].split("\\)")[0];
                sum += Integer.parseInt(numStr.split(",")[0]) * Integer.parseInt(numStr.split(",")[1]);
            }
        }
        System.out.println("The sum of just the enabled multiplications is: " + sum);
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
