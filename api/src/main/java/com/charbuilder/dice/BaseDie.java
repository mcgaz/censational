package main.java.com.charbuilder.dice;

import java.util.Arrays;
import java.util.Random;

public class BaseDie {
    public static int[] roll (int dieType, int numberOfDiceRolled) {
        long seed = 123L;
        Random rand = new Random(seed);
        int[] diceArrayRolled = new int[numberOfDiceRolled];

        for(int i = 0; i < numberOfDiceRolled; i++) {
            int randomInt = rand.nextInt(dieType) + 1;
            diceArrayRolled[i] = randomInt;
        }

        Arrays.sort(diceArrayRolled);

        return diceArrayRolled;
    }

    public static int[] rollHighest (int dieType, int numberOfDiceRolled, int numberOfDiceNeeded) {
        try {
            if(numberOfDiceNeeded > numberOfDiceRolled) { throw new ArithmeticException(); }
            int[] diceArray = roll(dieType, numberOfDiceRolled);
            int rangeStart = numberOfDiceRolled - numberOfDiceNeeded;
            int rangeEnd = numberOfDiceRolled + 1;

            return Arrays.copyOfRange(diceArray, rangeStart, rangeEnd);
        }
        catch (ArithmeticException e) {
            System.out.println("More dice needed than rolled: " + e.getMessage());
        }
        return new int[0];
    }

    public static int rollOne (int dieType) {
        long seed = 123L;
        Random rand = new Random(seed);
        return rand.nextInt(dieType) + 1;
    }
}
