package com.codecool.adventofcode2015;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle15ScienceForHungryPeople {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2015/Science for Hungry People.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        List<Map<String, Integer>> ingredients = new ArrayList<Map<String, Integer>>();

        for (int i = 0; i < inputLines.size(); i++) {
            ingredients.add(getProperties(inputLines.get(i)));
            for (String key : ingredients.get(i).keySet()) {
                System.out.print(key + " ");
                System.out.print(ingredients.get(i).get(key) + " ");
            }
            System.out.println();
        }

        System.out.println("The total score of the highest-scoring cookie: " + getHighScore(ingredients, 0));
        System.out.println("The total score of the highest-scoring cookie with calorie limit: " + getHighScore(ingredients, 500));
    }

    private static long getHighScore(List<Map<String, Integer>> ingredients, int maxCalories) {
        long highScore = 0;
        for (int sprinklesRatio = 1; sprinklesRatio < 100 - 3; sprinklesRatio++) {
            for (int butterscotchRatio = 1; butterscotchRatio < 100 - sprinklesRatio; butterscotchRatio++) {
                for (int chocolateRatio = 1; chocolateRatio < 100 - sprinklesRatio - butterscotchRatio; chocolateRatio++) {
                    int candyRatio = 100 - sprinklesRatio - butterscotchRatio - chocolateRatio;
                    int[] ratio = new int[] { sprinklesRatio, butterscotchRatio, chocolateRatio, candyRatio };
                    long cookieScore = scoreCookieProperties(ingredients, ratio, maxCalories);
                    if(highScore < cookieScore)
                        highScore = cookieScore;
                }
            }
        }
        return highScore;
    }

    private static long scoreCookieProperties(List<Map<String, Integer>> ingredients, int[] ratio, int maxCalories) {
        long cookieScore = 0;
        int nCapacity = 0, nDurability = 0, nflavor = 0, ntexture = 0, nCalories = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            nCapacity   += ratio[i] * ingredients.get(i).get("capacity");
            nDurability += ratio[i] * ingredients.get(i).get("durability");
            nflavor     += ratio[i] * ingredients.get(i).get("flavor");
            ntexture    += ratio[i] * ingredients.get(i).get("texture");
            nCalories   += ratio[i] * ingredients.get(i).get("calories");
        }

        if(0 < maxCalories && maxCalories < nCalories)
            return 0;

        if(nCapacity <= 0 || nDurability <= 0 || nflavor <= 0 || ntexture <= 0)
            return 0;

        return (long)(nCapacity) * nDurability * nflavor * ntexture;
    }

    private static HashMap<String, Integer> getProperties(String inputLine) {
        HashMap<String, Integer> propertyMap = new HashMap<String, Integer>();
        inputLine = inputLine.split(": ")[1];
        String[] properties = inputLine.split(", ");
        for (String property : properties) {
            propertyMap.put(property.split(" ")[0], Integer.parseInt(property.split(" ")[1]));
        }
        return propertyMap;
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
