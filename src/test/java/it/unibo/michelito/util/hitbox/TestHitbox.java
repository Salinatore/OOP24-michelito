package it.unibo.michelito.util.hitbox;

import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.api.HitBoxFactory;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * {@Link HitBox} test.
 */
class TestHitbox {
    private final HitBoxFactory hitBoxFactory = new HitBoxFactoryImpl();

    /**
     * Test about the entity type hitbox.
     */
    @Test void testEntityHitbox() {
        final HitBox h1 = hitBoxFactory.entityeHitBox(new Position(0, 0));
        assertNotNull(h1);
        assertNotNull(h1.getHitBox());
        assertEquals(new Position(0, 0), h1.getCenter());
        final HitBox h2 = hitBoxFactory.entityeHitBox(new Position(0, 0));
        assertNotNull(h2);
        assertNotNull(h2.getHitBox());
        assertEquals(h1, h2);

    }

    /**
     * test about the square type hitbox.
     */
    @Test void testSquareHitbox() {
        final HitBox h1 = hitBoxFactory.squareHitBox(new Position(0, 0));
        assertNotNull(h1);
        assertNotNull(h1.getHitBox());
        assertEquals(new Position(0, 0), h1.getCenter());
        final HitBox h2 = hitBoxFactory.squareHitBox(new Position(0, 0));
        assertNotNull(h2);
        assertNotNull(h2.getHitBox());
        assertEquals(h1, h2);
    }

    /**
     * test interaction between two entity type hitbox.
     */
    @Test void testTwoEntityHitbox() {
        final HitBox h1 = hitBoxFactory.entityeHitBox(new Position(0, 0));
        final HitBox h2 = hitBoxFactory.entityeHitBox(new Position(1.75, 0));
        final HitBox h3 = hitBoxFactory.entityeHitBox(new Position(2, 0));
        final HitBox h4 = hitBoxFactory.entityeHitBox(new Position(2.1, 0));
        final HitBox h5 = hitBoxFactory.entityeHitBox(new Position(-1.75, -3.75));
        assertTrue(h1.collision(h2));
        assertTrue(h1.collision(h3));
        assertFalse(h4.collision(h1));
        assertTrue(h1.collision(h5));
    }

    /**
     * test interaction between two square type hitbox.
     */
    @Test void testTwoSquareHitbox() {
        final HitBox h1 = hitBoxFactory.squareHitBox(new Position(0, 0));
        final HitBox h2 = hitBoxFactory.squareHitBox(new Position(5.75, 0));
        final HitBox h3 = hitBoxFactory.squareHitBox(new Position(6, 0));
        final HitBox h4 = hitBoxFactory.squareHitBox(new Position(6.1, 0));
        final HitBox h5 = hitBoxFactory.squareHitBox(new Position(-5.75, -5.75));
        assertTrue(h1.collision(h2));
        assertTrue(h1.collision(h3));
        assertFalse(h1.collision(h4));
        assertTrue(h1.collision(h5));
    }

    /**
     * test interaction between an entity and a square type hitbox.
     */
    @Test void testEntityAndSquareHitbox() {
        final HitBox h1 = hitBoxFactory.entityeHitBox(new Position(0, 0));
        final HitBox h2 = hitBoxFactory.squareHitBox(new Position(3.75, 0));
        final HitBox h3 = hitBoxFactory.squareHitBox(new Position(4, 0));
        final HitBox h4 = hitBoxFactory.squareHitBox(new Position(4.1, 0));
        final HitBox h5 = hitBoxFactory.squareHitBox(new Position(-3.75, -4.25));
        final HitBox h6 = hitBoxFactory.squareHitBox(new Position(0, 5.1));
        assertTrue(h1.collision(h2));
        assertTrue(h2.collision(h1));
        assertTrue(h1.collision(h3));
        assertFalse(h1.collision(h4));
        assertTrue(h5.collision(h1));
        assertFalse(h1.collision(h6));
    }

}
