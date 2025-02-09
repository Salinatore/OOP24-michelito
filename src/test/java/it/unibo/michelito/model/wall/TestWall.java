package it.unibo.michelito.model.wall;

import it.unibo.michelito.model.modelutil.hitbox.api.HitBox;
import it.unibo.michelito.model.modelutil.hitbox.impl.HitBoxFactoryImpl;
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
    private WallImpl wall;
    private Position position;

    /**
     * Sets up the test environment before each test.
     * Initializes a new {@link WallImpl} instance with a predefined position.
     */
    @BeforeEach
    void setUp() {
        this.position = new Position(0, 4);
        this.wall = new WallImpl(position);
    }

    /**
     * Tests the {@code getHitBox} method to ensure it returns the correct {@link HitBox}.
     */
    @Test
    void testGetHitBox() {
        HitBox expectedHitBox = new HitBoxFactoryImpl().squareHitBox(this.position);
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
