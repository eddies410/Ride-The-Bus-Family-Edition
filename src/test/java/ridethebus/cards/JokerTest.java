package ridethebus.cards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JokerTest {

    @Test
    void jokerSuitIsJoker() {
        assertEquals("Joker", new Joker().getSuit());
    }

    @Test
    void jokerValueIsZero() {
        assertEquals(0, new Joker().getValue());
    }

    @Test
    void jokerColorIsNone() {
        assertEquals("None", new Joker().getColor());
    }

    @Test
    void jokerDisplayNameIsJoker() {
        assertEquals("Joker", new Joker().getDisplayName());
    }

    @Test
    void jokerToStringIsJoker() {
        assertEquals("Joker", new Joker().toString());
    }
}