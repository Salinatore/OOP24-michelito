package it.unibo.michelito.model.maze.api;

import it.unibo.michelito.controller.playercommand.api.PlayerCommand;
import it.unibo.michelito.model.util.Updatable;
import it.unibo.michelito.util.GameObject;

import java.util.Set;

/**
 * Represents the game level, providing methods to interact with the external game management system.
 * This interface exposes functionality to update the game state, process {@link PlayerCommand},
 * and getter of the level state (win/loss).
 */
public interface Level {
    /**
     * Updates all {@link Updatable} and applies the player's command.
     *
     * @param deltaTime the time elapsed since the last update, in milliseconds, used for game state updates.
     */
    void update(long deltaTime);

    /**
     * Sets the next command to be applied.
     *
     * @param playerCommand the {@link PlayerCommand} to be stored.
     */
    void setCommand(PlayerCommand playerCommand);

    /**
     * Retrieves the set of {@link GameObject} instances currently present in the maze.
     *
     * @return a set of {@link GameObject}.
     */
    Set<GameObject> getGameObjects();

    /**
     * Getter for the level state won.
     *
     * @return {@code true} if the level has been won, {@code false} otherwise.
     */
    boolean isWon();

    /**
     * Getter for the level state lost.
     *
     * @return {@code true} if the level has been lost, {@code false} otherwise.
     */
    boolean isLost();
}
