package it.unibo.michelito.model.blankspace;

import it.unibo.michelito.model.blanckspace.impl.BlankSpaceImpl;
import it.unibo.michelito.model.modelutil.hitbox.api.HitBox;
import it.unibo.michelito.model.modelutil.hitbox.impl.HitBoxFactoryImpl;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the {@link BlankSpaceImpl} class.
 */
final class TestBlankSpace {
    private BlankSpaceImpl blankSpace;
    private Position position;

    /**
     * Sets up the test environment before each test.
     * Initializes a new {@link BlankSpaceImpl} instance with a predefined position.
     */
    @BeforeEach
    void setUp() {
        this.position = new Position(0,  4);
        this.blankSpace = new BlankSpaceImpl(position);
    }

    /**
     * Tests the {@code getHitBox} method to ensure it returns the correct {@link HitBox}.
     */
    @Test
    void testGetHitBox() {
        HitBox expectedHitBox = new HitBoxFactoryImpl().squareHitBox(this.position);
        assertEquals(expectedHitBox, this.blankSpace.getHitBox(), "HitBox should be squareHitBox");
    }

    /**
     * Tests the {@code getType} method to ensure it returns the correct {@link ObjectType}.
     */
    @Test
    void testGetType() {
        assertEquals(ObjectType.BLANK_SPACE, this.blankSpace.getType(), "Type should be BLANK_SPACE");
    }
}
