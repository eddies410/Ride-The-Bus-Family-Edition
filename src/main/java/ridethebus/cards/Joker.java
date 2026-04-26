package ridethebus.cards;

public class Joker implements ICard {

    @Override
    public String getSuit() { return "Joker"; }

    @Override
    public int getValue() { return 0; }

    @Override
    public String getColor() { return "None"; }

    @Override
    public String getDisplayName() { return "Joker"; }

    @Override
    public String toString() { return getDisplayName(); }
}