package it.unibo.michelito.model.util.hitbox.api;

import it.unibo.michelito.util.Position;

/**
 * An interface modelling factories of various kinds of HitBox.
 */
public interface HitBoxFactory {
    /**
     *
     * @param position the center position of the object.
     *
     * @return the square type hitbox.
     */
    HitBox squareHitBox(Position position);

    /**
     *
     * @param position the center of the entity.
     *
     * @return the hitbox for the entity type.
     */
    HitBox entityeHitBox(Position position);
}
