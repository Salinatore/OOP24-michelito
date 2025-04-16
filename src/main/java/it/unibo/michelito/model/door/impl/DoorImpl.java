package it.unibo.michelito.model.door.impl;

import it.unibo.michelito.model.door.api.Door;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;

/**
 * Implementation of the {@link Door} interface, representing a door in the maze.
 * <p>
 * The door can be either open or closed. It is opened when all enemies are eliminated from the maze.
 * Michelito can interact with the door by colliding with it when it is open.
 * </p>
 */
public final class DoorImpl implements Door {
    private boolean open;
    private final Position position;

    /**
     * Constructs a {@link DoorImpl} with the specified position.
     *
     * @param position the position of the door in the maze.
     */
    public DoorImpl(final Position position) {
        this.position = position;
    }

    @Override
    public void update(final long deltaTime, final Maze maze) {
        if (maze.getEnemies().isEmpty()) {
            this.open = true;
        }
        if (maze.getPlayer().getHitBox().collision(this.getHitBox()) && this.isOpen()) {
            maze.enterTheDoor();
        }
    }

    @Override
    public Position position() {
        return this.position;
    }

    @Override
    public HitBox getHitBox() {
        return new HitBoxFactoryImpl().squareHitBox(this.position);
    }

    @Override
    public ObjectType getType() {
        return ObjectType.DOOR;
    }

    @Override
    public boolean isOpen() {
        return this.open;
    }
}
