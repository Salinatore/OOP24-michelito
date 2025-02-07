package it.unibo.michelito.model.maze;

import it.unibo.michelito.model.box.impl.BoxImpl;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.maze.impl.MazeImpl;
import it.unibo.michelito.model.modelutil.MazeObject;
import it.unibo.michelito.model.modelutil.Temporary;
import it.unibo.michelito.model.player.impl.PlayerImpl;
import it.unibo.michelito.model.wall.impl.WallImpl;
import it.unibo.michelito.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for {@link MazeImpl}.
 */
final class TestMaze {
    private Set<MazeObject> setOfObjects;

    /**
     * Creates a Set of MazeObjects to be used in each test.
     */
    @BeforeEach
    void setUp() {
        setOfObjects = new HashSet<>(Set.of(
                new WallImpl(new Position(0, 0)),
                new WallImpl(new Position(1, 1)),
                new WallImpl(new Position(2, 2)),
                new WallImpl(new Position(3, 3)),
                new BoxImpl(new Position(0, 0)),
                new BoxImpl(new Position(1, 1)),
                new BoxImpl(new Position(2, 2)),
                new BoxImpl(new Position(3, 3)),
                new PlayerImpl(new Position(0, 0))
        ));
    }

    /**
     * Tests add and remove feature of maze.
     */
    @Test
    void testAddAndRemove() {
        final Temporary temporaryObject = new BoxImpl(new Position(4, 4));
        final Maze maze = new MazeImpl(setOfObjects, () -> { }, () -> { });
        assertNotNull(maze);
        assertFalse(maze.getAllObjects().isEmpty());
        assertFalse(maze.getAllObjects().contains(temporaryObject));
        assertTrue(maze.addMazeObject(temporaryObject));
        assertTrue(maze.getBoxes().contains(temporaryObject));
        assertTrue(maze.getAllObjects().contains(temporaryObject));
        assertTrue(maze.removeMazeObject(temporaryObject));
        assertFalse(maze.getBoxes().contains(temporaryObject));
    }

    /**
     * Tests that both add and remove throw exception.
     */
    @Test
    void testConsistency() {
        final Maze maze = new MazeImpl(setOfObjects, () -> { }, () -> { });
        assertThrows(NullPointerException.class, () -> maze.addMazeObject(null));
        assertThrows(NullPointerException.class, () -> maze.removeMazeObject(null));
        assertFalse(maze.removeMazeObject(new BoxImpl(new Position(4, 4))));
    }

    /**
     * Tests that the filter function.
     */
    @Test
    void testFilter() {
        final Maze maze = new MazeImpl(setOfObjects, () -> { }, () -> { });
        assertFalse(maze.getAllObjects().isEmpty());
        assertFalse(maze.getWalls().isEmpty());
        assertFalse(maze.getBoxes().isEmpty());
        assertTrue(maze.getPowerUp().isEmpty());
    }
}
