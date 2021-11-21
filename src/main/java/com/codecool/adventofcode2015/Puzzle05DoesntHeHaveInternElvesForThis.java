package com.codecool.adventofcode2015;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle05DoesntHeHaveInternElvesForThis {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2015/Doesn't He Have Intern-Elves For This.txt")));
        //ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(new String[]{"aabab", "abc", "12uu434gdsapq", "bccggifsknumgfde"}));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");
        else {
            System.out.println("The number of nice strings in PART ONE of this puzzle are: " + countNiceStringsPartOne(inputLines));
            System.out.println("The number of nice strings in PART TWO of this puzzle are: " + countNiceStringsPartTwo(inputLines));
        }
    }

    public static int countNiceStringsPartOne(ArrayList<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if(isTrueThreeVowels(line.toLowerCase()) && isTrueLetterTwiceInARow(line.toLowerCase()) && isTrueDoesNotContainWrongStrings(line.toLowerCase()))
                niceCount++;
        }
        return niceCount;
    }

    public static int countNiceStringsPartTwo(ArrayList<String> inputLines) {
        int niceCount = 0;
        for (String line : inputLines) {
            if(isTrueContainsPairOfTwoLetters(line) && isTrueLetterRepeatsWithOneOtherLetterBetween(line))
                niceCount++;
        }
        return niceCount;
    }

    private static boolean isTrueThreeVowels(String line) {
        for (int i = 0, vowelCount = 0; i < line.length(); i++) {
            if(line.charAt(i) == 'a' || line.charAt(i) == 'e' || line.charAt(i) == 'i' || line.charAt(i) == 'o' || line.charAt(i) == 'u') {
                if(++vowelCount == 3)
                    return true;
            }
        }
        return false;
    }

    private static boolean isTrueLetterTwiceInARow(String line) {
        for (int i = 1; i < line.length(); i++) {
            if('a' <= line.charAt(i) && line.charAt(i) <= 'z' && line.charAt(i-1) == line.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTrueDoesNotContainWrongStrings(String line) {
        return !line.contains("ab") && !line.contains("cd") && !line.contains("pq") && !line.contains("xy");
    }

    private static boolean isTrueContainsPairOfTwoLetters(String line) {
        for (char c1 = 'a'; c1 <= 'z'; c1++) {
            for (char c2 = 'a'; c2 <= 'z'; c2++) {
                int indexFound = 0;
                String toFind = c1 + "" + c2;
                int findCount = 0;
                
                while(indexFound != -1) {
                    indexFound = line.indexOf(toFind, indexFound);
                    if(indexFound != -1) {
                        findCount++;
                        indexFound += toFind.length();
                        if(findCount == 2)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isTrueLetterRepeatsWithOneOtherLetterBetween(String line) {
        for (int i = 0; i < line.length()-2; i++) {
            if('a' <= line.charAt(i) && line.charAt(i) <= 'z' && line.charAt(i) != line.charAt(i+1) && line.charAt(i) == line.charAt(i+2)) {
                return true;
            }
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
