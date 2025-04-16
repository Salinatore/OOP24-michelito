package it.unibo.michelito.model.bomb.impl;

import it.unibo.michelito.model.bomb.api.Bomb;
import it.unibo.michelito.model.flame.api.Flame;
import it.unibo.michelito.model.flame.api.FlamePropagation;
import it.unibo.michelito.model.flame.impl.FlameFactoryImpl;
import it.unibo.michelito.model.flame.impl.FlamePropagationImpl;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;

import java.util.Set;

/**
 * Abstract class for bombs.
 */
public abstract class AbstractBomb implements Bomb {
    private final Position position;
    private static final long FUSE_TIME = 3000;
    private long remainingTime = FUSE_TIME;

    /**
     * Constructor for the bomb.
     *
     * @param position The position of the bomb.
     */
    protected AbstractBomb(final Position position) {
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final Maze maze) {
        remainingTime -= deltaTime;
        if (remainingTime <= 0) {
            explode(maze);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position position() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HitBox getHitBox() {
        return new HitBoxFactoryImpl().squareHitBox(this.position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectType getType() {
        return ObjectType.BOMB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int getRange();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean isPassThrough();

    /**
     * Explodes the bomb.
     *
     * @param maze The maze where the bomb is.
     */
    private void explode(final Maze maze) {
        maze.removeMazeObject(this);
        generateFlame(maze);
    }

    /**
     * Generates the flame after the bomb explodes.
     *
     * @param maze The maze where the bomb is.
     */
    private void generateFlame(final Maze maze) {
        final FlamePropagation flamePropagation = new FlamePropagationImpl(new FlameFactoryImpl());
        final Set<Flame> flames = flamePropagation.propagate(
                this.position,
                getRange(),
                isPassThrough(),
                maze
        );
        flames.forEach(maze::addMazeObject);
    }
}
