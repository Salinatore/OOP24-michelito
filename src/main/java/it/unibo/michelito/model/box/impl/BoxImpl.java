package it.unibo.michelito.model.box.impl;

import it.unibo.michelito.model.box.api.Box;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;

/**
 * Implementation of the {@link Box} interface.
 *
 * @param position is the {@link Position} that the Box will have.
 */
public record BoxImpl(Position position) implements Box {
    @Override
    public HitBox getHitBox() {
        return new HitBoxFactoryImpl().squareHitBox(this.position);
    }

    @Override
    public ObjectType getType() {
        return ObjectType.BOX;
    }
}
