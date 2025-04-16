package it.unibo.michelito.model.maze;

import it.unibo.michelito.controller.levelgenerator.LevelGenerator;
import it.unibo.michelito.controller.playercommand.impl.MoveCommand;
import it.unibo.michelito.model.box.impl.BoxImpl;
import it.unibo.michelito.model.enemy.impl.EnemyImpl;
import it.unibo.michelito.model.maze.impl.MazeImpl;
import it.unibo.michelito.model.util.Temporary;
import it.unibo.michelito.util.Direction;
import it.unibo.michelito.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test for the {@link MazeImpl} class.
 */
final class TestMaze {
    public static final int X = 0;
    public static final int Y = 4;
    public static final int DELTA_TIME = 100;
    public static final int ENEMY_POSITION = 10; //used for x and y, for simplicity
    public static final int BOX_POSITION = 4; //used for x and y, for simplicity
    private MazeImpl maze;

    /**
     * Sets up the test environment before each test.
     * Initializes a new BaseMaze.
     */
    @BeforeEach
    void setUp() {
        this.maze = new MazeImpl(LevelGenerator.testLevel(), new LevelGenerator(e -> { }));
    }

    /**
     * Tests add and remove feature of maze.
     */
    @Test
    void testAddAndRemove() {
        final Temporary temporaryObject = new BoxImpl(new Position(X, Y));
        assertNotNull(this.maze);
        assertFalse(this.maze.getAllObjects().isEmpty());
        assertFalse(this.maze.getAllObjects().contains(temporaryObject));
        assertTrue(this.maze.addMazeObject(temporaryObject));
        assertTrue(this.maze.getBoxes().contains(temporaryObject));
        assertTrue(this.maze.getAllObjects().contains(temporaryObject));
        assertTrue(this.maze.removeMazeObject(temporaryObject));
        assertFalse(this.maze.getBoxes().contains(temporaryObject));
    }

    /**
     * Tests that both add and remove throw exception.
     */
    @Test
    void testConsistency() {
        assertThrows(NullPointerException.class, () -> this.maze.addMazeObject(null));
        assertThrows(NullPointerException.class, () -> this.maze.removeMazeObject(null));
        assertFalse(this.maze.removeMazeObject(new BoxImpl(new Position(BOX_POSITION, BOX_POSITION))));
    }

    /**
     * Tests that the filter function.
     */
    @Test
    void testFilter() {
        assertFalse(this.maze.getAllObjects().isEmpty());
        assertFalse(this.maze.getWalls().isEmpty());
        assertTrue(this.maze.getBombs().isEmpty());
        assertTrue(this.maze.getPowerUp().isEmpty());
    }

    /**
     * Tests the maze won and lost states.
     */
    @Test
    void testWinAndLost() {
        assertFalse(this.maze.isWon());
        assertFalse(this.maze.isLost());
        this.maze.killMichelito();
        assertTrue(this.maze.isLost());
        this.maze.enterTheDoor();
        assertTrue(this.maze.isWon());
    }

    /**
     * Test {@code Update} method.
     */
    @Test
    void testUpdate() {
        final var initialPlayerPosition = this.maze.getPlayer().position();
        final var initialEnemyPosition = new Position(ENEMY_POSITION, ENEMY_POSITION);
        this.maze.addMazeObject(new EnemyImpl(initialEnemyPosition));
        this.maze.setCommand(new MoveCommand(Direction.DOWN));
        this.maze.update(DELTA_TIME);
        assertNotEquals(initialPlayerPosition, this.maze.getPlayer().position());
        assertNotEquals(initialPlayerPosition, this.maze.getPlayer().position());
    }
}
