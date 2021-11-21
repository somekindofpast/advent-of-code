package com.codecool.adventofcode2015;

import java.util.ArrayList;

public class Puzzle10ElvesLookElvesSay {
    public static void main(String[] args) {
        //String input = "1321131112";
        //System.out.println("The length of the result is: ");

        System.out.println("The length of the result in PART ONE of this puzzle is: " + lookAndSay("1321131112", 40).length());
        System.out.println("The length of the result in PART TWO of this puzzle is: " + lookAndSay("1321131112", 50).length());
    }

    private static String lookAndSay(String input, int timesToRun) {
        ArrayList<Integer[]> numberList = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if(numberList.size() == 0 || numberList.get(numberList.size()-1)[1] != input.charAt(i)-48) {
                numberList.add(new Integer[]{0, input.charAt(i)-48});
            }
            numberList.get(numberList.size()-1)[0]++;
        }

        for (int i = 0; i < numberList.size(); i++) {
            output.append(numberList.get(i)[0]);
            output.append(numberList.get(i)[1]);
        }

        if(1 < timesToRun)
            return lookAndSay(output.toString(), timesToRun-1);
        else
            return output.toString();
    }
}
