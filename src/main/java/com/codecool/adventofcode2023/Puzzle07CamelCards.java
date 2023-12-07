package com.codecool.adventofcode2023;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Puzzle07CamelCards {
    public static void main(String[] args) {
        ArrayList<String> inputLines = new ArrayList<>(Arrays.asList(readLines("src/main/resources/2023/Camel Cards.txt")));
        if(inputLines.size() == 0)
            System.out.println("Input file not found");

        partOne(inputLines);
    }

    private static void partOne(ArrayList<String> inputLines) {
        ArrayList<String[]> hands = new ArrayList<>();
        for (String currentRow : inputLines) {
            String[] handAndBet = currentRow.split(" ");
            addHandV1(hands, new String[]{ String.valueOf(getHandStrengthV1(handAndBet[0])), handAndBet[0], handAndBet[1] });
        }
        long totalWinnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            System.out.println(Arrays.toString(hands.get(i)));
            totalWinnings += (i + 1) * Long.parseLong(hands.get(i)[2]);
        }
        System.out.println("total winnings: " + totalWinnings);
    }

    private static int getHandStrengthV1(String hand) {
        Map<Character, Integer> cardMap = new HashMap<>();
        for (int i = 0; i < hand.length(); i++) {
            char card = hand.charAt(i);
            Integer value = cardMap.get(card);
            cardMap.put(card, value == null ? 1 : value + 1);
        }
        int pairs = 0;
        boolean drill = false;
        for (Map.Entry<Character, Integer> entry : cardMap.entrySet()) {
            switch (entry.getValue()) {
                case 5:
                    return 6;
                case 4:
                    return 5;
                case 3:
                    drill = true;
                    break;
                case 2:
                    pairs++;
            };
        }
        if (drill) {
           if (0 < pairs) {
               return 4;
           }
           return 3;
        }
        return Math.max(0, pairs);
    }

    private static void addHandV1(ArrayList<String[]> hands, String[] newHand) {
        if (hands.size() == 0) {
            hands.add(newHand);
            return;
        }
        int newHandValue = Integer.parseInt(newHand[0]);
        for (int i = 0; i < hands.size(); i++) {
            String[] currentHand = hands.get(i);
            int currentHandValue = Integer.parseInt(currentHand[0]);
            if (newHandValue < currentHandValue) {
                hands.add(i, newHand);
                return;
            } else if (newHandValue == currentHandValue) {
                for (int j = 0; j < currentHand[1].length(); j++) {
                    int currentCard = evaluateCardV1(currentHand[1].charAt(j));
                    int newCard = evaluateCardV1(newHand[1].charAt(j));
                    if (newCard < currentCard) {
                        hands.add(i, newHand);
                        return;
                    }
                    if (currentCard < newCard) {
                        break;
                    }
                }
            }
        }
        hands.add(newHand);
    }

    private static int evaluateCardV1(char card) {
        return switch (card) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 11;
            case 'T' -> 10;
            default -> Integer.parseInt("" + card);
        };
    }

    private static void partTwo(ArrayList<String> inputLines) {
        ArrayList<String[]> hands = new ArrayList<>();
        for (String currentRow : inputLines) {
            String[] handAndBet = currentRow.split(" ");
            addHandV2(hands, new String[]{ String.valueOf(getHandStrengthV2(handAndBet[0])), handAndBet[0], handAndBet[1] });
        }
        long totalWinnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            System.out.println(Arrays.toString(hands.get(i)));
            totalWinnings += (i + 1) * Long.parseLong(hands.get(i)[2]);
        }
        System.out.println("total winnings: " + totalWinnings);
    }

    private static int getHandStrengthV2(String hand) {
        Map<Character, Integer> cardMap = new HashMap<>();
        for (int i = 0; i < hand.length(); i++) {
            char card = hand.charAt(i);
            Integer value = cardMap.get(card);
            cardMap.put(card, value == null ? 1 : value + 1);
        }
        int jokers = cardMap.getOrDefault('J', 0);
        int pairs = 0;
        boolean drill = false;
        for (Map.Entry<Character, Integer> entry : cardMap.entrySet()) {
            switch (entry.getValue()) {
                case 5:
                    return 6;
                case 4:
                    if (jokers == 4 || jokers == 1) {
                        return 6;
                    }
                    return 5;
                case 3:
                    drill = true;
                    break;
                case 2:
                    pairs++;
            };
        }
        if (drill) {
            if (0 < pairs) {
                if (jokers == 3 || jokers == 2) {
                    return 6;
                }
                return 4;
            }
            if (jokers == 3 || jokers == 1) {
                return 5;
            }
            return 3;
        }
        if (pairs == 2 && jokers == 2) {
            return 5;
        }
        if (pairs == 2 && jokers == 1) {
            return 4;
        }
        if (pairs == 1 && (jokers == 1 || jokers == 2)) {
            return 3;
        }
        if (jokers == 1) {
            return 1;
        }

        return Math.max(0, pairs);
    }

    private static void addHandV2(ArrayList<String[]> hands, String[] newHand) {
        if (hands.size() == 0) {
            hands.add(newHand);
            return;
        }
        int newHandValue = Integer.parseInt(newHand[0]);
        for (int i = 0; i < hands.size(); i++) {
            String[] currentHand = hands.get(i);
            int currentHandValue = Integer.parseInt(currentHand[0]);
            if (newHandValue < currentHandValue) {
                hands.add(i, newHand);
                return;
            } else if (newHandValue == currentHandValue) {
                for (int j = 0; j < currentHand[1].length(); j++) {
                    int currentCard = evaluateCardV2(currentHand[1].charAt(j));
                    int newCard = evaluateCardV2(newHand[1].charAt(j));
                    if (newCard < currentCard) {
                        hands.add(i, newHand);
                        return;
                    }
                    if (currentCard < newCard) {
                        break;
                    }
                }
            }
        }
        hands.add(newHand);
    }

    private static int evaluateCardV2(char card) {
        return switch (card) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 1;
            case 'T' -> 10;
            default -> Integer.parseInt("" + card);
        };
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
