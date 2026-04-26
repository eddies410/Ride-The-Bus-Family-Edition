package ridethebus.cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckFactoryTest {

    @Test
    void standardDeckHas52Cards() {
        assertEquals(52, DeckFactory.createDeck().size());
    }

    @Test
    void jokerDeckHas54Cards() {
        assertEquals(54, DeckFactory.createDeck("joker").size());
    }

    @Test
    void shortDeckHas36Cards() {
        assertEquals(36, DeckFactory.createDeck("short").size());
    }

    @Test
    void doubleDeckHas104Cards() {
        assertEquals(104, DeckFactory.createDeck("double").size());
    }

    @Test
    void unknownTypeCreatesStandardDeck() {
        assertEquals(52, DeckFactory.createDeck("unknown").size());
    }

    @Test
    void noArgCreatesDeckOf52() {
        assertEquals(52, DeckFactory.createDeck().size());
    }

    @Test
    void shortDeckHasNoCardsBelow6ExceptAces() {
        IDeck deck = DeckFactory.createDeck("short");
        while (!deck.isEmpty()) {
            ICard card = deck.deal();
            assertTrue(card.getValue() >= 6 || card.getValue() == 1,
                    "Short deck should only contain Aces or cards 6 and above, found: "
                            + card.getDisplayName());
        }
    }

    @Test
    void doubleDeckCanDeal104Cards() {
        IDeck deck = DeckFactory.createDeck("double");
        int count = 0;
        while (!deck.isEmpty()) {
            deck.deal();
            count++;
        }
        assertEquals(104, count);
    }
}