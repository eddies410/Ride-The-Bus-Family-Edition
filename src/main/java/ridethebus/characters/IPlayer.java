package ridethebus.characters;

import ridethebus.cards.ICard;
import java.util.List;

public interface IPlayer {
    String getName();
    int getScore();
    void addPoints(int points);
    void addCardToHand(ICard card);
    List<ICard> getHand();
    void clearHand();
}