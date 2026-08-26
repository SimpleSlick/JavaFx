package com.numbergame;

import java.util.Random;

public class NumberGame {

    private final int maxAttempts = 7;

    private int secretNumber;
    private int attemptsLeft;

    private int round = 0;
    private int totalRoundsWon = 0;
    private int totalGamesPlayed = 0;

    private boolean gameActive = false;

    private final Random random = new Random();

    // START NEW ROUND

    public void startNewRound() {
        round++;
        totalGamesPlayed++;
        secretNumber = random.nextInt(100) + 1;
        System.out.println(secretNumber); // to check if the number is working or not
        attemptsLeft = maxAttempts;
        gameActive = true;
    }

    // PROCESS GUESS

    public GuessResult makeGuess(int guess) {

        if (!gameActive) {
            return GuessResult.INACTIVE;
        }

        if (guess < 1 || guess > 100) {
            return GuessResult.INVALID_RANGE;
        }
        attemptsLeft--;

        // Correct
        if (guess == secretNumber) {
            gameActive = false;
            totalRoundsWon++;
            return GuessResult.CORRECT;
        }

        // No attempts remaining
        if (attemptsLeft == 0) {
            gameActive = false;
            return GuessResult.GAME_OVER;
        }

        // Too low
        if (guess < secretNumber) {
            return GuessResult.TOO_LOW;
        }

        // Too high
        return GuessResult.TOO_HIGH;
    }

    // GETTERS
    public int getSecretNumber() {
        return secretNumber;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getRound() {
        return round;
    }

    public int getTotalRoundsWon() {
        return totalRoundsWon;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public boolean isGameActive() {
        return gameActive;
    }

    // RESET GAME
    public void resetGame() {
        round = 0;
        totalRoundsWon = 0;
        totalGamesPlayed = 0;
        secretNumber = 0;
        attemptsLeft = 0;
        gameActive = false;
    }

    // GUESS RESULT ENUM
    public enum GuessResult {
        CORRECT,
        TOO_LOW,
        TOO_HIGH,
        GAME_OVER,
        INVALID_RANGE,
        INACTIVE
    }
}