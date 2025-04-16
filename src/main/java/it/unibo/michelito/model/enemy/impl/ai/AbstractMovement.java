package it.unibo.michelito.model.enemy.impl.ai;

import it.unibo.michelito.model.enemy.api.ai.Movement;
import it.unibo.michelito.model.enemy.api.ai.MovementType;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.api.HitBoxFactory;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import it.unibo.michelito.util.Direction;
import it.unibo.michelito.util.Position;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Implementation of {@link Movement}.
 */
public abstract class AbstractMovement implements Movement {
    private Position position;
    private Direction direction = Direction.NONE;
    private static final int POSSIBILITY_TO_CHANGE = 30;
    private final Set<Direction> directions = Set.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    private final Random random = new Random();

    /**
     * @param position set the position where the movement is currently on.
     */
    @Override
    public void setPosition(final Position position) {
        this.position = position;
    }

    /**
     * @param maze the maze where the enemy is.
     * @param time a delta time between a move and another.
     */
    @Override
    public void move(final Maze maze, final long time) {
        if (direction == Direction.NONE
                || shift(maze, time, this.direction).equals(this.position)
                || random.nextInt(POSSIBILITY_TO_CHANGE) == 0) {
            final List<Direction> possibleWay = new ArrayList<>();
            possibleWay.add(Direction.NONE);
            possibleWay.addAll(directions.stream().filter(x -> !shift(maze, time, x).equals(this.position)).toList());
            this.direction = possibleWay.get(random.nextInt(possibleWay.size()));
        }
        this.position = shift(maze, time, this.direction);
    }

    /**
     * @return the movement type.
     */
    @Override
    public MovementType getMovementType() {
        return movementType();
    }

    /**
     * @return the position that the movement is currently on.
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    private boolean findCollision(final Maze maze, final HitBox calshitbox) {
        return maze.getWalls().stream()
                .anyMatch(w -> calshitbox.collision(w.getHitBox()))
                ||
                maze.getBoxes().stream()
                        .anyMatch(b -> calshitbox.collision(b.getHitBox()));
    }

    private HitBox updateHitBox(final Position position) {
        final HitBoxFactory hitboxfactory = new HitBoxFactoryImpl();
        return hitboxfactory.entityeHitBox(position);
    }

    private Position shift(final Maze maze, final long time, final Direction direction) {
        final BigDecimal xMove = BigDecimal.valueOf(time).multiply(BigDecimal.valueOf(direction.toPosition().x() * velocity()));
        final BigDecimal yMove = BigDecimal.valueOf(time).multiply(BigDecimal.valueOf(direction.toPosition().y() * velocity()));

        final BigDecimal xValue = BigDecimal.valueOf(this.position.x()).add(xMove);
        final BigDecimal yValue = BigDecimal.valueOf(this.position.y()).add(yMove);

        final Position newposition = new Position(xValue.doubleValue(), yValue.doubleValue());

        final HitBox newhitbox = updateHitBox(newposition);
        if (!findCollision(maze, newhitbox)) {
            return newposition;
        }
        return position;
    }

    abstract double velocity();

    abstract MovementType movementType();
}
