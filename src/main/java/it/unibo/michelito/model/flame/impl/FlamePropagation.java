package it.unibo.michelito.model.flame.impl;

import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.util.Direction;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.modelutil.hitbox.api.HitBox;
import it.unibo.michelito.model.modelutil.hitbox.impl.HitBoxFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FlamePropagation {
    UP(Direction.UP),
    DOWN(Direction.DOWN),
    LEFT(Direction.LEFT),
    RIGHT(Direction.RIGHT);

    private final Direction direction;

    FlamePropagation(final Direction direction) {
        this.direction = direction;
    }

    public List<HitBox> updateHitBox(final Position origin, final int range, final boolean passThrough, final Maze maze) {
        final List<HitBox> hitBoxes = new ArrayList<>();
        final HitBoxFactoryImpl hitBoxFactory = new HitBoxFactoryImpl();
        final Position delta = direction.toPosition();
        for (int i = 1; i <= range; i++) {
            final Position newPos = new Position(origin.x() + i * delta.x(), origin.y() + i * delta.y());
            if (isWall(maze, newPos)) {
                break;
            }
            if (isBox(maze, newPos)) {
                removeBox(maze, newPos);
                if (!passThrough) {
                    break;
                }
            }
            hitBoxes.add(hitBoxFactory.squareHitBox(newPos));
        }
        return hitBoxes;
    }

    public static List<HitBox> getFlameHitBoxes(final Position origin, final int range, final boolean passThrough, final Maze maze) {
        final List<HitBox> allHitBoxes = new ArrayList<>();
        final HitBoxFactoryImpl hitBoxFactory = new HitBoxFactoryImpl();
        allHitBoxes.add(hitBoxFactory.squareHitBox(origin));
        Arrays.stream(values())
                .map(fp -> fp.updateHitBox(origin, range, passThrough, maze))
                .forEach(allHitBoxes::addAll);
        return allHitBoxes;
    }

    private boolean isWall(final Maze maze, final Position pos) {
        return maze.getWalls().stream().anyMatch(wall -> wall.position().equals(pos));
    }

    private boolean isBox(final Maze maze, final Position pos) {
        return maze.getBoxes().stream().anyMatch(box -> box.position().equals(pos));
    }

    private void removeBox(final Maze maze, final Position pos) {
        maze.getBoxes().stream()
                .filter(box -> box.position().equals(pos))
                .findFirst()
                .ifPresent(maze::removeMazeObject);
    }

}
