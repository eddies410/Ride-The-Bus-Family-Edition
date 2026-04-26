package ridethebus.observers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EventBusTest {

    private final List<GameEvent> received = new ArrayList<>();
    private final IGameObserver observer = received::add;

    @AfterEach
    void cleanup() {
        EventBus.getInstance().detach(observer);
        received.clear();
    }

    @Test
    void attachedObserverReceivesEvent() {
        EventBus.getInstance().attach(observer);
        EventBus.getInstance().post(GameEvent.Type.CARD_DEALT, "Test");
        assertEquals(1, received.size());
    }

    @Test
    void detachedObserverDoesNotReceiveEvent() {
        EventBus.getInstance().attach(observer);
        EventBus.getInstance().detach(observer);
        EventBus.getInstance().post(GameEvent.Type.CARD_DEALT, "Test");
        assertTrue(received.isEmpty());
    }

    @Test
    void eventHasCorrectType() {
        EventBus.getInstance().attach(observer);
        EventBus.getInstance().post(GameEvent.Type.WRONG_GUESS, "Test");
        assertEquals(GameEvent.Type.WRONG_GUESS, received.get(0).getType());
    }

    @Test
    void eventHasCorrectMessage() {
        EventBus.getInstance().attach(observer);
        EventBus.getInstance().post(GameEvent.Type.CARD_DEALT, "Ace of Spades");
        assertEquals("Ace of Spades", received.get(0).getMessage());
    }

    @Test
    void multipleObserversAllReceiveEvent() {
        List<GameEvent> second = new ArrayList<>();
        IGameObserver secondObserver = second::add;

        EventBus.getInstance().attach(observer);
        EventBus.getInstance().attach(secondObserver);
        EventBus.getInstance().post(GameEvent.Type.SCORE_UPDATED, "Test");

        assertEquals(1, received.size());
        assertEquals(1, second.size());

        EventBus.getInstance().detach(secondObserver);
    }

    @Test
    void gameEventToStringContainsTypeAndMessage() {
        GameEvent event = new GameEvent(GameEvent.Type.GAME_OVER, "Alice wins");
        assertTrue(event.toString().contains("GAME_OVER"));
        assertTrue(event.toString().contains("Alice wins"));
    }
}