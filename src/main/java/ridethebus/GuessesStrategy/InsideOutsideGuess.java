package ridethebus.GuessesStrategy;

import ridethebus.cards.ICard;

/**
 * Represents an inside or outside guess.
 * Third question in the guessing round.
 * Compares the new card value to the range of the first two cards.
 */
public class InsideOutsideGuess extends BaseGuess {

    public static final String INSIDE = "Inside";
    public static final String OUTSIDE = "Outside";

    private final ICard firstCard;
    private final ICard secondCard;

    public InsideOutsideGuess(String guess, ICard firstCard, ICard secondCard) {
        super(guess, INSIDE, OUTSIDE);
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    @Override
    public boolean isCorrect(ICard card) {
        int low = Math.min(firstCard.getValue(), secondCard.getValue());
        int high = Math.max(firstCard.getValue(), secondCard.getValue());
        boolean isInside = card.getValue() > low && card.getValue() < high;
        return guess.equals(INSIDE) ? isInside : !isInside;
    }

    @Override
    public String getGuessDescription() {
        return "Inside or Outside: " + guess + " of "
                + firstCard.getDisplayName() + " and " + secondCard.getDisplayName();
    }
}