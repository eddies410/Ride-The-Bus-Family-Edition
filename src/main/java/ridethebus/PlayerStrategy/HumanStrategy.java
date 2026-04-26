package ridethebus.PlayerStrategy;

import ridethebus.cards.ICard;
import ridethebus.game.IGuess;

import java.util.List;

/**
 * Strategy implementation for a human player.
 * All decisions (guess, wager, cash-out) come from the frontend via the API,
 * so this implementation defers by returning null/defaults.
 * The controller handles actual human input — this class exists to complete
 * the Strategy pattern and make the human/bot distinction explicit.
 */
public class HumanStrategy implements IPlayerStrategy {

    @Override
    public IGuess makeGuess(int question, List<ICard> dealtSoFar) {
        // Human guesses are submitted via the API — no auto-decision here
        return null;
    }

    @Override
    public int decideWager(int credits) {
        // Human wagers are submitted via the API
        return 0;
    }

    @Override
    public boolean shouldCashOut(int questionsCorrect) {
        // Human cash-out decisions are submitted via the API
        return false;
    }
}