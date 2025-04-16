package it.unibo.michelito.model.wall.impl;

import it.unibo.michelito.model.wall.api.Wall;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;

/**
 * Implementation of the {@link Wall} interface.
 *
 * @param position is the {@link Position} that the Wall will have.
 */
public record WallImpl(Position position) implements Wall {
    @Override
    public HitBox getHitBox() {
        return new HitBoxFactoryImpl().squareHitBox(this.position);
    }

    @Override
    public ObjectType getType() {
        return ObjectType.WALL;
    }
}
