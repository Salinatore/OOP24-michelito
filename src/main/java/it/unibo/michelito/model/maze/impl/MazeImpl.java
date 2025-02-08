package it.unibo.michelito.model.maze.impl;

import it.unibo.michelito.controller.objectadapter.api.ObjectAdapter;
import it.unibo.michelito.controller.objectadapter.impl.ObjectAdapterImpl;
import it.unibo.michelito.controller.playercommand.api.PlayerCommand;
import it.unibo.michelito.model.blanckspace.api.BlankSpace;
import it.unibo.michelito.model.bomb.api.Bomb;
import it.unibo.michelito.model.box.api.Box;
import it.unibo.michelito.model.door.api.Door;
import it.unibo.michelito.model.enemy.api.Enemy;
import it.unibo.michelito.model.maze.api.Level;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.modelutil.MazeObject;
import it.unibo.michelito.model.modelutil.Temporary;
import it.unibo.michelito.model.modelutil.Updatable;
import it.unibo.michelito.model.player.api.Player;
import it.unibo.michelito.model.powerups.api.PowerUp;
import it.unibo.michelito.model.wall.api.Wall;
import it.unibo.michelito.util.GameObject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of {@link Maze} and {@link Level} interfaces representing a single maze in the game.
 * <p>
 * This class manages the maze objects, including walls, boxes, enemies, power-ups, and more.
 * It provides methods to interact with and manipulate these objects within the maze thought the Maze interface.
 * It provides method to get the current state and to update all {@link Updatable} thought the Level interface.
 * </p>
 */
public final class MazeImpl implements Maze, Level {
    private final Set<MazeObject> mazeObjectsSet;

    private boolean won;
    private boolean lost;

    /**
     * Construct a Maze of a certain level.
     *
     * @param levelNumber current level number.
     */
    public MazeImpl(final int levelNumber) {
        ObjectAdapter objectAdapter = new ObjectAdapterImpl();
        mazeObjectsSet = new HashSet<>(objectAdapter.requestMazeObjects(levelNumber));
    }

    @Override
    public void update(long currentTime) {
        this.getUpdatable().forEach(u -> u.update(currentTime, this));
    }

    @Override
    public void setCommand(PlayerCommand playerCommand) {
        playerCommand.execute(this.getPlayer());
    }

    @Override
    public Set<GameObject> getGameObjects() {
        return this.getAllObjects().stream()
                .map(m -> new GameObject(m.getType(), m.position()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isWon() {
        return this.won;
    }

    @Override
    public boolean isLost() {
        return this.lost;
    }

    @Override
    public boolean addMazeObject(final MazeObject mazeObject) {
        Objects.requireNonNull(mazeObject);
        return this.mazeObjectsSet.add(mazeObject);
    }

    @Override
    public boolean removeMazeObject(final Temporary temporaryObject) {
        Objects.requireNonNull(temporaryObject);
        return this.mazeObjectsSet.remove(temporaryObject);
    }

    @Override
    public void killMichelito() {
        this.lost = true;
    }

    @Override
    public void enterTheDoor() {
        this.won = true;
    }

    @Override
    public Set<MazeObject> getAllObjects() {
        return Set.copyOf(this.mazeObjectsSet);
    }

    @Override
    public Set<Wall> getWalls() {
        return this.getObjectsOfType(Wall.class);
    }

    @Override
    public Set<Box> getBoxes() {
        return this.getObjectsOfType(Box.class);
    }

    @Override
    public Set<Updatable> getUpdatable() {
        return this.getObjectsOfType(Updatable.class);
    }

    @Override
    public Player getPlayer() {
        return this.getObjectsOfType(Player.class)
                .stream()
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Set<PowerUp> getPowerUp() {
        return this.getObjectsOfType(PowerUp.class);
    }

    @Override
    public Door getDoor() {
        return this.getObjectsOfType(Door.class)
                .stream()
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Set<Enemy> getEnemies() {
        return this.getObjectsOfType(Enemy.class);
    }

    @Override
    public Set<BlankSpace> getBlankSpaces() {
        return this.getObjectsOfType(BlankSpace.class);
    }

    @Override
    public Set<Bomb> getBombs() {
        return this.getObjectsOfType(Bomb.class);
    }

    /**
     * A utility method to filter and retrieve all objects of a specific type from the maze.
     *
     * @param type the class type to filter the objects by.
     * @param <T>  the type of element that will be contained in the output set.
     * @return a {@link Set} of objects of the specified type.
     */
    private <T> Set<T> getObjectsOfType(final Class<T> type) {
        return this.mazeObjectsSet.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mazeObjectsSet);
    }

    @Override
    public String toString() {
        return "MazeImpl{" +
                "mazeObjectsSet=" + mazeObjectsSet +
                ", won=" + won +
                ", lost=" + lost +
                '}';
    }
}
