package it.unibo.michelito.model.blanckspace.impl;

import it.unibo.michelito.model.blanckspace.api.BlankSpace;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;

/**
 * Implementation of the {@link BlankSpace} interface.
 *
 * @param position is the {@link Position} that the BlankSpace will have.
 */
public record BlankSpaceImpl(Position position) implements BlankSpace {
    @Override
    public HitBox getHitBox() {
        return new HitBoxFactoryImpl().squareHitBox(position);
    }

    @Override
    public ObjectType getType() {
        return ObjectType.BLANK_SPACE;
    }
}
