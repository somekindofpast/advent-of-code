package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle13ClawContraption {
    private static int BUTTON_A = 0;
    private static int BUTTON_B = 1;
    private static int PRIZE = 2;
    private static int BUTTON_PUSHES = 3;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Claw Contraption.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines, 3, 1);
    }

    private static void partOne(ArrayList<String> inputLines, int aPrice, int bPrice) {
        List<Pair> machine = new ArrayList<>();
        long tokens = 0;
        for (String line : inputLines) {
            if (line.strip().isEmpty()) {
                continue;
            }
            String[] parts = line.split(":");
            if (!parts[0].equals("Prize")) {
                int left = Integer.parseInt(parts[1].split(",")[1].strip().split("\\+")[1]);
                int right = Integer.parseInt(parts[1].split(",")[0].strip().split("\\+")[1]);
                machine.add(new Pair(left, right));
            } else {
                int left = Integer.parseInt(parts[1].split(",")[1].strip().split("=")[1]);
                int right = Integer.parseInt(parts[1].split(",")[0].strip().split("=")[1]);
                machine.add(new Pair(left, right));
                Pair buttonPushes = calculateButtonPushes(machine);
                machine.add(buttonPushes);
                machine = new ArrayList<>();

                if (buttonPushes != null) {
                    int aTokens = buttonPushes.left * aPrice;
                    int bTokens = buttonPushes.right * bPrice;
                    System.out.println(buttonPushes.left + "*" + aPrice + " + " + buttonPushes.right + "*" + bPrice + " = " + (aTokens + bTokens));
                    tokens += aTokens + bTokens;
                }
            }
        }
        System.out.println("The fewest tokens to spend to win all possible prizes is: " + tokens);
    }

    private static Pair calculateButtonPushes(List<Pair> machine) {
        Pair buttonA = machine.get(BUTTON_A);
        Pair buttonB = machine.get(BUTTON_B);
        Pair prize = machine.get(PRIZE);

        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                if (
                        i * buttonA.left + j * buttonB.left == prize.left &&
                        i * buttonA.right + j * buttonB.right == prize.right
                ) {
                    return new Pair(i, j);
                }
            }
        }
        return null;
    }

    private static class Pair {
        public int left;
        public int right;

        Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return left + ":" + right;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + left;
            hash = 31 * hash + right;
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return left == pair.left && right == pair.right;
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
