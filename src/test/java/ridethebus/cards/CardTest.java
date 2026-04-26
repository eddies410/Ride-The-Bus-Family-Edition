package ridethebus.cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void heartsIsRed() {
        assertEquals(Card.RED, new Card(Card.HEARTS, 5).getColor());
    }

    @Test
    void diamondsIsRed() {
        assertEquals(Card.RED, new Card(Card.DIAMONDS, 3).getColor());
    }

    @Test
    void clubsIsBlack() {
        assertEquals(Card.BLACK, new Card(Card.CLUBS, 5).getColor());
    }

    @Test
    void spadesIsBlack() {
        assertEquals(Card.BLACK, new Card(Card.SPADES, 3).getColor());
    }

    @Test
    void aceDisplaysCorrectly() {
        assertEquals("Ace of Spades", new Card(Card.SPADES, 1).getDisplayName());
    }

    @Test
    void jackDisplaysCorrectly() {
        assertEquals("Jack of Hearts", new Card(Card.HEARTS, 11).getDisplayName());
    }

    @Test
    void queenDisplaysCorrectly() {
        assertEquals("Queen of Diamonds", new Card(Card.DIAMONDS, 12).getDisplayName());
    }

    @Test
    void kingDisplaysCorrectly() {
        assertEquals("King of Clubs", new Card(Card.CLUBS, 13).getDisplayName());
    }

    @Test
    void numberCardDisplaysCorrectly() {
        assertEquals("7 of Hearts", new Card(Card.HEARTS, 7).getDisplayName());
    }

    @Test
    void toStringMatchesDisplayName() {
        Card card = new Card(Card.SPADES, 5);
        assertEquals(card.getDisplayName(), card.toString());
    }

    @Test
    void getSuitReturnsCorrectSuit() {
        assertEquals(Card.HEARTS, new Card(Card.HEARTS, 5).getSuit());
    }

    @Test
    void getValueReturnsCorrectValue() {
        assertEquals(7, new Card(Card.CLUBS, 7).getValue());
    }
}