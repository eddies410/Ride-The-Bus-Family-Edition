package ridethebus.scoring;

/**
 * Family-friendly scoring strategy with lower point penalties.
 */
public class FamilyScoringStrategy implements IScoringStrategy {

    private static final int WRONG_COLOR_POINTS = 2;
    private static final int WRONG_HIGHER_LOWER_POINTS = 1;
    private static final int WRONG_INSIDE_OUTSIDE_POINTS = 2;
    private static final int WRONG_SUIT_POINTS = 4;
    private static final int WRONG_BUS_POINTS = 2;

    @Override
    public int pointsForWrongColorGuess() {
        return WRONG_COLOR_POINTS;
    }

    @Override
    public int pointsForWrongHigherLowerGuess() {
        return WRONG_HIGHER_LOWER_POINTS;
    }

    @Override
    public int pointsForWrongInsideOutsideGuess() {
        return WRONG_INSIDE_OUTSIDE_POINTS;
    }

    @Override
    public int pointsForWrongSuitGuess() {
        return WRONG_SUIT_POINTS;
    }

    @Override
    public int pointsForWrongBusGuess() {
        return WRONG_BUS_POINTS;
    }
}