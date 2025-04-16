package it.unibo.michelito.model.box;

import it.unibo.michelito.model.box.impl.BoxImpl;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the {@link BoxImpl} class.
 */
final class TestBox {
    public static final int X = 0;
    public static final int Y = 4;
    private BoxImpl box;
    private Position position;

    /**
     * Sets up the test environment before each test.
     * Initializes a new {@link BoxImpl} instance with a predefined position.
     */
    @BeforeEach
    void setUp() {
        this.position = new Position(X, Y);
        this.box = new BoxImpl(position);
    }

    /**
     * Tests the {@code getHitBox} method to ensure it returns the correct {@link HitBox}.
     */
    @Test
    void testGetHitBox() {
        final HitBox expectedHitBox = new HitBoxFactoryImpl().squareHitBox(this.position);
        assertEquals(expectedHitBox, this.box.getHitBox(), "HitBox should be squareHitBox");
    }

    /**
     * Tests the {@code getType} method to ensure it returns the correct {@link ObjectType}.
     */
    @Test
    void testGetType() {
        assertEquals(ObjectType.BOX, this.box.getType(), "Type should be BOX");
    }
}
