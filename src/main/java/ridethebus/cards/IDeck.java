package ridethebus.cards;

import java.util.List;

public interface IDeck {
    ICard deal();
    void shuffle();
    int size();
    boolean isEmpty();
    List<ICard> dealHand(int numberOfCards);
}