package it.unibo.michelito.controller.levelgenerator;

import it.unibo.michelito.controller.gamecontroller.api.GameExceptionHandler;
import it.unibo.michelito.util.GameObject;
import it.unibo.michelito.util.ObjectType;
import it.unibo.michelito.util.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * A class that create the maze reading form file.
 */
public class LevelGenerator implements Function<Integer, Set<GameObject>> {
    private static final int MAZE_BLOCK_WIDTH = 21;
    private static final int MAZE_BLOCK_HEIGHT = 15;
    private static final int BLOCK_EDGE = 6;
    private static final int TEST_MAZE_CODE = -1;

    private final GameExceptionHandler gameExceptionHandler;

    /**
     * @param exceptionHandler a handler that take possible exception so he can manage it.
     */
    public LevelGenerator(final GameExceptionHandler exceptionHandler) {
        this.gameExceptionHandler = exceptionHandler;
    }

    /**
     *
     * @param integer is the number of level to generate, if -1 is pass it will generate a base level with a player used for test.
     * @return a set of {@link GameObject} that represent every object in the current level maze.
     */
    @Override
    public Set<GameObject> apply(final Integer integer) {
        return generate(integer);
    }

     private Set<GameObject> generate(final int levelNumber) {
         final Set<GameObject> maze = new HashSet<>(baseMaze());
         final String filePath = "it.unibo.michelito/level/level" + levelNumber + ".txt";
         maze.addAll(baseMaze());
         if (levelNumber != TEST_MAZE_CODE) {
             final Set<GameObject> fileMaze = new HashSet<>(mazeFromFile(filePath));
             for (final GameObject gameObject : fileMaze) {
                 if (!overlap(maze, gameObject)) {
                     maze.add(gameObject);
                 }
             }
         } else {
             maze.add(new GameObject(ObjectType.PLAYER, new Position(BLOCK_EDGE, BLOCK_EDGE)));
         }
         return maze;
    }

    /**
     * @return the code for the test level.
     */
    public static int testLevel() {
         return TEST_MAZE_CODE;
    }

    /**
     * @return the base level that include the border wall and the wall grid inside it,
     * all the maze is covered whit blank space to help correct positioning.
     */
    private static Set<GameObject> baseMaze() {
        final Set<GameObject> maze = new HashSet<>();
        cellPositions().forEach(x -> maze.add(new GameObject(ObjectType.BLANK_SPACE, x)));
        for (int x = 0; x < MAZE_BLOCK_WIDTH; x++) {
            maze.add(new GameObject(ObjectType.WALL, new Position(x * BLOCK_EDGE, 0)));
            maze.add(new GameObject(ObjectType.WALL, new Position(x * BLOCK_EDGE, (MAZE_BLOCK_HEIGHT - 1) * BLOCK_EDGE)));
        }
        for (int x = 0; x < MAZE_BLOCK_HEIGHT; x++) {
            maze.add(new GameObject(ObjectType.WALL, new Position(0, x * BLOCK_EDGE)));
            maze.add(new GameObject(ObjectType.WALL, new Position((MAZE_BLOCK_WIDTH - 1) * BLOCK_EDGE, x * BLOCK_EDGE)));
        }
        for (int x = 0; x < MAZE_BLOCK_WIDTH; x = x + 2) {
            for (int y = 0; y < MAZE_BLOCK_HEIGHT; y = y + 2) {
                maze.add(new GameObject(ObjectType.WALL, new Position(x * BLOCK_EDGE, y * BLOCK_EDGE)));
            }
        }
        return maze;
    }

    /**
     * used for reed from file converting the string in the txt in object type and position.
     * @param file name of the file.
     * @return a set with all the game object form the file.
     */
    private Set<GameObject> mazeFromFile(final String file) {
        final Set<GameObject> maze = new HashSet<>();
        String objectType;
        double xValue;
        double yValue;
        final InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        GameObject readObject;
        try {
            if (is == null) {
                throw new IOException("file not found");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String riga = br.readLine();
                while (riga  != null) {
                    final String[] read = riga.split(" ");
                    objectType = read[0];
                    xValue = Double.parseDouble(read[1]);
                    yValue = Double.parseDouble(read[2]);
                    readObject = switch (objectType) {
                        case "wall" -> new GameObject(ObjectType.WALL, new Position(xValue, yValue));
                        case "box" -> new GameObject(ObjectType.BOX, new Position(xValue, yValue));
                        case "enemy" -> new GameObject(ObjectType.ENEMY, new Position(xValue, yValue));
                        case "door" -> new GameObject(ObjectType.DOOR, new Position(xValue, yValue));
                        case "player" -> new GameObject(ObjectType.PLAYER, new Position(xValue, yValue));
                        default -> throw new IOException("wrong object type");
                    };
                    if (cellPositions().contains(readObject.position()) && !overlap(maze, readObject)) {
                            maze.add(readObject);
                    }
                    riga = br.readLine();
                }
                // NOPMD - Suppress "ExceptionAsFlowControl" warning
            }
        } catch (IOException e) { // NOPMD
            this.gameExceptionHandler.gameControllerHandleException(e);
        }
        return maze;
    }

    private static Set<Position> cellPositions() {
        final Set<Position> positions = new HashSet<>();
        for (int x = 0; x < MAZE_BLOCK_WIDTH; x++) {
            for (int y = 0; y < MAZE_BLOCK_HEIGHT; y++) {
                positions.add(new Position(x * BLOCK_EDGE, y * BLOCK_EDGE));
            }
        }
        return positions;
    }

    private boolean overlap(final Set<GameObject> maze, final GameObject object) {
        if (object.objectType().equals(ObjectType.PLAYER)
                || object.objectType().equals(ObjectType.ENEMY)
                || object.objectType().equals(ObjectType.BOX)
                || object.objectType().equals(ObjectType.DOOR)) {
            return maze.stream().filter(x -> x.objectType().equals(ObjectType.WALL))
                    .map(GameObject::position).anyMatch(x -> x.equals(object.position()));
        }
        return false;
    }
}
