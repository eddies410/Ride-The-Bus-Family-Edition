package ridethebus.game;

import ridethebus.cards.DeckFactory;
import ridethebus.cards.ICard;
import ridethebus.cards.IDeck;

import ridethebus.cards.Joker;
import ridethebus.characters.IPlayer;
import ridethebus.observers.EventBus;
import ridethebus.observers.GameEvent;
import ridethebus.observers.GameLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Credit-based game managing 5 rounds of 4 guessing questions.
 * Each player starts with 500 credits and wagers before each round.
 * Multipliers: Q1=2x, Q2=4x, Q3=8x, Q4=20x.
 * Wrong guess = lose wager for that round.
 * Going bankrupt (0 credits) = automatic loss.
 *
 * Patterns used:
 *   - Strategy: IGuess implementations (RedBlack, HigherLower, InsideOutside, Suit)
 *   - Observer: EventBus.post() on every state change
 *   - Factory: DeckFactory.createDeck()
 *   - Dependency Injection: players and bot injected via addPlayer()
 */
public class CreditGame {

    public static final int STARTING_CREDITS = 500;
    public static final int TOTAL_ROUNDS = 5;
    public static final int[] MULTIPLIERS = {2, 4, 8, 20};

    private final IDeck deck;
    private final List<PlayerState> playerStates = new ArrayList<>();
    private int currentRound = 0;
    private boolean gameOver = false;

    public CreditGame() {
        this.deck = DeckFactory.createDeck("standard");
        EventBus.getInstance().attach(new GameLogger());
    }

    public CreditGame(String deckType) {
        this.deck = DeckFactory.createDeck(deckType);
        EventBus.getInstance().attach(new GameLogger());
    }

    public void addPlayer(IPlayer player) {
        playerStates.add(new PlayerState(player, STARTING_CREDITS));
        EventBus.getInstance().post(GameEvent.Type.PLAYER_ADDED,
                player.getName() + " joined with " + STARTING_CREDITS + " credits");
    }

    public void startNextRound() {
        currentRound++;
        EventBus.getInstance().post(GameEvent.Type.ROUND_STARTED,
                "Round " + currentRound + " started");
        for (PlayerState ps : playerStates) {
            ps.startRound();
        }
    }

    /**
     * Place a wager for a player before their questions begin.
     * Returns false if wager exceeds credits or player is bankrupt.
     */
    public boolean placeWager(IPlayer player, int wager) {
        PlayerState ps = getState(player);
        if (ps == null || ps.isBankrupt() || wager > ps.getCredits() || wager <= 0) return false;
        ps.setWager(wager);
        EventBus.getInstance().post(GameEvent.Type.ROUND_STARTED,
                player.getName() + " wagered " + wager + " credits");
        return true;
    }

    /**
     * Process a guess for the current question.
     * Returns the card dealt. Caller checks isCorrect() for result.
     * Advances question counter internally.
     */
    public ICard processGuess(IPlayer player, IGuess guess) {
        PlayerState ps = getState(player);
        if (ps == null) throw new IllegalStateException("Player not in game");

        ICard card = deck.deal();
        ps.addDealtCard(card);

        // Joker = instant loss regardless of guess
        if (card instanceof Joker) {
            ps.applyLoss();
            EventBus.getInstance().post(GameEvent.Type.ROUND_ENDED,
                    player.getName() + " drew a Joker — instant loss!");
            checkBankruptcy(ps);
            return card;
        }

        boolean correct = guess.isCorrect(card);

        if (correct) {
            ps.setCurrentQuestion(ps.getCurrentQuestion() + 1);
            EventBus.getInstance().post(GameEvent.Type.ROUND_STARTED,
                    player.getName() + " guessed correctly — Q" + ps.getCurrentQuestion());
        } else {
            // Wrong: lose wager, round over for this player
            ps.applyLoss();
            EventBus.getInstance().post(GameEvent.Type.ROUND_ENDED,
                    player.getName() + " guessed wrong, lost " + ps.getWager() + " credits");
            checkBankruptcy(ps);
        }
        return card;
    }

    /**
     * Player cashes out after a correct guess.
     * Applies the multiplier for the current question level.
     */
    public int cashOut(IPlayer player) {
        PlayerState ps = getState(player);
        if (ps == null || ps.getCurrentQuestion() == 0) return 0;
        int multiplier = MULTIPLIERS[ps.getCurrentQuestion() - 1];
        int winnings = ps.getWager() * multiplier;
        ps.applyWin(winnings);
        EventBus.getInstance().post(GameEvent.Type.ROUND_ENDED,
                player.getName() + " cashed out " + winnings + " credits (" + multiplier + "x)");
        return winnings;
    }

    /**
     * Auto cash-out after Q4 correct. Called internally when currentQuestion reaches 4.
     */
    public int autoWin(IPlayer player) {
        PlayerState ps = getState(player);
        if (ps == null) return 0;
        int winnings = ps.getWager() * MULTIPLIERS[3];
        ps.applyWin(winnings);
        EventBus.getInstance().post(GameEvent.Type.GAME_OVER,
                player.getName() + " won " + winnings + " credits (20x)!");
        return winnings;
    }

    public boolean isRoundComplete() {
        return playerStates.stream().allMatch(PlayerState::isRoundDone);
    }

    public boolean isGameOver() {
        if (currentRound >= TOTAL_ROUNDS && isRoundComplete()) gameOver = true;
        return gameOver;
    }

    public IPlayer getWinner() {
        return playerStates.stream()
                .max((a, b) -> Integer.compare(a.getCredits(), b.getCredits()))
                .map(PlayerState::getPlayer)
                .orElse(null);
    }

    public PlayerState getState(IPlayer player) {
        return playerStates.stream()
                .filter(ps -> ps.getPlayer() == player)
                .findFirst().orElse(null);
    }

    public PlayerState getStateByName(String name) {
        return playerStates.stream()
                .filter(ps -> ps.getPlayer().getName().equals(name))
                .findFirst().orElse(null);
    }

    public List<PlayerState> getPlayerStates() { return List.copyOf(playerStates); }

    public int getCurrentRound() { return currentRound; }

    private void checkBankruptcy(PlayerState ps) {
        if (ps.isBankrupt()) {
            EventBus.getInstance().post(GameEvent.Type.GAME_OVER,
                    ps.getPlayer().getName() + " is bankrupt!");
        }
    }
}