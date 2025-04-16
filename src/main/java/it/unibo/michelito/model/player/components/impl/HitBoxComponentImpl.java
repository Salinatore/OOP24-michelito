package it.unibo.michelito.model.player.components.impl;

import it.unibo.michelito.model.blanckspace.api.BlankSpace;
import it.unibo.michelito.model.player.components.api.HitBoxComponent;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.api.HitBoxFactory;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import it.unibo.michelito.model.powerups.api.PowerUp;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.wall.api.Wall;
import it.unibo.michelito.model.box.api.Box;

import java.util.Optional;

/**
 * Implementation of {@link HitBoxComponent} representing a manager for a {@link HitBox}.
 * This component handles {@link HitBox} creation, updates, and collision detection with
 * various game elements such as {@link Wall}, {@link Box}, {@link PowerUp}, and {@link BlankSpace}.
 */
public class HitBoxComponentImpl implements HitBoxComponent {
    private HitBox hitBox;

    /**
     * Constructs a new {@link HitBoxComponentImpl} with the specified initial {@link Position}.
     *
     * @param position the {@link Position} position of the {@link HitBox}
     */
    public HitBoxComponentImpl(final Position position) {
        final HitBoxFactory hitBoxFactory = new HitBoxFactoryImpl();
        this.hitBox = hitBoxFactory.entityeHitBox(position);
    }

    /**
     * Returns the current {@link HitBox} associated with this component.
     *
     * @return the current {@link HitBox} instance
     */
    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    /**
     * Updates the {@link HitBox} to a new {@link Position}.
     *
     * @param position the new {@link Position} to update the {@link HitBox} to
     */
    @Override
    public void update(final Position position) {
        final HitBoxFactory hitBoxFactory = new HitBoxFactoryImpl();
        this.hitBox = hitBoxFactory.entityeHitBox(position);
    }

    /**
     * Checks if the {@link HitBox} collides with any {@link Wall} or {@link Box} in the given maze.
     *
     * @param maze the {@link Maze} containing walls and boxes
     * @return true if a collision is detected, otherwise false
     */
    @Override
    public boolean checkCollisionWallsBoxes(final Maze maze) {
        final boolean collisionWalls = maze.getWalls().stream()
                .anyMatch(w -> this.hitBox.collision(w.getHitBox()));
        final boolean collisionBox = maze.getBoxes().stream()
                .anyMatch(b -> this.hitBox.collision(b.getHitBox()));
        return collisionWalls || collisionBox;
    }

    /**
     * Checks if the {@link HitBox} collides with a power-up in the given maze.
     *
     * @param maze the {@link Maze} containing {@link PowerUp}
     * @return an {@link Optional} containing the colliding {@link PowerUp} or an Optional.empty() if there are none
     */
    @Override
    public Optional<PowerUp> checkCollisionPowerUp(final Maze maze) {
        return maze.getPowerUp().stream()
                .filter(obj ->
                        obj.getType().equals(ObjectType.BOMB_TYPE_POWERUP)
                        || obj.getType().equals(ObjectType.SPEED_POWERUP)
                        || obj.getType().equals(ObjectType.BOMB_LIMIT_POWERUP))
                .filter(p -> this.getHitBox().collision(p.getHitBox()))
                .findAny();
    }

    /**
     * Finds the closest {@link BlankSpace} that collides with the {@link HitBox} in the given {@link Maze}.
     *
     * @param maze the {@link Maze} containing blank spaces
     * @return an {@link Optional} containing the closest colliding blank space or an Optional.empty() if there are none
     */
    @Override
    public Optional<BlankSpace> closestBlankSpace(final Maze maze) {
        return maze.getBlankSpaces().stream()
                .filter(b -> b.getHitBox().collision(this.hitBox))
                .filter(blankSpace -> maze.getBombs().stream()
                        .noneMatch(bomb -> bomb.position().equals(blankSpace.position())))
                .findAny();
    }
}
