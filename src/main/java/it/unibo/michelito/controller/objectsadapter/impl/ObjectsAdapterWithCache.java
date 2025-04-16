package it.unibo.michelito.controller.objectsadapter.impl;

import it.unibo.michelito.controller.objectsadapter.api.ObjectsAdapter;
import it.unibo.michelito.model.util.MazeObject;
import it.unibo.michelito.util.GameObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * A proxy implementation of {@link ObjectsAdapter} that caches the results of maze object requests.
 * This class enhances performance by storing previously retrieved sets of {@link MazeObject},
 * avoiding redundant calls to the underlying {@link ObjectsAdapterImpl}.
 * While this class is public, it is recommended to use the {@link ObjectsAdapterFactory} for creating instances.
 */
public class ObjectsAdapterWithCache implements ObjectsAdapter {
    private final ObjectsAdapter base;
    private final Map<Integer, Set<MazeObject>> cache = new HashMap<>();

    /**
     * Construct a ObjectsAdapterWithCache.
     *
     * @param levelGenerator {@link Function} that provided a number return a Set of {@link GameObject}.
     */
    public ObjectsAdapterWithCache(final Function<Integer, Set<GameObject>> levelGenerator) {
        this.base = new ObjectsAdapterImpl(levelGenerator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MazeObject> requestMazeObjects(final int level) {
        return this.cache.computeIfAbsent(level, base::requestMazeObjects);
    }
}
