package ridethebus.scoring;

/**
 * Adult scoring strategy where points represent drinks.
 * Directly mirrors the traditional Ride the Bus drinking game rules.
 * The player with the highest "drink count" loses.
 */
public class AdultScoringStrategy implements IScoringStrategy {

    private static final int WRONG_COLOR_POINTS = 1;

    private static final int WRONG_HIGHER_LOWER_POINTS = 2;

    private static final int WRONG_INSIDE_OUTSIDE_POINTS = 3;

    private static final int WRONG_SUIT_POINTS = 4;

    private static final int WRONG_BUS_POINTS = 1;

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