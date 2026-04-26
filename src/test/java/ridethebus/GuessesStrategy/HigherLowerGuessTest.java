package ridethebus.GuessesStrategy;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import static org.junit.jupiter.api.Assertions.*;

public class HigherLowerGuessTest {

    private final ICard fiveOfHearts = new Card(Card.HEARTS, 5);

    @Test
    void higherGuessCorrectForHigherCard() {
        assertTrue(new HigherLowerGuess(HigherLowerGuess.HIGHER, fiveOfHearts)
                .isCorrect(new Card(Card.SPADES, 9)));
    }

    @Test
    void higherGuessWrongForLowerCard() {
        assertFalse(new HigherLowerGuess(HigherLowerGuess.HIGHER, fiveOfHearts)
                .isCorrect(new Card(Card.CLUBS, 3)));
    }

    @Test
    void lowerGuessCorrectForLowerCard() {
        assertTrue(new HigherLowerGuess(HigherLowerGuess.LOWER, fiveOfHearts)
                .isCorrect(new Card(Card.DIAMONDS, 2)));
    }

    @Test
    void lowerGuessWrongForHigherCard() {
        assertFalse(new HigherLowerGuess(HigherLowerGuess.LOWER, fiveOfHearts)
                .isCorrect(new Card(Card.HEARTS, 8)));
    }

    @Test
    void invalidGuessThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new HigherLowerGuess("Sideways", fiveOfHearts));
    }

    @Test
    void descriptionContainsFirstCardName() {
        String desc = new HigherLowerGuess(HigherLowerGuess.HIGHER, fiveOfHearts)
                .getGuessDescription();
        assertTrue(desc.contains(fiveOfHearts.getDisplayName()));
    }
}