package com.codecool.adventofcode2015;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ItHangsInTheBalance {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2015/It Hangs in the Balance.txt")));
        //ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3", "4", "5", "7", "8", "9", "10", "11"}));
        //ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3", "5", "7", "13", "17", "19", "23", "29", "31"}));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        ArrayList<Integer> numberList = new ArrayList<>();
        for (String line : inputLines) {
            numberList.add(Integer.parseInt(line));
        }

        System.out.println("Quantum entanglement of the first group of packages in the ideal configuration: " + quantumEntanglement(numberList));
    }

    public static Long quantumEntanglement(final ArrayList<Integer> numberList) {

        ArrayList<ArrayList<Integer>> group1Final = new ArrayList<>();
        ArrayList<ArrayList<Integer>> group2Final = new ArrayList<>();
        ArrayList<ArrayList<Integer>> group3Final = new ArrayList<>();
        ArrayList<Long> quantumEntanglementValues = new ArrayList<>();

        for (int group1Size = 1; (group1Size <= numberList.size()-2) && group1Final.size() == 0; group1Size++) {
            ArrayList<Integer> group1Possibilities = selectNumbersFromList(group1Size, numberList, 3);

            for (int i = 0; i < group1Possibilities.size(); i+=group1Size) {
                ArrayList<Integer> group1 = new ArrayList<>();
                for (int j = 0; j < group1Size; j++) {
                    group1.add(group1Possibilities.get(i+j));
                }

                final ArrayList<Integer> remainingNumberList = removeGroupFromList(group1, numberList);
                ArrayList<Integer> group2;
                for (int group2Size = 1; group2Size < numberList.size()-group1Size-2; group2Size++) {
                    group2 = selectNumbersFromList(group2Size, remainingNumberList, 2);
                    if(0 < group2.size()) {
                        group2 = new ArrayList<>(group2.subList(0, group2Size));
                        group1Final.add(new ArrayList<>(group1));
                        group2Final.add(new ArrayList<>(group2));
                        group3Final.add(removeGroupFromList(group2, remainingNumberList));
                        quantumEntanglementValues.add(calculateQuantumEntanglementValue(group1));
                        break;
                    }
                }
                if(group1Final.size() != 0)
                    break;
            }
        }

        System.out.println("Group1:     Quantum:    Group2:        Group3:");
        for (int i = 0; i < group1Final.size(); i++) {
            for (int j = 0; j < group1Final.get(i).size(); j++) {
                System.out.print(group1Final.get(i).get(j) + " ");
            }
            System.out.print("     ");
            System.out.print("(QE=" + quantumEntanglementValues.get(i) + ")      ");
            for (int j = 0; j < group2Final.get(i).size(); j++) {
                System.out.print(group2Final.get(i).get(j) + " ");
            }
            System.out.print("        ");
            for (int j = 0; j < group3Final.get(i).size(); j++) {
                System.out.print(group3Final.get(i).get(j) + " ");
            }
            System.out.println("");
        }

        return 0 < quantumEntanglementValues.size() ? quantumEntanglementValues.get(0) : 0;
    }

    private static ArrayList<Integer> selectNumbersFromList(int groupSize, final ArrayList<Integer> numberList, int divider) {
        ArrayList<Integer> numberGroup;

        int allWeight = getSumOfNumberList(numberList);
        numberGroup = recursiveSelection(0, groupSize-1, null, numberList, allWeight, divider);
        if(divider == 3)
            removeRepeatingNumberGroups(groupSize, numberGroup);

        return numberGroup;
    }

    private static ArrayList<Integer> recursiveSelection(int start, int stop, ArrayList<Integer> toAdd, final ArrayList<Integer> numberList, int allWeight, int divider) {
        if(toAdd == null) {
            toAdd = new ArrayList<>();
        }
        ArrayList<Integer> subGroup;
        ArrayList<Integer> returnGroup = new ArrayList<>();

        for (int i = 0; i < numberList.size(); i++) {
            if(i == 0)
                toAdd.add(numberList.get(i));
            else
                toAdd.set(toAdd.size()-1, numberList.get(i));

            if(start < stop) {
                subGroup = recursiveSelection(start+1, stop, toAdd, numberList, allWeight, divider);
                returnGroup.addAll(subGroup);
            }
            else if(!numbersRepeat(toAdd) && groupHasCorrectWeight(toAdd, numberList, allWeight, divider)) {
                returnGroup.addAll(toAdd);
            }
        }
        toAdd.remove(toAdd.size()-1);
        return returnGroup;
    }

    private static boolean numbersRepeat(final ArrayList<Integer> numbers) {
        if(numbers == null || numbers.size() < 2)
            return false;

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if(i != j && Objects.equals(numbers.get(i), numbers.get(j)))
                    return true;
            }
        }
        return false;
    }

    private static void removeRepeatingNumberGroups(Integer groupSize, ArrayList<Integer> numbers) {
        if(numbers == null || numbers.size() < 2)
            return;

        if(numbers.size() % groupSize != 0) {
            System.out.println("ERROR! Function removeRepeatingNumberGroups called with unfitting groupSize argument.");
            System.exit(-1);
        }

        ArrayList<Integer> groupA;
        ArrayList<Integer> groupB;
        for (int i = 0; i < numbers.size(); i+=groupSize) {
            for (int j = 0; j < numbers.size(); j+=groupSize) {
                if(i == j)
                    continue;

                groupA =  new ArrayList<>();
                groupB =  new ArrayList<>();

                for (int k = 0; k < groupSize; k++) {
                    groupA.add(numbers.get(i+k));
                    groupB.add(numbers.get(j+k));
                }

                if(sameNumberGroups(groupA, groupB)) {

                    for (int k = 0; k < groupSize; k++) {
                        numbers.remove(j);
                    }

                    if(j < i)
                        i -= groupSize;
                    j -= groupSize;
                }
            }
        }
    }

    private static boolean sameNumberGroups(ArrayList<Integer> groupA, ArrayList<Integer> groupB) {
        for (int i = 0; i < groupA.size(); i++) {
            if(!groupB.contains(groupA.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static int getSumOfNumberList(final ArrayList<Integer> numberList) {
        int sum = 0;
        for (int i = 0; i < numberList.size(); i++) {
            sum += numberList.get(i);
        }
        return sum;
    }

    private static boolean groupHasCorrectWeight(ArrayList<Integer> group, final ArrayList<Integer> numberList, int allWeight, int divider) {
        return getSumOfNumberList(group) == allWeight / divider;
    }

    private static ArrayList<Integer> removeGroupFromList(final ArrayList<Integer> group, final ArrayList<Integer> numberList) {
        ArrayList<Integer> returnGroup = new ArrayList<>(numberList);

        for (int i = 0; i < group.size(); i++) {
            returnGroup.remove(group.get(i));
        }
        return returnGroup;
    }

    private static Long calculateQuantumEntanglementValue(final ArrayList<Integer> group) {
        Long result = 1L;
        for (int i = 0; i < group.size(); i++) {
            result *= group.get(i);
        }
        return result;
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
