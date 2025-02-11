package it.unibo.michelito.model.flame.impl;

import it.unibo.michelito.model.flame.api.Flame;
import it.unibo.michelito.model.flame.api.FlameFactory;
import it.unibo.michelito.model.flame.api.FlamePropagation;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.powerups.api.PowerUp;
import it.unibo.michelito.model.powerups.api.PowerUpFactory;
import it.unibo.michelito.model.powerups.api.PowerUpType;
import it.unibo.michelito.model.powerups.impl.PowerUpFactoryImpl;
import it.unibo.michelito.util.Direction;
import it.unibo.michelito.util.Position;

import java.math.BigDecimal;
import java.util.*;

/**
 * Implementation of {@link FlamePropagation}.
 */
public class FlamePropagationImpl implements FlamePropagation {
    private static final long BLOCK_SIZE = 6;
    private final FlameFactory flameFactory;
    private static final double DROP_CHANCE = 1;

    /**
     * Constructs a {@code FlamePropagationImpl} with the specified {@link FlameFactory}.
     *
     * @param flameFactory the {@link FlameFactory} used to create flames
     */
    public FlamePropagationImpl(FlameFactory flameFactory) {
        this.flameFactory = flameFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Flame> propagate(Position origin, int range, boolean passThrough, Maze maze) {
        Set<Flame> allFlames = new HashSet<>();
        allFlames.add(flameFactory.createFlame(origin));
        maze.addMazeObject(flameFactory.createFlame(origin));
        for (Direction direction : Direction.values()) {
            Position delta = direction.toPosition();
            if (!(delta.x() != 0 && delta.y() != 0)) {
                allFlames.addAll(createFlames(origin, direction, range, passThrough, maze));
            }
        }
        return allFlames;
    }

    /**
     * Creates a set of {@link Flame}s in the specified {@link Direction} from the specified {@link Position}.
     *
     * @param origin      the {@link Position} where the flames start
     * @param direction   the {@link Direction} in which the flames propagate
     * @param range       the range of the flames
     * @param passThrough {@code true} if the flames pass through boxes, {@code false} otherwise
     * @param maze        the {@link Maze} where the flames are created
     * @return a set of {@link Flame}s created in the specified {@link Direction} from the specified {@link Position}
     */
    private Set<Flame> createFlames(Position origin, Direction direction, int range, boolean passThrough, Maze maze) {
        Set<Flame> flames = new HashSet<>();
        Position delta = direction.toPosition();
        for (int i = 1; i <= range; i++) {
            Position newPos = new Position(
                    BigDecimal.valueOf(origin.x())
                            .add(BigDecimal.valueOf(i).
                                    multiply(BigDecimal.valueOf(delta.x())
                                            .multiply(BigDecimal.valueOf(BLOCK_SIZE))))
                            .doubleValue(),
                    BigDecimal.valueOf(origin.y())
                            .add(BigDecimal.valueOf(i)
                                    .multiply(BigDecimal.valueOf(delta.y())
                                            .multiply(BigDecimal.valueOf(BLOCK_SIZE))))
                            .doubleValue()
            );
            if (isWall(maze, newPos)) {
                break;
            }
            if (isBox(maze, newPos)) {
                maze.removeMazeObject(maze.getBoxes().stream().filter(box -> box.position().equals(newPos)).findFirst().get());
                dropRandomPowerUp(newPos).ifPresent(maze::addMazeObject);
                if (!passThrough) {
                    break;
                }
            }
            Flame flame = flameFactory.createFlame(newPos);
            flames.add(flame);
            maze.addMazeObject(flame);
        }
        return flames;
    }

    /**
     * Checks if the specified {@link Position} is a wall.
     *
     * @param maze the {@link Maze} to check
     * @param pos  the {@link Position} to check
     * @return {@code true} if the specified {@link Position} is a wall, {@code false} otherwise
     */
    private boolean isWall(Maze maze, Position pos) {
        return maze.getWalls().stream().anyMatch(wall -> wall.position().equals(pos));
    }

    /**
     * Checks if the specified {@link Position} is a box.
     *
     * @param maze the {@link Maze} to check
     * @param pos  the {@link Position} to check
     * @return {@code true} if the specified {@link Position} is a box, {@code false} otherwise
     */
    private boolean isBox(Maze maze, Position pos) {
        return maze.getBoxes().stream().anyMatch(box -> box.position().equals(pos));
    }

    /**
     * Drops a random {@link PowerUp} at the specified {@link Position}.
     *
     * @param pos the {@link Position} where the {@link PowerUp} is dropped
     * @return an {@link Optional} containing the dropped {@link PowerUp} if it was dropped, an empty {@link Optional} otherwise
     */
    private Optional<PowerUp> dropRandomPowerUp(Position pos) {
        final Random random = new Random();
        final double chance = random.nextDouble();
        PowerUpFactory factory = new PowerUpFactoryImpl();
        List<PowerUp> list = new ArrayList<>();
        List<PowerUpType> powerUpTypes = Arrays.stream(PowerUpType.values()).toList();
        for (PowerUpType powerUpType : powerUpTypes) {
            list.add(factory.createPowerUp(pos, powerUpType));
        }
        if (chance <= DROP_CHANCE) {
            return Optional.of(list.get(random.nextInt(list.size())));
        } else {
            return Optional.empty();
        }
    }
}