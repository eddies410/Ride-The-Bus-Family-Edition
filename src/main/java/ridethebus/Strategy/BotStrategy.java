package ridethebus.Strategy;

import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import ridethebus.game.*;

import java.util.List;
import java.util.Random;

/**
 * Simple bot strategy that makes random guesses for each question type.
 * Implements the Strategy pattern — the bot picks a random valid option
 * for the current question and delegates to the appropriate IGuess impl.
 *
 * Wager strategy: always wagers 20% of current credits (min 10).
 * Cash-out strategy: always cashes out after the first correct guess.
 */
public class BotStrategy {

    private static final Random RNG = new Random();

    /**
     * Returns a random IGuess for the given question index.
     *   0 = Red / Black
     *   1 = Higher / Lower  (needs first dealt card)
     *   2 = Inside / Outside (needs first two dealt cards)
     *   3 = Suit
     */
    public static IGuess makeGuess(int question, List<ICard> dealtSoFar) {
        return switch (question) {
            case 0 -> {
                String[] opts = { Card.RED, Card.BLACK };
                yield new RedBlackGuess(opts[RNG.nextInt(2)]);
            }
            case 1 -> {
                String[] opts = { HigherLowerGuess.HIGHER, HigherLowerGuess.LOWER };
                ICard first = dealtSoFar.isEmpty() ? null : dealtSoFar.get(0);
                yield first != null
                        ? new HigherLowerGuess(opts[RNG.nextInt(2)], first)
                        : new RedBlackGuess(Card.RED);
            }
            case 2 -> {
                String[] opts = { InsideOutsideGuess.INSIDE, InsideOutsideGuess.OUTSIDE };
                ICard first  = dealtSoFar.size() > 0 ? dealtSoFar.get(0) : null;
                ICard second = dealtSoFar.size() > 1 ? dealtSoFar.get(1) : null;
                yield (first != null && second != null)
                        ? new InsideOutsideGuess(opts[RNG.nextInt(2)], first, second)
                        : new RedBlackGuess(Card.RED);
            }
            case 3 -> {
                String[] suits = { Card.HEARTS, Card.DIAMONDS, Card.CLUBS, Card.SPADES };
                yield new SuitGuess(suits[RNG.nextInt(4)]);
            }
            default -> new RedBlackGuess(Card.RED);
        };
    }

    /** Wager 20% of credits, minimum 10. */
    public static int decideWager(int credits) {
        return Math.min(credits, Math.max(10, (int)(credits * 0.20)));
    }

    /** Always cash out after the first correct guess. */
    public static boolean shouldCashOut(int questionsCorrect) {
        return questionsCorrect >= 1;
    }
}