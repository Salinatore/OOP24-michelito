package it.unibo.michelito.controller.objectsadapter.api;

import it.unibo.michelito.controller.levelgenerator.LevelGenerator;
import it.unibo.michelito.model.util.MazeObject;

import java.util.Set;

/**
 * Represents an ObjectsAdapter.
 * Adapter for the LevelGenerator.
 */
@FunctionalInterface
public interface ObjectsAdapter {
    /**
     * This method retrieves a set of {@link MazeObject} from the {@link LevelGenerator}.
     *
     * @param level the level to generate the set of {@link MazeObject}s.
     * @return a set of {@link MazeObject} representing the level.
     */
    Set<MazeObject> requestMazeObjects(int level);
}
