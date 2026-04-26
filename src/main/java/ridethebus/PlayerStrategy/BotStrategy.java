package ridethebus.PlayerStrategy;

import ridethebus.GuessesStrategy.HigherLowerGuess;
import ridethebus.GuessesStrategy.InsideOutsideGuess;
import ridethebus.GuessesStrategy.RedBlackGuess;
import ridethebus.GuessesStrategy.SuitGuess;
import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import ridethebus.game.*;

import java.util.List;
import java.util.Random;

/**
 * Strategy implementation for a bot player.
 * Makes random but valid guesses for each question type.
 * Wagers 20% of current credits and always cashes out after the first correct guess.
 *
 * Implements IPlayerStrategy — swappable with HumanStrategy at runtime
 * without changing any game logic.
 */
public class BotStrategy implements IPlayerStrategy {

    private static final Random RNG = new Random();

    @Override
    public IGuess makeGuess(int question, List<ICard> dealtSoFar) {
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

    @Override
    public int decideWager(int credits) {
        return Math.min(credits, Math.max(10, (int)(credits * 0.20)));
    }

    @Override
    public boolean shouldCashOut(int questionsCorrect) {
        return questionsCorrect >= 1;
    }


    public static IGuess staticMakeGuess(int question, List<ICard> dealtSoFar) {
        return new BotStrategy().makeGuess(question, dealtSoFar);
    }

    public static int staticDecideWager(int credits) {
        return new BotStrategy().decideWager(credits);
    }

    public static boolean staticShouldCashOut(int questionsCorrect) {
        return new BotStrategy().shouldCashOut(questionsCorrect);
    }
}