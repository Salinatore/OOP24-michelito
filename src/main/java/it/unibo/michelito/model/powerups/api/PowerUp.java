package it.unibo.michelito.model.powerups.api;

import it.unibo.michelito.model.util.Temporary;
import it.unibo.michelito.model.player.api.ModifiablePlayer;
import it.unibo.michelito.model.player.api.Player;

/**
 * Interface that represents a powerUp for the {@link Player}.
 */
public interface PowerUp extends Temporary {
    /**
     * Method that applies the {@link PowerUp} effect to the {@link Player}.
     * @param player the target of the {@link PowerUp}.
     */
    void applyEffect(ModifiablePlayer player);
}
