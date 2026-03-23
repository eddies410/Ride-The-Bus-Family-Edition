package ridethebus.rounds;

import org.junit.jupiter.api.Test;
import ridethebus.characters.HumanPlayer;
import ridethebus.characters.IPlayer;
import ridethebus.scoring.AdultScoringStrategy;
import ridethebus.scoring.FamilyScoringStrategy;
import ridethebus.scoring.IScoringStrategy;
import static org.junit.jupiter.api.Assertions.*;

public class GuessingRoundTest {

    @Test
    void wrongColorPenaltyDiffersBasedOnStrategy() {
        IPlayer familyPlayer = new HumanPlayer("Alice");
        IPlayer adultPlayer = new HumanPlayer("Bob");
        // implementation of polymorphia
        GuessingRound familyRound = new GuessingRound(new FamilyScoringStrategy());
        GuessingRound adultRound = new GuessingRound(new AdultScoringStrategy());

        familyRound.applyWrongColorPenalty(familyPlayer);
        adultRound.applyWrongColorPenalty(adultPlayer);

        assertNotEquals(familyPlayer.getScore(), adultPlayer.getScore());
    }

    @Test
    void wrongSuitPenaltyIsHigherThanWrongColorPenalty() {
        IPlayer player = new HumanPlayer("Alice");
        IScoringStrategy strategy = new FamilyScoringStrategy();
        GuessingRound round = new GuessingRound(strategy);

        round.applyWrongColorPenalty(player);
        int colorPenalty = player.getScore();

        player.addPoints(-colorPenalty); // reset
        round.applyWrongSuitPenalty(player);
        int suitPenalty = player.getScore();

        assertTrue(suitPenalty > colorPenalty);
    }

    @Test
    void roundStartsAsNotComplete() {
        GuessingRound round = new GuessingRound(new FamilyScoringStrategy());
        assertFalse(round.isComplete());
    }

    @Test
    void roundIsCompleteAfterEnd() {
        GuessingRound round = new GuessingRound(new FamilyScoringStrategy());
        round.start();
        round.end();
        assertTrue(round.isComplete());
    }
}