package ridethebus.game;

import org.junit.jupiter.api.Test;
import ridethebus.cards.Card;
import ridethebus.characters.HumanPlayer;
import ridethebus.characters.IPlayer;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerStateTest {

    private IPlayer player() { return new HumanPlayer("Alice"); }

    @Test
    void startsWithGivenCredits() {
        assertEquals(500, new PlayerState(player(), 500).getCredits());
    }

    @Test
    void isNotBankruptWithCredits() {
        assertFalse(new PlayerState(player(), 500).isBankrupt());
    }

    @Test
    void applyLossHalvesCredits() {
        PlayerState ps = new PlayerState(player(), 500);
        ps.setWager(100);
        ps.applyLoss();
        assertEquals(250, ps.getCredits());
    }

    @Test
    void applyLossMarksRoundDone() {
        PlayerState ps = new PlayerState(player(), 500);
        ps.applyLoss();
        assertTrue(ps.isRoundDone());
    }

    @Test
    void applyWinAddsCredits() {
        PlayerState ps = new PlayerState(player(), 500);
        ps.applyWin(200);
        assertEquals(700, ps.getCredits());
    }

    @Test
    void applyWinMarksRoundDone() {
        PlayerState ps = new PlayerState(player(), 500);
        ps.applyWin(100);
        assertTrue(ps.isRoundDone());
    }

    @Test
    void startRoundResetsState() {
        PlayerState ps = new PlayerState(player(), 500);
        ps.setWager(100);
        ps.setCurrentQuestion(2);
        ps.applyLoss();
        ps.startRound();
        assertEquals(0, ps.getWager());
        assertEquals(0, ps.getCurrentQuestion());
        assertFalse(ps.isRoundDone());
    }

    @Test
    void setCurrentQuestionTo4MarksRoundDone() {
        PlayerState ps = new PlayerState(player(), 500);
        ps.setCurrentQuestion(4);
        assertTrue(ps.isRoundDone());
    }

    @Test
    void addDealtCardTracksCards() {
        PlayerState ps = new PlayerState(player(), 500);
        ps.addDealtCard(new Card(Card.HEARTS, 5));
        assertEquals(1, ps.getDealtCards().size());
    }

    @Test
    void applyLossNeverGoesBelowOne() {
        PlayerState ps = new PlayerState(player(), 1);
        ps.applyLoss();
        assertEquals(1, ps.getCredits());
    }

    @Test
    void getPlayerReturnsCorrectPlayer() {
        IPlayer p = player();
        assertEquals(p, new PlayerState(p, 500).getPlayer());
    }
}