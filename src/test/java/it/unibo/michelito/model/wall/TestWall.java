package it.unibo.michelito.model.wall;

import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import it.unibo.michelito.model.wall.impl.WallImpl;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  Test class for {@link WallImpl} class.
 */
final class TestWall {
    public static final int X = 0;
    public static final int Y = 4;
    private WallImpl wall;
    private Position position;

    /**
     * Sets up the test environment before each test.
     * Initializes a new {@link WallImpl} instance with a predefined position.
     */
    @BeforeEach
    void setUp() {
        this.position = new Position(X, Y);
        this.wall = new WallImpl(position);
    }

    /**
     * Tests the {@code getHitBox} method to ensure it returns the correct {@link HitBox}.
     */
    @Test
    void testGetHitBox() {
        final HitBox expectedHitBox = new HitBoxFactoryImpl().squareHitBox(this.position);
        assertEquals(expectedHitBox, this.wall.getHitBox(), "HitBox should be squareHitBox");
    }

    /**
     * Tests the {@code getType} method to ensure it returns the correct {@link ObjectType}.
     */
    @Test
    void testGetType() {
        assertEquals(ObjectType.WALL, this.wall.getType(), "Type should be WALL");
    }
}
