package it.unibo.michelito.model.player.api;

import it.unibo.michelito.model.util.Updatable;
import it.unibo.michelito.util.Direction;
import it.unibo.michelito.model.bomb.api.Bomb;

/**
 * Interface for {@link Player} that extends {@link Updatable}.
 * This interface defines methods for setting the player's movement direction and bomb placement.
 */
public interface Player extends Updatable {
    /**
     * Sets the direction for the next move of the {@link Player}.
     *
     * @param direction the {@link Direction} in which the {@link Player} will move next
     */
    void setDirection(Direction direction);

    /**
     * Informs the {@link Player} to place a {@link Bomb}.
     * This method triggers the placement of a bomb by the player.
     */
    void notifyToPlace();
}
