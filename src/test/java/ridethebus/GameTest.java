//package ridethebus.game;
//
//import org.junit.jupiter.api.Test;
//import ridethebus.characters.HumanPlayer;
//import ridethebus.characters.IPlayer;
//import ridethebus.scoring.AdultScoringStrategy;
//import ridethebus.scoring.FamilyScoringStrategy;
//import ridethebus.scoring.IScoringStrategy;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GameTest {
//
//    @Test
//    void gameStartsWithNoPlayers() {
//        Game game = new Game(new FamilyScoringStrategy());
//        assertTrue(game.getPlayers().isEmpty());
//    }
//
//    @Test
//    void addingPlayerIncreasesPlayerCount() {
//        Game game = new Game(new FamilyScoringStrategy());
//        game.addPlayer(new HumanPlayer("Alice"));
//        assertEquals(1, game.getPlayers().size());
//    }
//
//    @Test
//    void gameCannotStartWithNoPlayers() {
//        Game game = new Game(new FamilyScoringStrategy());
//        assertThrows(IllegalStateException.class, game::start);
//    }
//
//    @Test
//    void gameIsNotOverOnCreation() {
//        Game game = new Game(new FamilyScoringStrategy());
//        assertFalse(game.isOver());
//    }
//
//    @Test
//    void winnerIsPlayerWithLowestScore() {
//        Game game = new Game(new FamilyScoringStrategy());
//        IPlayer alice = new HumanPlayer("Alice");
//        IPlayer bob = new HumanPlayer("Bob");
//        alice.addPoints(5);
//        bob.addPoints(3);
//        game.addPlayer(alice);
//        game.addPlayer(bob);
//        game.addPlayer(new HumanPlayer("Charlie"));
//
//        // manually end the game to test winner logic
//        game.addPlayer(new HumanPlayer("Dummy"));
//        game.start();
//        game.nextRound();
//        game.nextRound();
//
//        assertEquals("Bob", game.getWinner().getName());
//    }
//
//    @Test
//    void gameAcceptsDifferentScoringStrategies() {
//        // demonstrates polymorphism and dependency injection
//        IScoringStrategy family = new FamilyScoringStrategy();
//        IScoringStrategy adult = new AdultScoringStrategy();
//
//        Game familyGame = new Game(family);
//        Game adultGame = new Game(adult);
//
//        assertNotEquals(
//                familyGame.getScoringStrategy().pointsForWrongSuitGuess(),
//                adultGame.getScoringStrategy().pointsForWrongSuitGuess()
//        );
//    }
//
//    @Test
//    void highestScorerIsCorrectlyIdentified() {
//        Game game = new Game(new FamilyScoringStrategy());
//        IPlayer alice = new HumanPlayer("Alice");
//        IPlayer bob = new HumanPlayer("Bob");
//        alice.addPoints(10);
//        bob.addPoints(3);
//        game.addPlayer(alice);
//        game.addPlayer(bob);
//
//        assertEquals("Alice", game.getHighestScorer().getName());
//    }
//}