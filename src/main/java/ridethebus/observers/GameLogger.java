package ridethebus.observers;

/**
 * Concrete Observer implementation that logs all game events to the console.
 * Registered with the EventBus at game startup so every state change
 * is printed in real time during the demo.
 *
 * Demonstrates the Observer pattern — GameLogger reacts to events posted
 * by CreditGame without CreditGame knowing anything about logging.
 */
public class GameLogger implements IGameObserver {

    @Override
    public void onGameEvent(GameEvent event) {
        System.out.println("[" + event.getType() + "] " + event.getMessage());
    }
}