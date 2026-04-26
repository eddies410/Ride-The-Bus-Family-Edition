package ridethebus.cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    void freshDeckHas52Cards() {
        assertEquals(52, new Deck().size());
    }

    @Test
    void dealReducesDeckByOne() {
        Deck deck = new Deck();
        deck.deal();
        assertEquals(51, deck.size());
    }

    @Test
    void dealHandReducesDeckByHandSize() {
        Deck deck = new Deck();
        deck.dealHand(4);
        assertEquals(48, deck.size());
    }

    @Test
    void emptyDeckThrowsOnDeal() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) deck.deal();
        assertThrows(IllegalStateException.class, deck::deal);
    }

    @Test
    void deckIsNotEmptyWhenFull() {
        assertFalse(new Deck().isEmpty());
    }

    @Test
    void deckIsEmptyAfterAllCardsDealt() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) deck.deal();
        assertTrue(deck.isEmpty());
    }

    @Test
    void addCardIncreasesDeckSize() {
        Deck deck = new Deck();
        deck.addCard(new Joker());
        assertEquals(53, deck.size());
    }

    @Test
    void dealHandReturnsCorrectNumberOfCards() {
        Deck deck = new Deck();
        assertEquals(4, deck.dealHand(4).size());
    }
}