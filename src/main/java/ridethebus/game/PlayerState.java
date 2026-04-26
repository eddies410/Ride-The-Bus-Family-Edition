package ridethebus.game;

import ridethebus.cards.ICard;
import ridethebus.characters.IPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the state of a single player during a round.
 * Holds credits, current wager, question progress, and dealt cards.
 * Extracted from CreditGame to follow single responsibility principle.
 */
public class PlayerState {

    private final IPlayer player;
    private int credits;
    private int wager = 0;
    private int currentQuestion = 0;
    private boolean roundDone = false;
    private final List<ICard> dealtCards = new ArrayList<>();

    public PlayerState(IPlayer player, int credits) {
        this.player = player;
        this.credits = credits;
    }

    public void startRound() {
        wager = 0;
        currentQuestion = 0;
        roundDone = false;
        dealtCards.clear();
    }

    public void applyLoss() {
        credits = Math.max(1, credits / 2);
        roundDone = true;
    }

    public void applyWin(int winnings) {
        credits += winnings;
        roundDone = true;
    }

    public void addDealtCard(ICard card) { dealtCards.add(card); }

    public boolean isBankrupt() { return credits <= 0; }
    public boolean isRoundDone() { return roundDone; }

    public IPlayer getPlayer() { return player; }
    public int getCredits() { return credits; }
    public int getWager() { return wager; }
    public int getCurrentQuestion() { return currentQuestion; }
    public List<ICard> getDealtCards() { return List.copyOf(dealtCards); }

    public void setWager(int wager) { this.wager = wager; }
    public void setCurrentQuestion(int q) {
        this.currentQuestion = q;
        if (q >= 4) { roundDone = true; }
    }
}