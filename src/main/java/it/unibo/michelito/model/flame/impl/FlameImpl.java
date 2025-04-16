package it.unibo.michelito.model.flame.impl;

import it.unibo.michelito.model.enemy.api.Enemy;
import it.unibo.michelito.model.flame.api.Flame;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.player.api.Player;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * A flame that is created when a bomb explodes.
 */
public class FlameImpl implements Flame {
    private final Position position;
    private long timeLeft;

    /**
     * Constructor for the flame.
     *
     * @param position The position of the flame.
     */
    public FlameImpl(final Position position) {
        this.position = position;
        this.timeLeft = 1000;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position position() {
        return position;
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
        return ObjectType.FLAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final Maze maze) {
        computeTimeToExtinguish(maze, deltaTime);
        checkAndKillMichelito(maze, getHitBox());
        checkAndKillEnemies(maze, getHitBox());
    }

    /**
     * Compute the time left for the flame to extinguish.
     * If the time is up, the flame is removed from the maze.
     *
     * @param maze The maze where the flame is located.
     * @param deltaTime The time passed since the last update.
     */
    private void computeTimeToExtinguish(final Maze maze, final long deltaTime) {
        this.timeLeft -= deltaTime;
        if (this.timeLeft <= 0) {
            maze.removeMazeObject(this);
        }
    }

    /**
     * Check if the flame {@link HitBox} collides with {@link Player} {@link HitBox}.
     * If so, Michelito is killed.
     *
     * @param maze The maze where the flame is located.
     * @param flameHitBox The {@link HitBox} of the flame.
     */
    private void checkAndKillMichelito(final Maze maze, final HitBox flameHitBox) {
        final Player player = maze.getPlayer();
        if (flameHitBox.collision(player.getHitBox())) {
            maze.killMichelito();
        }
    }

    /**
     * Check if the flame {@link HitBox} collides with {@link Enemy} {@link HitBox}.
     * If so, the enemy is killed.
     *
     * @param maze The maze where the flame is located.
     * @param flameHitBox The {@link HitBox} of the flame.
     */
    private void checkAndKillEnemies(final Maze maze, final HitBox flameHitBox) {
        final Set<Enemy> enemiesToRemove = new HashSet<>();
        for (final Enemy enemy : maze.getEnemies()) {
            if (flameHitBox.collision(enemy.getHitBox())) {
                enemiesToRemove.add(enemy);
            }
        }
        enemiesToRemove.forEach(maze::removeMazeObject);
    }
}
