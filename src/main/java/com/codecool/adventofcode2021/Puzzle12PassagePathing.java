package com.codecool.adventofcode2021;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle12PassagePathing {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2021/Passage Pathing.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        Map<String, Visited> caves = new HashMap<>();
        List<String[]> connections = new ArrayList<>();
        for (int row = 0; row < inputLines.size(); row++) {
            connections.add(inputLines.get(row).split("-"));
            caves.put(connections.get(row)[0], Visited.NO);
            caves.put(connections.get(row)[1], Visited.NO);
        }

        List<List<String>> paths = new ArrayList<>();
        paths = getPaths(caves, connections, paths, "start");

        for (int i = 0; i < paths.size(); i++) {
            System.out.println(paths.get(i));
        }
        System.out.println("Puzzle 1: Number of paths through this cave system that visit small caves at most once: " + paths.size());
    }

    private enum Visited {
        YES, NO;
    }

    private static List<List<String>> getPaths(Map<String, Visited> caves, List<String[]> connections, List<List<String>> paths, String cave) {
        if (cave.equals("start")) {
            paths = new ArrayList<>();
            List<String> neighbours = getNeighbours(connections, "start");
            for (String neighbour : neighbours) {
                for (Map.Entry<String, Visited> entry : caves.entrySet()) {
                    entry.setValue(Visited.NO);
                }
                paths.add(new ArrayList<>());
                paths.get(paths.size()-1).add("start");
                paths = getPaths(caves, connections, paths, neighbour);
            }

            for (int i = 0; i < paths.size(); i++) {
                if(!paths.get(i).get(paths.get(i).size()-1).equals("end")) {
                    paths.remove(i);
                    i--;
                }
            }

        } else if (cave.equals("end")) {
            paths.get(paths.size()-1).add("end");
        } else {
            caves.put(cave, Visited.YES);
            paths.get(paths.size()-1).add(cave);
            List<String> neighbours = getNeighbours(connections, cave);
            List<String> currentPath = paths.get(paths.size()-1);
            for (String neighbour : neighbours) {
                for (Map.Entry<String, Visited> entry : caves.entrySet()) {
                    entry.setValue(Visited.NO);
                }
                for (int i = 0; i < currentPath.size(); i++) {
                    caves.put(currentPath.get(i), Visited.YES);
                }
                if(!neighbour.equals("start") && (caves.get(neighbour) != Visited.YES || Character.isUpperCase(neighbour.charAt(0)))) {
                    paths.add(new ArrayList<>(currentPath));
                    paths = getPaths(caves, connections, paths, neighbour);
                }
            }
        }
        return paths;
    }

    private static List<String> getNeighbours(List<String[]> connections, String cave) {
        List<String> neighbours = new ArrayList<>();
        for (String[] connection : connections) {
            if(connection[0].equals(cave))
                neighbours.add(connection[1]);
            else if(connection[1].equals(cave))
                neighbours.add(connection[0]);
        }
        return neighbours;
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
