package ridethebus.GuessesStrategy;

import ridethebus.game.IGuess;

import java.util.Arrays;

public abstract class BaseGuess implements IGuess {
    protected final String guess;

    protected BaseGuess(String guess, String... validOptions) {
        if (Arrays.stream(validOptions).noneMatch(guess::equals)) {
            throw new IllegalArgumentException("Invalid guess: " + guess);
        }
        this.guess = guess;
    }

    @Override
    public String getGuessDescription() {
        return getClass().getSimpleName() + ": " + guess;
    }
}