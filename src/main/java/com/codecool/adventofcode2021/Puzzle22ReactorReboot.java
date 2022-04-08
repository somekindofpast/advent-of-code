package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Puzzle22ReactorReboot {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Reactor Reboot.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        List<Cuboid> cuboids = new ArrayList<>();
        for (int row = 0; row < inputLines.size() - 2; row++) {
            Cuboid cuboid = new Cuboid();

            cuboid.on = inputLines.get(row).split(" ")[0].equals("on");
            String range = inputLines.get(row).split(" ")[1].split(",")[0];
            cuboid.x1 = Integer.parseInt(range.split("=")[1].split("\\..")[0]);
            cuboid.x2 = Integer.parseInt(range.split("=")[1].split("\\..")[1]);

            range = inputLines.get(row).split(" ")[1].split(",")[1];
            cuboid.y1 = Integer.parseInt(range.split("=")[1].split("\\..")[0]);
            cuboid.y2 = Integer.parseInt(range.split("=")[1].split("\\..")[1]);

            range = inputLines.get(row).split(" ")[1].split(",")[2];
            cuboid.z1 = Integer.parseInt(range.split("=")[1].split("\\..")[0]);
            cuboid.z2 = Integer.parseInt(range.split("=")[1].split("\\..")[1]);

            cuboids.add(cuboid);

            if(cuboid.x1 < min)
                min = cuboid.x1;
            if(cuboid.y1 < min)
                min = cuboid.y1;
            if(cuboid.z1 < min)
                min = cuboid.z1;

            if(max < cuboid.x2)
                max = cuboid.x2;;
            if(max < cuboid.y2)
                max = cuboid.y2;;
            if(max < cuboid.z2)
                max = cuboid.z2;;
        }

        System.out.println("cuboids: " + cuboids.size());
        System.out.println("minX: " + min);
        System.out.println("maxZ: " + max);

        int range = (min < 0 ? Math.abs(min - 1) : 0) + max + 1;
        Boolean[][][] cubes = new Boolean[range][range][range];
        for (int x = 0; x < cubes.length; x++) {
            for (int y = 0; y < cubes[x].length; y++) {
                for (int z = 0; z < cubes[x][y].length; z++) {
                    cubes[x][y][z] = false;
                }
            }
        }

        for (Cuboid cuboid : cuboids) {
            for (int x = cuboid.x1; x <= cuboid.x2; x++) {
                for (int y = cuboid.y1; y <= cuboid.y2; y++) {
                    for (int z = cuboid.z1; z <= cuboid.z2; z++) {
                        cubes[x + range / 2][y + range / 2][z + range / 2] = cuboid.on;
                    }
                }
            }
        }

        int onCubes = 0;
        int xMin = 1;
        int xMax = 101;
        int yMin = 1;
        int yMax = 101;
        int zMin = 1;
        int zMax = 101;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                for (int z = zMin; z <= zMax; z++) {
                    if(cubes[x][y][z])
                        onCubes++;
                }
            }
        }
        System.out.println(onCubes + " cubes are on");
    }

    private static class Cuboid {
        boolean on;
        int x1;
        int x2;
        int y1;
        int y2;
        int z1;
        int z2;
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
