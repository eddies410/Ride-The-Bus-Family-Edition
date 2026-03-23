package ridethebus.cards;

public class Card implements ICard {

    public static final String HEARTS = "Hearts";
    public static final String DIAMONDS = "Diamonds";
    public static final String CLUBS = "Clubs";
    public static final String SPADES = "Spades";

    public static final String RED = "Red";
    public static final String BLACK = "Black";

    private final String suit;
    private final int value;

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String getSuit() {
        return suit;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getColor() {
        if (suit.equals(HEARTS) || suit.equals(DIAMONDS)) {
            return RED;
        }
        return BLACK;
    }

    @Override
    public String getDisplayName() {
        return getFaceValue() + " of " + suit;
    }

    private String getFaceValue() {
        return switch (value) {
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> String.valueOf(value);
        };
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}