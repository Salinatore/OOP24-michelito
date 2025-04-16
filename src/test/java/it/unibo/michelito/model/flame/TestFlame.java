package it.unibo.michelito.model.flame;

import it.unibo.michelito.controller.levelgenerator.LevelGenerator;
import it.unibo.michelito.model.flame.api.Flame;
import it.unibo.michelito.model.flame.api.FlameFactory;
import it.unibo.michelito.model.flame.api.FlamePropagation;
import it.unibo.michelito.model.flame.impl.FlameFactoryImpl;
import it.unibo.michelito.model.flame.impl.FlamePropagationImpl;
import it.unibo.michelito.model.maze.impl.MazeImpl;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.player.impl.PlayerImpl;
import it.unibo.michelito.model.enemy.impl.EnemyImpl;
import it.unibo.michelito.model.box.impl.BoxImpl;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.util.ObjectType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Flame}.
 */
final class TestFlame {
    private MazeImpl maze;
    private FlameFactory flameFactory;
    private FlamePropagation flamePropagation;

    private static final double X_SPAWN = 6;
    private static final double Y_SPAWN = 6;
    private static final double BLOCK_SIZE = 6;
    private static final long FLAME_LIFETIME = 1000;
    private static final int BOMB_RANGE = 1;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        this.maze = new MazeImpl(LevelGenerator.testLevel(), new LevelGenerator(e -> { }));
        this.flameFactory = new FlameFactoryImpl();
        this.flamePropagation = new FlamePropagationImpl(flameFactory);
    }

    /**
     * Test the creation of a flame.
     */
    @Test
    void testFlameCreation() {
        final Flame flame = flameFactory.createFlame(new Position(X_SPAWN, Y_SPAWN));
        maze.addMazeObject(flame);
        final HitBox hitBox = flame.getHitBox();

        assertEquals(new Position(X_SPAWN, Y_SPAWN), flame.position());
        assertEquals(ObjectType.FLAME, flame.getType());
        assertNotNull(hitBox);
    }

    /**
     * Test that a flame extinguishes after a certain amount of time.
     */
    @Test
    void testFlameExtinguishesAfterTime() {
        final Flame flame = flameFactory.createFlame(new Position(X_SPAWN, Y_SPAWN));
        maze.addMazeObject(flame);
        for (long time = 0; time <= FLAME_LIFETIME; time += 100) {
            flame.update(100, maze);
        }

        assertFalse(maze.getAllObjects().contains(flame));
    }

    /**
     * Test that a flame kills Michelito.
     */
    @Test
    void testFlameKillsMichelito() {
        final PlayerImpl player = new PlayerImpl(new Position(X_SPAWN, Y_SPAWN));
        maze.addMazeObject(player);
        final Set<Flame> flames = flamePropagation.propagate(
                new Position(X_SPAWN, Y_SPAWN),
                BOMB_RANGE,
                false,
                maze
        );
        flames.forEach(maze::addMazeObject);
        flames.forEach(flame -> flame.update(100, maze));

        assertTrue(maze.isLost());
    }

    /**
     * Test that a flame kills enemies.
     */
    @Test
    void testFlameKillsEnemy() {
        final EnemyImpl enemy = new EnemyImpl(new Position(X_SPAWN, Y_SPAWN));
        maze.addMazeObject(enemy);
        final Set<Flame> flames = flamePropagation.propagate(
                new Position(X_SPAWN, Y_SPAWN),
                BOMB_RANGE,
                false,
                maze
        );
        flames.forEach(maze::addMazeObject);
        flames.forEach(flame -> flame.update(100, maze));

        assertFalse(maze.getEnemies().contains(enemy));
    }

    /**
     * Test that a flame destroys boxes.
     */
    @Test
    void testFlameDestroysBox() {
        final BoxImpl box = new BoxImpl(new Position(X_SPAWN + BLOCK_SIZE, Y_SPAWN));
        maze.addMazeObject(box);
        final Set<Flame> flames = flamePropagation.propagate(
                new Position(X_SPAWN, Y_SPAWN),
                BOMB_RANGE,
                false,
                maze
        );
        flames.forEach(maze::addMazeObject);

        assertFalse(maze.getBoxes().contains(box));
    }
}
