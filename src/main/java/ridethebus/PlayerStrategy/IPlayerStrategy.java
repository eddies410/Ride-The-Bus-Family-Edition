package ridethebus.PlayerStrategy;

import ridethebus.cards.ICard;
import ridethebus.game.IGuess;

import java.util.List;

public interface IPlayerStrategy {
    IGuess makeGuess(int question, List<ICard> dealtSoFar);
    int decideWager(int credits);
    boolean shouldCashOut(int questionsCorrect);
}