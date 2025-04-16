package it.unibo.michelito.model.enemy;

import it.unibo.michelito.controller.levelgenerator.LevelGenerator;
import it.unibo.michelito.model.enemy.api.Enemy;
import it.unibo.michelito.model.enemy.api.ai.MoodAI;
import it.unibo.michelito.model.enemy.api.ai.MovementType;
import it.unibo.michelito.model.enemy.impl.EnemyImpl;
import it.unibo.michelito.model.enemy.impl.ai.MoodAIImpl;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.maze.impl.MazeImpl;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.util.hitbox.api.HitBoxFactory;
import it.unibo.michelito.model.util.hitbox.impl.HitBoxFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test for {@link EnemyImpl}.
 */
class TestEnemy {
    private static final long SLEEP_TIME = 100_000;
    private static final Position BASE_POSITION = new Position(6, 6);
    private static final long TESTDELTA1 = 10;
    private static final long TESTDELTA2 = 5;

    private Enemy enemy;
    private Maze maze;

    @BeforeEach
    void setUp() {
        final int levelNumber = -1;
        maze = new MazeImpl(levelNumber, new LevelGenerator(e -> { }));
        final Position initalPosition = new Position(6, 6);
        this.enemy = new EnemyImpl(initalPosition);
    }

    @Test
    void testMovement() {
        final HitBoxFactory testHitBoxFact = new HitBoxFactoryImpl();
        final HitBox testHitBox = testHitBoxFact.entityeHitBox(BASE_POSITION);
        assertEquals(testHitBox, this.enemy.getHitBox());
        enemy.update(SLEEP_TIME, maze);
        enemy.update(TESTDELTA1, maze);
        assertNotEquals(BASE_POSITION, enemy.position());
        final Position newPosition = enemy.position();
        enemy.update(TESTDELTA2, maze);
        assertNotEquals(newPosition, enemy.position());
    }

    @Test
   void testAI() {
        final MoodAI moodAI = new MoodAIImpl(maze);
        assertEquals(MovementType.SLEEPING, moodAI.getMovement().getMovementType());
        moodAI.update(SLEEP_TIME, maze);
        assertEquals(MovementType.CHILLING, moodAI.getMovement().getMovementType());
    }

}
