package it.unibo.michelito.model.door;

import it.unibo.michelito.controller.levelgenerator.LevelGenerator;
import it.unibo.michelito.model.door.api.Door;
import it.unibo.michelito.model.door.impl.DoorImpl;
import it.unibo.michelito.model.enemy.api.Enemy;
import it.unibo.michelito.model.enemy.impl.EnemyImpl;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.maze.impl.MazeImpl;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for the {@link DoorImpl} class.
 */
final class TestDoor {
    public static final int X = 0;
    public static final int Y = 4;
    private static final int TIME = 0; //The TIME is irrelevant when updating a door
    public static final int INITIAL_POSITION = 10;
    private Door door;
    private Position position;

    /**
     * Sets up the test environment before each test.
     * Initializes a new {@link DoorImpl} instance with a predefined position.
     */
    @BeforeEach
    void setUp() {
        this.position = new Position(X, Y);
        this.door = new DoorImpl(position);
    }

    /**
     * Tests the {@code getHitBox} method to ensure it returns the correct {@link HitBox}.
     */
    @Test
    void testGetHitBox() {
        final HitBox expectedHitBox = new HitBoxFactoryImpl().squareHitBox(this.position);
        assertEquals(expectedHitBox, this.door.getHitBox(), "HitBox should be squareHitBox");
    }

    /**
     * Tests the {@code getType} method to ensure it returns the correct {@link ObjectType}.
     */
    @Test
    void testGetType() {
        assertEquals(ObjectType.DOOR, this.door.getType(), "Type should be DOOR");
    }

    /**
     * Tests the behavior of a door opening when all {@link Enemy}s are removed from the {@link Maze}.
     */
    @Test
    void testOpening() {
        final var enemy = new EnemyImpl(new Position(INITIAL_POSITION, INITIAL_POSITION));
        final Maze maze = new MazeImpl(LevelGenerator.testLevel(), new LevelGenerator(e -> { }));
        assertFalse(this.door.isOpen());
        maze.addMazeObject(enemy);
        this.door.update(TIME, maze);
        assertFalse(this.door.isOpen());
        maze.removeMazeObject(enemy);
        this.door.update(TIME, maze);
        assertTrue(this.door.isOpen());
    }

    /**
     * Tests the behavior of a door setting the {@link Maze} as won
     * when it's open and the player is over it.
     */
    @Test
    void testMazeWin() {
        final MazeImpl maze = new MazeImpl(LevelGenerator.testLevel(), new LevelGenerator(e -> { }));
        final Door doorUnderPlayer = new DoorImpl(maze.getPlayer().position());
        assertFalse(maze.isWon());
        doorUnderPlayer.update(TIME, maze);
        assertTrue(doorUnderPlayer.isOpen());
        assertTrue(maze.isWon());
    }
}
