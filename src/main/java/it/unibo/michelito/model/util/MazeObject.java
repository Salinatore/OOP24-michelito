package it.unibo.michelito.model.util;

import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.maze.api.Maze;

/**
 * Represents a generic object in the {@link Maze}.
 * All object within the {@link Maze} must implement this interface.
 */
public interface MazeObject {
    /**
     * Get the {@link Position} of the MazeObject.
     *
     * @return the {@link Position} of the MazeObject.
     */
    Position position();

    /**
     * Get the {@link HitBox} of the MazeObject.
     *
     * @return the {@link HitBox} of the MazeObject.
     */
    HitBox getHitBox();

    /**
     * Get the {@link ObjectType} of the MazeObject.
     *
     * @return the {@link ObjectType} of the MazeObject.
     */
    ObjectType getType();
}
