package it.unibo.michelito.model.enemy.impl;

import it.unibo.michelito.model.enemy.api.Enemy;
import it.unibo.michelito.model.enemy.api.ai.MoodAI;
import it.unibo.michelito.model.enemy.api.ai.Movement;
import it.unibo.michelito.model.enemy.impl.ai.MoodAIImpl;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.api.HitBoxFactory;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;

/**
 * Implementation of {@link Enemy}.
 */
public final class EnemyImpl implements Enemy {
    private Position actualposition;
    private Movement movement;
    private MoodAI moodAI;
    private HitBox hitBox;

    /**
     *Constructor for {@link EnemyImpl}.
     * @param position the initial position of enemy.
     */
    public EnemyImpl(final Position position) {
        this.actualposition = position;
        updateHitBox(position);
    }

    @Override
    public void update(final long deltaTime, final Maze maze) {
        if (moodAI == null) {
            moodAI = new MoodAIImpl(maze);
            movement = moodAI.getMovement();
        }
        if (!movement.getMovementType().equals(moodAI.getMovement().getMovementType())) {
            movement = moodAI.getMovement();
        }
        movement.setPosition(actualposition);
        verifyHitPlayer(maze);
        movement.move(maze, deltaTime);
        this.actualposition = movement.getPosition();
        updateHitBox(actualposition);
        verifyHitPlayer(maze);
        moodAI.update(deltaTime, maze);
    }

    @Override
    public Position position() {
        return actualposition;
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.ENEMY;
    }

    private void updateHitBox(final Position position) {
        final HitBoxFactory hitboxfactory = new HitBoxFactoryImpl();
        hitBox = hitboxfactory.entityeHitBox(position);
    }

    private void verifyHitPlayer(final Maze maze) {
        if (this.hitBox.collision(maze.getPlayer().getHitBox())) {
            maze.killMichelito();
        }
    }
}
