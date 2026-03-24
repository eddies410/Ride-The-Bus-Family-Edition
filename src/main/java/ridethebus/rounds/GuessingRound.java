package ridethebus.rounds;

import ridethebus.characters.IPlayer;
import ridethebus.scoring.IScoringStrategy;

/**
Represents the first round of Ride the Bus.
Uses polymorphism through IScoringStrategy 
 */
public class GuessingRound implements IRound {

    private final IScoringStrategy scoringStrategy;
    private boolean isComplete = false;

    public GuessingRound(IScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }

    @Override
    public void start() {
        isComplete = false;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void end() {
        isComplete = true;
    }


    public void applyWrongColorPenalty(IPlayer player) {
        player.addPoints(scoringStrategy.pointsForWrongColorGuess());
    }

    public void applyWrongHigherLowerPenalty(IPlayer player) {
        player.addPoints(scoringStrategy.pointsForWrongHigherLowerGuess());
    }


    public void applyWrongInsideOutsidePenalty(IPlayer player) {
        player.addPoints(scoringStrategy.pointsForWrongInsideOutsideGuess());
    }


    public void applyWrongSuitPenalty(IPlayer player) {
        player.addPoints(scoringStrategy.pointsForWrongSuitGuess());
    }
}
