package com.roadmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    // High score lưu theo difficulty
    private static final Map<String, Integer> highScores = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("🎯 Welcome to the Number Guessing Game!");

        boolean playAgain = true;

        while (playAgain) {
            playGame();
            playAgain = askPlayAgain();
        }

        System.out.println("👋 Thanks for playing! Bye!");
    }

    private static void playGame() {
        int numberToGuess = random.nextInt(100) + 1;
        int maxAttempts = chooseDifficulty();
        int attempts = 0;

        System.out.println("\nI'm thinking of a number between 1 and 100.");
        System.out.println("Let's start the game!");

        long startTime = System.currentTimeMillis();

        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int guess = getValidNumber();
            attempts++;

            if (guess == numberToGuess) {
                long endTime = System.currentTimeMillis();
                long timeTaken = (endTime - startTime) / 1000;

                System.out.println("🎉 Congratulations! You guessed it in " + attempts + " attempts.");
                System.out.println("⏱ Time taken: " + timeTaken + " seconds");

                updateHighScore(maxAttempts, attempts);
                return;
            }

            if (guess > numberToGuess) {
                System.out.println("❌ Incorrect! The number is less than " + guess);
            } else {
                System.out.println("❌ Incorrect! The number is greater than " + guess);
            }

            // Hint system (khi gần hết lượt)
            if (maxAttempts - attempts == 1) {
                giveHint(numberToGuess);
            }
        }

        System.out.println("💀 Game Over! The number was: " + numberToGuess);
    }

    private static int chooseDifficulty() {
        System.out.println("\nSelect difficulty:");
        System.out.println("1. Easy (10 chances)");
        System.out.println("2. Medium (5 chances)");
        System.out.println("3. Hard (3 chances)");

        while (true) {
            System.out.print("Enter your choice: ");
            int choice = getValidNumber();

            switch (choice) {
                case 1:
                    System.out.println("🟢 Easy mode selected!");
                    return 10;
                case 2:
                    System.out.println("🟡 Medium mode selected!");
                    return 5;
                case 3:
                    System.out.println("🔴 Hard mode selected!");
                    return 3;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private static int getValidNumber() {
        while (!scanner.hasNextInt()) {
            System.out.print("⚠️ Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void giveHint(int number) {
        System.out.println("💡 Hint activated!");
        if (number % 2 == 0) {
            System.out.println("👉 The number is EVEN.");
        } else {
            System.out.println("👉 The number is ODD.");
        }
    }

    private static void updateHighScore(int difficulty, int attempts) {
        String key = String.valueOf(difficulty);

        if (!highScores.containsKey(key) || attempts < highScores.get(key)) {
            highScores.put(key, attempts);
            System.out.println("🏆 New High Score for this difficulty!");
        }

        System.out.println("📊 High Score: " + highScores.get(key) + " attempts");
    }

    private static boolean askPlayAgain() {
        System.out.print("\nDo you want to play again? (y/n): ");
        String input = scanner.next().toLowerCase();
        return input.equals("y");
    }
}