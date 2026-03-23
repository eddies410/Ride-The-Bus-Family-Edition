package ridethebus.cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    void freshDeckHas52Cards() {
        IDeck deck = new Deck();
        assertEquals(52, deck.size());
    }

    @Test
    void dealingReducesDeckSizeByOne() {
        IDeck deck = new Deck();
        deck.deal();
        assertEquals(51, deck.size());
    }

    @Test
    void dealingHandReducesDeckByHandSize() {
        IDeck deck = new Deck();
        deck.dealHand(4);
        assertEquals(48, deck.size());
    }

    @Test
    void dealingFromEmptyDeckThrowsException() {
        IDeck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.deal();
        }
        assertThrows(IllegalStateException.class, deck::deal);
    }

    @Test
    void factoryCreatesStandardDeck() {
        IDeck deck = DeckFactory.createDeck();
        assertEquals(52, deck.size());
    }
}