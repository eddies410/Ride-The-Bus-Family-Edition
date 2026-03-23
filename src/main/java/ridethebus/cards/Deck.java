package ridethebus.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements IDeck {

    private static final String[] SUITS = {
            Card.HEARTS, Card.DIAMONDS, Card.CLUBS, Card.SPADES
    };

    private final List<ICard> cards = new ArrayList<>();

    public Deck() {
        buildDeck();
        shuffle();
    }

    private void buildDeck() {
        for (String suit : SUITS) {
            for (int value = 1; value <= 13; value++) {
                cards.add(new Card(suit, value));
            }
        }
    }

    @Override
    public ICard deal() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot deal from an empty deck");
        }
        return cards.remove(cards.size() - 1);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public List<ICard> dealHand(int numberOfCards) {
        List<ICard> hand = new ArrayList<>();
        for (int i = 0; i < numberOfCards; i++) {
            hand.add(deal());
        }
        return hand;
    }
}
