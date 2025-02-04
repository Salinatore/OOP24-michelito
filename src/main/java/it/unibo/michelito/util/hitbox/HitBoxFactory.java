package it.unibo.michelito.util.hitbox;

import it.unibo.michelito.util.position.Position;


/**
 *  An interface modelling factories of various kinds of HitBox.
 */
public interface HitBoxFactory {
    /**
     *
     * @param position the center position of the object.
     *
     * @return the square tipe hitbox.
     */
    HitBox squareHitBox(Position position);


    /**
     *
     * @param position the center of the entity.
     *
     * @return the hitbox for the entity tipe.
     */
    HitBox entityeHitBox(Position position);
}
