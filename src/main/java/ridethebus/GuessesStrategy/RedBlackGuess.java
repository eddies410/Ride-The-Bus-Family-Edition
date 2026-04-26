package ridethebus.GuessesStrategy;

import ridethebus.cards.Card;
import ridethebus.cards.ICard;

public class RedBlackGuess extends BaseGuess {

    public RedBlackGuess(String guess) {
        super(guess, Card.RED, Card.BLACK);
    }

    @Override
    public boolean isCorrect(ICard card) {
        return card.getColor().equals(guess);
    }
}