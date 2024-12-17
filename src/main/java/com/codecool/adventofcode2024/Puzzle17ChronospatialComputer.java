package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle17ChronospatialComputer {
    private static long regA;
    private static long regB;
    private static long regC;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Chronospatial Computer.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
        partTwo(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        regA = Integer.parseInt(inputLines.get(0).split(":")[1].strip());
        regB = Integer.parseInt(inputLines.get(1).split(":")[1].strip());
        regC = Integer.parseInt(inputLines.get(2).split(":")[1].strip());
        int[] program = Arrays.stream(inputLines.get(4).split(":")[1].strip().split(","))
                .mapToInt(Integer::parseInt).toArray();

        List<String> output = new ArrayList<>();
        for (int i = 0; i < program.length; i += 2) {
            int opCode = program[i];
            int operand = program[i + 1];
            switch (opCode) {
                case 0:
                    regA = (long) (regA / Math.pow(2, getComboOperandValue(operand)));
                    break;
                case 1:
                    regB = regB | operand;
                    break;
                case 2:
                    regB = getComboOperandValue(operand) % 8;
                    break;
                case 3:
                    if (regA != 0) {
                        i = operand - 2;
                    }
                    break;
                case 4:
                    regB = regB | regC;
                    break;
                case 5:
                    output.add("" + (getComboOperandValue(operand) % 8));
                    break;
                case 6:
                    regB = (long) (regA / Math.pow(2, getComboOperandValue(operand)));
                    break;
                case 7:
                    regC = (long) (regA / Math.pow(2, getComboOperandValue(operand)));
                    break;
            }
        }
        System.out.println("The output of the program is: " + String.join(",", output));
    }

    private static long getComboOperandValue(int comboOp) {
        return switch (comboOp) {
            case 0, 1, 2, 3 -> comboOp;
            case 4 -> regA;
            case 5 -> regB;
            case 6 -> regC;
            default -> -1;
        };
    }

    private static void partTwo(ArrayList<String> inputLines) {
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
