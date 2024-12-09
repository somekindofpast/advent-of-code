package com.codecool.adventofcode2024;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle09DiskFragmenter {
    static int FREE_SPACE = -1;

    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2024/Disk Fragmenter.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines.get(0).toCharArray());
        partTwo(inputLines.get(0).toCharArray());
    }

    private static void partOne(char[] inputChars) {
        List<Integer> nums = new ArrayList<>();
        int numId = 0;
        for (int i = 0; i < inputChars.length; i++) {
            int blockSize = Integer.parseInt("" + inputChars[i]);
            for (int j = 0; j < blockSize; j++) {
                if (i % 2 == 0) {
                    nums.add(numId);
                } else {
                    nums.add(FREE_SPACE);
                }
            }
            if (i % 2 == 0) {
                numId++;
            }
        }

        //printMemoryBlocks(nums);

        int placeFrom = nums.size();
        int placeTo = -1;
        while (true) {
            placeFrom = findPrevNum(nums, placeFrom);
            placeTo = findNextSpace(nums, placeTo);

            if (placeTo < placeFrom) {
                nums.set(placeTo, nums.get(placeFrom));
                nums.set(placeFrom, FREE_SPACE);
            } else {
                break;
            }
        }
        //printMemoryBlocks(nums);

        long checkSum = 0L;
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            if (num == FREE_SPACE) {
                break;
            }
            checkSum += (long) i * num;
        }
        System.out.println("The resulting filesystem checksum is: " + checkSum);
    }

    private static void partTwo(char[] inputChars) {
        List<Integer> nums = new ArrayList<>();
        int numId = 0;
        for (int i = 0; i < inputChars.length; i++) {
            int blockSize = Integer.parseInt("" + inputChars[i]);
            for (int j = 0; j < blockSize; j++) {
                if (i % 2 == 0) {
                    nums.add(numId);
                } else {
                    nums.add(FREE_SPACE);
                }
            }
            if (i % 2 == 0) {
                numId++;
            }
        }

        //printMemoryBlocks(nums);

        int fileStart = FREE_SPACE;
        int fileEnd = FREE_SPACE;
        int fileId = FREE_SPACE;
        for (int i = nums.size() - 1; 0 <= i; i--) {
            int fileNum = nums.get(i);
            if (fileNum != FREE_SPACE && fileEnd == FREE_SPACE) {
                fileEnd = i;
                fileId = fileNum;
                continue;
            }
            if (fileNum != fileId && fileEnd != FREE_SPACE) {
                i++;
                fileStart = i;
                int spaceStart = FREE_SPACE;
                for (int j = 0; j < fileStart; j++) {
                    int spaceNum = nums.get(j);
                    if (spaceNum == FREE_SPACE && spaceStart == FREE_SPACE) {
                        spaceStart = j;
                        continue;
                    }
                    if (spaceNum != FREE_SPACE && spaceStart != FREE_SPACE) {
                        j--;
                        int spaceEnd = j;
                        int spaceNeeded = fileEnd - fileStart + 1;
                        int spaceAvailable = spaceEnd - spaceStart + 1;
                        if (spaceNeeded <= spaceAvailable) {
                            for (int k = fileStart; k <= fileEnd; k++) {
                                nums.set(spaceStart + (k - fileStart), fileId);
                                nums.set(k, FREE_SPACE);
                            }
                            break;
                        } else {
                            spaceStart = FREE_SPACE;
                        }
                    }
                }
                fileEnd = FREE_SPACE;
                fileId = FREE_SPACE;
            }
        }
        //printMemoryBlocks(nums);

        long checkSum = 0L;
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            if (num == FREE_SPACE) {
                continue;
            }
            checkSum += (long) i * num;
        }
        System.out.println("The filesystem checksum for moving whole files is: " + checkSum);
    }

    private static int findNextSpace(List<Integer> nums, int startIndex) {
        for (int i = startIndex + 1; i < nums.size(); i++) {
            if (nums.get(i) == FREE_SPACE) {
                return i;
            }
        }
        return -1;
    }

    private static int findPrevNum(List<Integer> nums, int startIndex) {
        for (int i = startIndex - 1; 0 <= i; i--) {
            if (nums.get(i) != FREE_SPACE) {
                return i;
            }
        }
        return -1;
    }

    private static void printMemoryBlocks(List<Integer> nums) {
        for (int num : nums) {
            System.out.print((num == FREE_SPACE ? "." : num) + "|");
        }
        System.out.println();
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
