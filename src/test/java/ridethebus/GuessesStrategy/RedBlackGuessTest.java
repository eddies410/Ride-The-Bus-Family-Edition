package ridethebus.GuessesStrategy;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import static org.junit.jupiter.api.Assertions.*;

public class RedBlackGuessTest {

    @Test
    void redGuessCorrectForRedCard() {
        assertTrue(new RedBlackGuess(Card.RED).isCorrect(new Card(Card.HEARTS, 5)));
    }

    @Test
    void redGuessWrongForBlackCard() {
        assertFalse(new RedBlackGuess(Card.RED).isCorrect(new Card(Card.CLUBS, 5)));
    }

    @Test
    void blackGuessCorrectForBlackCard() {
        assertTrue(new RedBlackGuess(Card.BLACK).isCorrect(new Card(Card.SPADES, 5)));
    }

    @Test
    void blackGuessWrongForRedCard() {
        assertFalse(new RedBlackGuess(Card.BLACK).isCorrect(new Card(Card.DIAMONDS, 5)));
    }

    @Test
    void invalidGuessThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new RedBlackGuess("Purple"));
    }

    @Test
    void guessDescriptionContainsGuess() {
        assertTrue(new RedBlackGuess(Card.RED).getGuessDescription().contains("Red"));
    }
}