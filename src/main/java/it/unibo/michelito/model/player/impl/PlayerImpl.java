package it.unibo.michelito.model.player.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.michelito.model.blanckspace.api.BlankSpace;
import it.unibo.michelito.model.bomb.api.BombType;
import it.unibo.michelito.model.player.components.api.BombManagerComponent;
import it.unibo.michelito.model.player.components.api.HitBoxComponent;
import it.unibo.michelito.model.player.components.api.MovementComponent;
import it.unibo.michelito.model.player.components.impl.BombManagerComponentImpl;
import it.unibo.michelito.model.player.components.impl.HitBoxComponentImpl;
import it.unibo.michelito.model.player.components.impl.MovementComponentImpl;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.player.api.ModifiablePlayer;
import it.unibo.michelito.model.player.api.Player;
import it.unibo.michelito.util.Direction;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.powerups.api.PowerUp;

/**
 * Implementation of {@link Player} and {@link ModifiablePlayer} that represents a player entity in the game.
 * It manages movement, collisions, and bomb placement.
 */
public class PlayerImpl implements Player, ModifiablePlayer {
    private final HitBoxComponent hitBoxComponent;
    private final MovementComponent movementComponent;
    private final BombManagerComponent bombManagerComponent;

    /**
     * Constructs a {@code PlayerImpl} instance at the specified {@link Position}.
     *
     * @param position the spawning {@link Position} of the {@link Player}.
     */
    public PlayerImpl(final Position position) {
        this.movementComponent = new MovementComponentImpl(position);
        this.bombManagerComponent = new BombManagerComponentImpl();
        this.hitBoxComponent = new HitBoxComponentImpl(position);
    }

    /**
     * Updates the player's state, including movement and interactions with the {@link Maze}.
     *
     * @param deltaTime the time elapsed since the last update
     * @param maze the current game {@link Maze}
     */
    @Override
    public final void update(final long deltaTime, final Maze maze) {
            this.move(deltaTime, maze);
            this.hitBoxComponent.update(this.position());
            this.checkPowerUp(maze);
            this.placeBomb(maze, deltaTime);
    }

    private void move(final long time, final Maze maze) {
        final Position oldPosition = this.position();
        this.movementComponent.move(time);
        this.hitBoxComponent.update(this.position());

        if (this.isCollidingWithWallsOrBoxes(maze)) {
            this.setPosition(oldPosition);
        }
    }

    private void setPosition(final Position position) {
        this.movementComponent.setPosition(position);
        this.hitBoxComponent.update(this.position());
    }

    private boolean isCollidingWithWallsOrBoxes(final Maze maze) {
        return this.hitBoxComponent.checkCollisionWallsBoxes(maze);
    }

    private void checkPowerUp(final Maze maze) {
        final Optional<PowerUp> powerUp = this.hitBoxComponent.checkCollisionPowerUp(maze);

        if (powerUp.isPresent()) {
            powerUp.get().applyEffect(this);
            maze.removeMazeObject(powerUp.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position position() {
        return this.movementComponent.getPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HitBox getHitBox() {
        return this.hitBoxComponent.getHitBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectType getType() {
        return ObjectType.PLAYER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBombLimit(final int newLimit) {
        this.bombManagerComponent.setBombLimit(newLimit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeed(final double newSpeed) {
        this.movementComponent.setSpeed(newSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSpeed() {
        return this.movementComponent.getSpeed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBombLimit() {
        return this.bombManagerComponent.getBombLimit();
    }

    private void placeBomb(final Maze maze, final long deltaTime) {
        final Optional<BlankSpace> bombPlacement = this.hitBoxComponent.closestBlankSpace(maze);

        if (bombPlacement.isPresent()) {
            this.bombManagerComponent.place(maze, bombPlacement.get().position(), deltaTime);
        } else {
            this.bombManagerComponent.abortPlace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection(final Direction direction) {
        if (Objects.isNull(direction)) {
            throw new IllegalArgumentException();
        } else {
            this.movementComponent.setDirection(direction);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyToPlace() {
        this.bombManagerComponent.notifyToPlace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBombType(final BombType type) {
        this.bombManagerComponent.setBombType(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BombType getBombType() {
        return this.bombManagerComponent.getBombType();
    }
}
