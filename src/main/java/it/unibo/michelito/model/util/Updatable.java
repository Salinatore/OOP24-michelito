package it.unibo.michelito.model.util;

import it.unibo.michelito.model.maze.api.Maze;

/**
 * Represents all {@link MazeObject} that could be updated.
 */
public interface Updatable extends MazeObject {
    /**
     * This method tells the object to update itself in relation to the deltaTime passed.
     * This method may change the state of the maze.
     *
     * @param deltaTime is the delta between the last update and this.
     * @param maze is the current {@link Maze}, used to let the {@link MazeObject} change the status.
     */
    void update(long deltaTime, Maze maze);
}
