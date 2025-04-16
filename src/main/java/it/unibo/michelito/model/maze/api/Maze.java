package it.unibo.michelito.model.maze.api;

import it.unibo.michelito.model.blanckspace.api.BlankSpace;
import it.unibo.michelito.model.bomb.api.Bomb;
import it.unibo.michelito.model.box.api.Box;
import it.unibo.michelito.model.door.api.Door;
import it.unibo.michelito.model.enemy.api.Enemy;
import it.unibo.michelito.model.util.MazeObject;
import it.unibo.michelito.model.util.Temporary;
import it.unibo.michelito.model.util.Updatable;
import it.unibo.michelito.model.player.api.Player;
import it.unibo.michelito.model.powerups.api.PowerUp;
import it.unibo.michelito.model.wall.api.Wall;

import java.util.Set;

/**
 * Represents a maze that contains all {@link MazeObject} instances present in the game.
 * Provides methods to manage and interact with the various objects within the maze.
 * <p>
 * Additionally, it defines methods to handle key game events, such as when the {@link Player}
 * (Michelito) dies or successfully completes the level by entering the door.
 */
public interface Maze {
    /**
     * Adds a {@link MazeObject} to the maze.
     *
     * @param mazeObject the object to be added.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    boolean addMazeObject(MazeObject mazeObject);

    /**
     * Removes a {@link Temporary} object from the maze.
     *
     * @param temporaryObject the object to be removed.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    boolean removeMazeObject(Temporary temporaryObject);

    /**
     * Handles the event of Michelito's death.
     */
    void killMichelito();

    /**
     * Handles the event when Michelito enters the door to complete the level.
     */
    void enterTheDoor();

    /**
     * Retrieves all {@link MazeObject} instances in the maze.
     *
     * @return a set containing all {@link MazeObject} instances.
     */
    Set<MazeObject> getAllObjects();

    /**
     * Retrieves all {@link Wall} objects in the maze.
     *
     * @return a set of {@link Wall} objects.
     */
    Set<Wall> getWalls();

    /**
     * Retrieves all {@link Box} objects in the maze.
     *
     * @return a set of {@link Box} objects.
     */
    Set<Box> getBoxes();

    /**
     * Retrieves all {@link Updatable} objects in the maze.
     *
     * @return a set of {@link Updatable} objects.
     */
    Set<Updatable> getUpdatable();

    /**
     * Retrieves the {@link Player} in the maze.
     *
     * @return the {@link Player} instance.
     */
    Player getPlayer();

    /**
     * Retrieves all {@link PowerUp} objects in the maze.
     *
     * @return a set of {@link PowerUp} objects.
     */
    Set<PowerUp> getPowerUp();

    /**
     * Retrieves the {@link Door} in the maze.
     *
     * @return the {@link Door} instance.
     */
    Door getDoor();

    /**
     * Retrieves all {@link Enemy} objects in the maze.
     *
     * @return a set of {@link Enemy} objects.
     */
    Set<Enemy> getEnemies();

    /**
     * Retrieves all {@link BlankSpace} objects in the maze.
     *
     * @return a set of {@link BlankSpace} objects.
     */
    Set<BlankSpace> getBlankSpaces();

    /**
     * Retrieves all {@link Bomb} objects in the maze.
     *
     * @return a set of {@link Bomb} objects.
     */
    Set<Bomb> getBombs();
}
