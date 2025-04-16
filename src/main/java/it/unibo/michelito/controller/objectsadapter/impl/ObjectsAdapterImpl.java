package it.unibo.michelito.controller.objectsadapter.impl;
import it.unibo.michelito.controller.objectsadapter.api.ObjectsAdapter;
import it.unibo.michelito.model.blanckspace.impl.BlankSpaceImpl;
import it.unibo.michelito.model.box.impl.BoxImpl;
import it.unibo.michelito.model.door.impl.DoorImpl;
import it.unibo.michelito.model.enemy.impl.EnemyImpl;
import it.unibo.michelito.model.util.MazeObject;
import it.unibo.michelito.model.player.impl.PlayerImpl;
import it.unibo.michelito.model.wall.impl.WallImpl;
import it.unibo.michelito.util.GameObject;
import it.unibo.michelito.util.ObjectType;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ObjectsAdapter} interface.
 * This class adapts objects from the LevelGenerator into a set of {@link MazeObject}s.
 * While this class is public, it is recommended to use the {@link ObjectsAdapterFactory} for creating instances.
 */
public class ObjectsAdapterImpl implements ObjectsAdapter {
    private static final Map<ObjectType, Function<GameObject, MazeObject>> OBJECT_CREATORS = Map.of(
            ObjectType.PLAYER, obj -> new PlayerImpl(obj.position()),
            ObjectType.BOX, obj -> new BoxImpl(obj.position()),
            ObjectType.WALL, obj -> new WallImpl(obj.position()),
            ObjectType.ENEMY, obj -> new EnemyImpl(obj.position()),
            ObjectType.DOOR, obj -> new DoorImpl(obj.position()),
            ObjectType.BLANK_SPACE, obj -> new BlankSpaceImpl(obj.position())
    );
    private final Function<Integer, Set<GameObject>> levelGenerator;

    /**
     * Construct a ObjectsAdapterImpl.
     *
     * @param levelGenerator {@link Function} that provided a number return a Set of {@link GameObject}.
     */
    public ObjectsAdapterImpl(final Function<Integer, Set<GameObject>> levelGenerator) {
        this.levelGenerator = levelGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MazeObject> requestMazeObjects(final int level) {
        return levelGenerator.apply(level).stream()
                .map(this::objectTransformer)
                .collect(Collectors.toSet());
    }

    /**
     * Transforms a {@link GameObject} into a corresponding {@link MazeObject} based on its type.
     * If the object type is not supported, an {@link IllegalArgumentException} is thrown.
     *
     * @param gameObject the {@link GameObject} to transform.
     * @return the corresponding {@link MazeObject}.
     * @throws IllegalArgumentException if the object type is not supported.
     */
    private MazeObject objectTransformer(final GameObject gameObject) {
        return OBJECT_CREATORS.getOrDefault(
                gameObject.objectType(),
                obj -> {
                    throw new IllegalArgumentException("Object type " + gameObject.objectType() + " not supported");
                }
        ).apply(gameObject);
    }
}
