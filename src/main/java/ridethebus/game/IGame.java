package ridethebus.game;

import ridethebus.characters.IPlayer;
import ridethebus.scoring.IScoringStrategy;

import java.util.List;

public interface IGame {
    void start();
    void nextRound();
    boolean isOver();
    IPlayer getWinner();
    List<IPlayer> getPlayers();
    IScoringStrategy getScoringStrategy();
    void addPlayer(IPlayer player);
}