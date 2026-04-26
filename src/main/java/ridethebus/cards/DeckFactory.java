package ridethebus.cards;

/**
 * Factory that creates different deck types based on game mode.
 * Demonstrates the Factory pattern — callers don't need to know
 * how each deck is constructed, just which type they want.
 *
 * Supported types:
 *   standard  — 52 cards, Ace through King in 4 suits
 *   joker     — 54 cards, standard + 2 Jokers
 *   short     — 36 cards, 6 through King (removes low cards)
 *   double    — 104 cards, two standard decks shuffled together
 */
public class DeckFactory {

    public static IDeck createDeck(String type) {
        return switch (type) {
            case "joker"   -> createJokerDeck();
            case "short"   -> createShortDeck();
            case "double"  -> createDoubleDeck();
            default        -> createStandardDeck();
        };
    }

    public static IDeck createDeck() {
        return createStandardDeck();
    }

    private static IDeck createStandardDeck() {
        return new Deck();
    }

    private static IDeck createJokerDeck() {
        Deck deck = new Deck();
        deck.addCard(new Joker());
        deck.addCard(new Joker());
        deck.shuffle();
        return deck;
    }

    private static IDeck createShortDeck() {
        // Removes 2s through 5s — 36 cards total
        // Changes game feel since fewer low cards means
        // Higher/Lower guesses become harder to predict
        Deck deck = new Deck(6);
        // Ace is high in short deck — add it back manually
        deck.addCard(new Card(Card.HEARTS, 1));
        deck.addCard(new Card(Card.DIAMONDS, 1));
        deck.addCard(new Card(Card.CLUBS, 1));
        deck.addCard(new Card(Card.SPADES, 1));
        deck.shuffle();
        return deck;
    }

    private static IDeck createDoubleDeck() {
        // Two full decks combined — 104 cards
        // Good for longer games or more players
        Deck deck = new Deck();
        Deck second = new Deck();
        while (!second.isEmpty()) {
            deck.addCard(second.deal());
        }
        deck.shuffle();
        return deck;
    }
}