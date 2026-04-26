package ridethebus.characters;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import static org.junit.jupiter.api.Assertions.*;

public class HumanPlayerTest {

    @Test
    void playerStartsWithZeroScore() {
        assertEquals(0, new HumanPlayer("Alice").getScore());
    }

    @Test
    void addPointsIncreasesScore() {
        HumanPlayer p = new HumanPlayer("Alice");
        p.addPoints(5);
        assertEquals(5, p.getScore());
    }

    @Test
    void multipleAddPointsAccumulate() {
        HumanPlayer p = new HumanPlayer("Alice");
        p.addPoints(3);
        p.addPoints(7);
        assertEquals(10, p.getScore());
    }

    @Test
    void handStartsEmpty() {
        assertTrue(new HumanPlayer("Alice").getHand().isEmpty());
    }

    @Test
    void addCardToHandIncreasesHandSize() {
        HumanPlayer p = new HumanPlayer("Alice");
        p.addCardToHand(new Card(Card.HEARTS, 5));
        assertEquals(1, p.getHand().size());
    }

    @Test
    void clearHandRemovesAllCards() {
        HumanPlayer p = new HumanPlayer("Alice");
        p.addCardToHand(new Card(Card.HEARTS, 5));
        p.addCardToHand(new Card(Card.SPADES, 3));
        p.clearHand();
        assertTrue(p.getHand().isEmpty());
    }

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Bob", new HumanPlayer("Bob").getName());
    }

    @Test
    void toStringIncludesNameAndScore() {
        HumanPlayer p = new HumanPlayer("Alice");
        p.addPoints(10);
        assertTrue(p.toString().contains("Alice"));
        assertTrue(p.toString().contains("10"));
    }

    @Test
    void handIsImmutable() {
        HumanPlayer p = new HumanPlayer("Alice");
        p.addCardToHand(new Card(Card.HEARTS, 5));
        assertThrows(UnsupportedOperationException.class,
                () -> p.getHand().add(new Card(Card.CLUBS, 3)));
    }
}