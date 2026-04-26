package ridethebus.GuessesStrategy;

import ridethebus.cards.ICard;

/**
 * Represents a higher or lower guess.
 * Second question in the guessing round.
 * Compares the new card value to the first card dealt.
 */
public class HigherLowerGuess extends BaseGuess {

    public static final String HIGHER = "Higher";
    public static final String LOWER = "Lower";

    private final ICard firstCard;

    public HigherLowerGuess(String guess, ICard firstCard) {
        super(guess, HIGHER, LOWER);
        this.firstCard = firstCard;
    }

    @Override
    public boolean isCorrect(ICard card) {
        return guess.equals(HIGHER)
                ? card.getValue() > firstCard.getValue()
                : card.getValue() < firstCard.getValue();
    }

    @Override
    public String getGuessDescription() {
        return "Higher or Lower: " + guess + " than " + firstCard.getDisplayName();
    }
}