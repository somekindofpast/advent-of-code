package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle10SyntaxScoring {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Syntax Scoring.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        int syntaxErrorScore = 0;
        for (int row = 0; row < inputLines.size(); row++) {
            String line = inputLines.get(row);
            Parentheses[] parentheses = new Parentheses[line.length()];
            for (int i = 0; i < line.length(); i++) {
                parentheses[i] = new Parentheses(line.charAt(i));
            }
            for (int i = 0; i < parentheses.length; i++) {
                if(!parentheses[i].paired && !parentheses[i].open) {
                    boolean pairFound = false;
                    int unPairedNumber = 0;
                    for (int j = i-1; 0 <= j ; j--) {

                        if(!parentheses[j].paired)
                            unPairedNumber++;

                        if(!parentheses[j].paired && parentheses[j].open && parentheses[i].canBePaired(parentheses[j].type) && unPairedNumber <= 1) {
                            parentheses[i].paired = true;
                            parentheses[j].paired = true;
                            pairFound = true;
                            break;
                        }
                    }
                    if(!pairFound) {
                        syntaxErrorScore += parentheses[i].getValue();
                        System.out.println("line " + (row+1) + ": " + parentheses[i].type + " at index " + i);
                        inputLines.remove(row);
                        row--;
                        break;
                    }
                }
            }
        }
        System.out.println("Puzzle 1: the total syntax error score is: " + syntaxErrorScore);

        System.out.println("Remaining incomplete lines: " + inputLines.size());

        for (int row = 0; row < inputLines.size(); row++) {
            String line = inputLines.get(row);
            Parentheses[] parentheses = new Parentheses[line.length()];
            for (int i = 0; i < line.length(); i++) {
                parentheses[i] = new Parentheses(line.charAt(i));
            }
            for (int i = 0; i < parentheses.length; i++) {
                int openRound = 0;
                int openSquare = 0;
                int openBrace = 0;
                int openAngle = 0;

                if(parentheses[i].type == '(') {
                    openRound++;
                } else if(parentheses[i].type == '[') {
                    openSquare++;
                } else if(parentheses[i].type == '{') {
                    openBrace++;
                } else if(parentheses[i].type == '<') {
                    openAngle++;
                } else if(parentheses[i].type == ')') {
                    openRound--;
                } else if(parentheses[i].type == ']') {
                    openSquare--;
                } else if(parentheses[i].type == '}') {
                    openBrace--;
                } else if(parentheses[i].type == '>') {
                    openAngle--;
                }
            }
        }
    }

    private static class Parentheses {
        char type;
        boolean paired;
        boolean open;

        public Parentheses(char type) {
            this.type = type;
            this.paired = false;
            this.open = (type == '(' || type == '[' || type == '{' || type == '<');
        }

        public boolean canBePaired(char pair) {
            if(open) {
                return  (type + "" + pair).equals("()") ||
                        (type + "" + pair).equals("[]") ||
                        (type + "" + pair).equals("{}") ||
                        (type + "" + pair).equals("<>");
            } else {
                return  (pair + "" + type).equals("()") ||
                        (pair + "" + type).equals("[]") ||
                        (pair + "" + type).equals("{}") ||
                        (pair + "" + type).equals("<>");
            }
        }

        public int getValue () {
            if(type == ')') return 3;
            if(type == ']') return 57;
            if(type == '}') return 1197;
            if(type == '>') return 25137;
            return 0;
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
