package it.unibo.michelito.model.door.api;

import it.unibo.michelito.model.enemy.api.Enemy;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.util.Updatable;

/**
 * Represents a generic door in the game.
 * Every door implementation may open when updated when no {@link Enemy} is in the {@link Maze}.
 */
public interface Door extends Updatable {
    /**
     * Getter of the door status.
     *
     * @return true if the door is open.
     */
    boolean isOpen();
}
