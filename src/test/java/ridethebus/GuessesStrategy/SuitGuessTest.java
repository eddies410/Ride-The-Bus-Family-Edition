package ridethebus.GuessesStrategy;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import static org.junit.jupiter.api.Assertions.*;

public class SuitGuessTest {

    @Test
    void correctForMatchingSuit() {
        assertTrue(new SuitGuess(Card.HEARTS).isCorrect(new Card(Card.HEARTS, 5)));
    }

    @Test
    void wrongForDifferentSuit() {
        assertFalse(new SuitGuess(Card.HEARTS).isCorrect(new Card(Card.SPADES, 5)));
    }

    @Test
    void invalidSuitThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new SuitGuess("Stars"));
    }

    @Test
    void allFourSuitsAreValid() {
        assertDoesNotThrow(() -> new SuitGuess(Card.HEARTS));
        assertDoesNotThrow(() -> new SuitGuess(Card.DIAMONDS));
        assertDoesNotThrow(() -> new SuitGuess(Card.CLUBS));
        assertDoesNotThrow(() -> new SuitGuess(Card.SPADES));
    }

    @Test
    void descriptionContainsGuess() {
        assertTrue(new SuitGuess(Card.HEARTS).getGuessDescription().contains("Hearts"));
    }
}