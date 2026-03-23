package ridethebus.characters;

import ridethebus.cards.ICard;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a human player in the game.
 * Score starts at 0 and increases with wrong guesses.
 */
public class HumanPlayer implements IPlayer {

    private final String name;
    private int score;
    private final List<ICard> hand = new ArrayList<>();

    public HumanPlayer(String name) {
        this.name = name;
        this.score = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void addPoints(int points) {
        score += points;
    }

    @Override
    public void addCardToHand(ICard card) {
        hand.add(card);
    }

    @Override
    public List<ICard> getHand() {
        return List.copyOf(hand);
    }

    @Override
    public void clearHand() {
        hand.clear();
    }

    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }
}