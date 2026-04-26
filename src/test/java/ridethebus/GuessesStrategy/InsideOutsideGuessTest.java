package ridethebus.GuessesStrategy;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import static org.junit.jupiter.api.Assertions.*;

public class InsideOutsideGuessTest {

    private final ICard three = new Card(Card.HEARTS, 3);
    private final ICard nine = new Card(Card.SPADES, 9);

    @Test
    void insideGuessCorrectForCardInRange() {
        assertTrue(new InsideOutsideGuess(InsideOutsideGuess.INSIDE, three, nine)
                .isCorrect(new Card(Card.CLUBS, 6)));
    }

    @Test
    void insideGuessWrongForCardOutsideRange() {
        assertFalse(new InsideOutsideGuess(InsideOutsideGuess.INSIDE, three, nine)
                .isCorrect(new Card(Card.DIAMONDS, 2)));
    }

    @Test
    void outsideGuessCorrectForCardOutsideRange() {
        assertTrue(new InsideOutsideGuess(InsideOutsideGuess.OUTSIDE, three, nine)
                .isCorrect(new Card(Card.HEARTS, 1)));
    }

    @Test
    void outsideGuessWrongForCardInRange() {
        assertFalse(new InsideOutsideGuess(InsideOutsideGuess.OUTSIDE, three, nine)
                .isCorrect(new Card(Card.CLUBS, 5)));
    }

    @Test
    void invalidGuessThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new InsideOutsideGuess("Between", three, nine));
    }

    @Test
    void descriptionContainsBothCardNames() {
        String desc = new InsideOutsideGuess(InsideOutsideGuess.INSIDE, three, nine)
                .getGuessDescription();
        assertTrue(desc.contains(three.getDisplayName()));
        assertTrue(desc.contains(nine.getDisplayName()));
    }

    @Test
    void worksWithReversedCardOrder() {
        // Should work even if first card is higher than second
        assertTrue(new InsideOutsideGuess(InsideOutsideGuess.INSIDE, nine, three)
                .isCorrect(new Card(Card.CLUBS, 6)));
    }
}
