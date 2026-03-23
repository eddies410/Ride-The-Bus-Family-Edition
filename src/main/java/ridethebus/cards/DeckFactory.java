package ridethebus.cards;

public class DeckFactory {

    public static IDeck createDeck() {
        return new Deck();
    }
}