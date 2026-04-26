//package ridethebus.scoring;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ScoringStrategyTest {
//
//    @Test
//    void familyStrategyHasHigherBusPenaltyThanAdult() {
//        IScoringStrategy family = new FamilyScoringStrategy();
//        IScoringStrategy adult = new AdultScoringStrategy();
//        assertTrue(family.pointsForWrongBusGuess() > adult.pointsForWrongBusGuess());
//    }
//
//    @Test
//    void familySuitGuessCostsFourPoints() {
//        IScoringStrategy strategy = new FamilyScoringStrategy();
//        assertEquals(4, strategy.pointsForWrongSuitGuess());
//    }
//
//    @Test
//    void adultWrongColorIsOneDrink() {
//        IScoringStrategy strategy = new AdultScoringStrategy();
//        assertEquals(1, strategy.pointsForWrongColorGuess());
//    }
//
//    @Test
//    void adultWrongSuitIsFourDrinks() {
//        IScoringStrategy strategy = new AdultScoringStrategy();
//        assertEquals(4, strategy.pointsForWrongSuitGuess());
//    }
//
//    @Test
//    void strategiesCanBeSwapped() {
//        IScoringStrategy family = new FamilyScoringStrategy();
//        IScoringStrategy adult = new AdultScoringStrategy();
//        assertNotEquals(
//                family.pointsForWrongBusGuess(),
//                adult.pointsForWrongBusGuess()
//        );
//    }
//}