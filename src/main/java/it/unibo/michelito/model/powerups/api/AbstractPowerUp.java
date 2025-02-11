package it.unibo.michelito.model.powerups.api;

import it.unibo.michelito.model.player.api.ModifiablePlayer;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.modelutil.hitbox.api.HitBox;
import it.unibo.michelito.model.modelutil.hitbox.api.HitBoxFactory;
import it.unibo.michelito.model.modelutil.hitbox.impl.HitBoxFactoryImpl;

public abstract class AbstractPowerUp implements PowerUp {
    private final Position position;
    private final HitBox hitBox;
    /**
     * Constructor for {@link PowerUp}.
     * @param position position {@link Position} of the {@link PowerUp}
     */
    public AbstractPowerUp(final Position position) {
        this.position = position;
        this.hitBox = createHitBox();
    }

    private HitBox createHitBox() {
        final HitBoxFactory factory = new HitBoxFactoryImpl();
        return factory.squareHitBox(this.position);
    }

    /**
     * {@inheritDoc}
     */
   @Override
   public HitBox getHitBox() {
        return this.hitBox;
   }

    /**
     * {@inheritDoc}
     */
   @Override
    public Position position() {
        return this.position;
   }

    /**
     * {@inheritDoc}
     */
   @Override
    public abstract void applyEffect(ModifiablePlayer player);
}
