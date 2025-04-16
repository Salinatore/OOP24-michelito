package it.unibo.michelito.model.bomb.api;

import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.util.Temporary;
import it.unibo.michelito.model.util.Updatable;

/**
 * Represents a bomb in the {@link Maze}.
 */
public interface Bomb extends Temporary, Updatable {
    /**
     * Returns the range of the bomb.
     *
     * @return the range of the bomb.
     */
    int getRange();

    /**
     * Returns whether the bomb explosion can pass through boxes.
     *
     * @return whether the bomb explosion can pass through boxes.
     */
    boolean isPassThrough();
}
