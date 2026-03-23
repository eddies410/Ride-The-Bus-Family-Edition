package ridethebus.scoring;


public interface IScoringStrategy {
    int pointsForWrongColorGuess();
    int pointsForWrongHigherLowerGuess();
    int pointsForWrongInsideOutsideGuess();
    int pointsForWrongSuitGuess();
    int pointsForWrongBusGuess();
}