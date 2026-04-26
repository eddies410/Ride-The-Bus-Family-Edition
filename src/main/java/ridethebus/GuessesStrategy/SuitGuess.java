package ridethebus.GuessesStrategy;

import ridethebus.cards.Card;
import ridethebus.cards.ICard;

/**
 * Represents a suit guess.
 * Fourth and final question in the guessing round.
 */
public class SuitGuess extends BaseGuess {

    public SuitGuess(String guess) {
        super(guess, Card.HEARTS, Card.DIAMONDS, Card.CLUBS, Card.SPADES);
    }

    @Override
    public boolean isCorrect(ICard card) {
        return card.getSuit().equals(guess);
    }
}