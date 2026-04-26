//package ridethebus.game;
//
//import org.junit.jupiter.api.Test;
//import ridethebus.characters.HumanPlayer;
//import ridethebus.scoring.AdultScoringStrategy;
//import ridethebus.scoring.FamilyScoringStrategy;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GameBuilderTest {
//
//    @Test
//    void builderCreatesGameWithPlayers() {
//        Game game = new GameBuilder()
//                .withPlayer(new HumanPlayer("Alice"))
//                .withPlayer(new HumanPlayer("Bob"))
//                .build();
//        assertEquals(2, game.getPlayers().size());
//    }
//
//    @Test
//    void builderDefaultsToFamilyScoringStrategy() {
//        Game game = new GameBuilder()
//                .withPlayer(new HumanPlayer("Alice"))
//                .build();
//        assertInstanceOf(FamilyScoringStrategy.class, game.getScoringStrategy());
//    }
//
//    @Test
//    void builderAcceptsCustomScoringStrategy() {
//        Game game = new GameBuilder()
//                .withPlayer(new HumanPlayer("Alice"))
//                .withScoringStrategy(new AdultScoringStrategy())
//                .build();
//        assertInstanceOf(AdultScoringStrategy.class, game.getScoringStrategy());
//    }
//
//    @Test
//    void builderThrowsExceptionWithNoPlayers() {
//        assertThrows(IllegalStateException.class, () ->
//                new GameBuilder().build()
//        );
//    }
//
//    @Test
//    void builderCanChainMultiplePlayers() {
//        Game game = new GameBuilder()
//                .withPlayer(new HumanPlayer("Alice"))
//                .withPlayer(new HumanPlayer("Bob"))
//                .withPlayer(new HumanPlayer("Charlie"))
//                .build();
//        assertEquals(3, game.getPlayers().size());
//    }
//}