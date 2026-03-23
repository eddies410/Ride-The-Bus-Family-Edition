package ridethebus.cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void redCardReturnsRedColor() {
        ICard heart = new Card(Card.HEARTS, 5);
        ICard diamond = new Card(Card.DIAMONDS, 3);
        assertEquals(Card.RED, heart.getColor());
        assertEquals(Card.RED, diamond.getColor());
    }

    @Test
    void blackCardReturnsBlackColor() {
        ICard club = new Card(Card.CLUBS, 5);
        ICard spade = new Card(Card.SPADES, 3);
        assertEquals(Card.BLACK, club.getColor());
        assertEquals(Card.BLACK, spade.getColor());
    }

    @Test
    void aceDisplaysCorrectly() {
        ICard ace = new Card(Card.SPADES, 1);
        assertEquals("Ace of Spades", ace.getDisplayName());
    }

    @Test
    void faceCardsDisplayCorrectly() {
        assertEquals("Jack of Hearts", new Card(Card.HEARTS, 11).getDisplayName());
        assertEquals("Queen of Diamonds", new Card(Card.DIAMONDS, 12).getDisplayName());
        assertEquals("King of Clubs", new Card(Card.CLUBS, 13).getDisplayName());
    }
}