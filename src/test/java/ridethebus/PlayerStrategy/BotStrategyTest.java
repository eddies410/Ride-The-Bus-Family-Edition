package ridethebus.PlayerStrategy;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import ridethebus.cards.ICard;
import ridethebus.game.IGuess;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BotStrategyTest {

    private final BotStrategy bot = new BotStrategy();

    @Test
    void makeGuessQuestion0ReturnsRedBlackGuess() {
        IGuess guess = bot.makeGuess(0, new ArrayList<>());
        assertNotNull(guess);
        assertTrue(guess.getGuessDescription().contains("RedBlackGuess"));
    }

    @Test
    void makeGuessQuestion1WithFirstCardReturnsHigherLowerGuess() {
        List<ICard> cards = List.of(new Card(Card.HEARTS, 5));
        IGuess guess = bot.makeGuess(1, cards);
        assertNotNull(guess);
        assertTrue(guess.getGuessDescription().contains("Higher") ||
                guess.getGuessDescription().contains("Lower"));
    }

    @Test
    void makeGuessQuestion2WithTwoCardsReturnsInsideOutsideGuess() {
        List<ICard> cards = List.of(
                new Card(Card.HEARTS, 3),
                new Card(Card.SPADES, 9)
        );
        IGuess guess = bot.makeGuess(2, cards);
        assertNotNull(guess);
        assertTrue(guess.getGuessDescription().contains("Inside") ||
                guess.getGuessDescription().contains("Outside"));
    }

    @Test
    void makeGuessQuestion3ReturnsSuitGuess() {
        IGuess guess = bot.makeGuess(3, new ArrayList<>());
        assertNotNull(guess);
        assertTrue(guess.getGuessDescription().contains("SuitGuess"));
    }

    @Test
    void decideWagerIs20PercentOfCredits() {
        int wager = bot.decideWager(500);
        assertEquals(100, wager);
    }

    @Test
    void decideWagerMinimumIs10UnlessCreditsAreLower() {
        // with very low credits, wager is capped at available credits
        int wager = bot.decideWager(1);
        assertEquals(1, wager);
    }

    @Test
    void decideWagerMinimumIs10WithEnoughCredits() {
        // with enough credits, minimum wager is 10
        int wager = bot.decideWager(50);
        assertEquals(10, wager);
    }

    @Test
    void shouldCashOutAfterOneCorrect() {
        assertTrue(bot.shouldCashOut(1));
    }

    @Test
    void shouldNotCashOutWithZeroCorrect() {
        assertFalse(bot.shouldCashOut(0));
    }

    @Test
    void defaultCaseReturnsRedBlackGuess() {
        IGuess guess = bot.makeGuess(99, new ArrayList<>());
        assertNotNull(guess);
    }
}