package it.unibo.michelito.model.powerups.impl;

import it.unibo.michelito.model.player.api.Player;
import it.unibo.michelito.util.Position;

/**
 * Implementation of a {@link it.unibo.michelito.model.powerups.api.PowerUp} that increases {@link Player} speed.
 */
public class SpeedPowerUp extends AbsPowerUp {
    private static final double SPEED_UPGRADE = 0.1;
    /**
     * Constructor for {@link SpeedPowerUp}.
     * @param position {@link Position} of the {@link SpeedPowerUp}
     */
    public SpeedPowerUp(final Position position) {
        super(position);
    }

    @Override
    public final void applyEffect(final Player player) {
        player.increaseSpeed(SPEED_UPGRADE);
    }
}
