package ridethebus.game;
import ridethebus.cards.IDeck;
import ridethebus.scoring.IScoringStrategy;

public class Game {
    private final IScoringStrategy scoringStrategy;
    private final IDeck deck;

    public Game(IScoringStrategy scoringStrategy, IDeck deck) {
        this.scoringStrategy = scoringStrategy;
        this.deck = deck;
    }
}