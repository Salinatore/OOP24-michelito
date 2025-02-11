package it.unibo.michelito.controller.objectsadapter.impl;

import it.unibo.michelito.controller.objectsadapter.api.ObjectsAdapter;
import it.unibo.michelito.util.GameObject;

import java.util.Set;
import java.util.function.Function;

/**
 * Factory class for creating instances of {@link ObjectsAdapter}.
 * Its use is recommended for obtaining {@link ObjectsAdapter} instances.
 */
public class ObjectsAdapterFactory {
    /**
     * Private constructor preventing instantiation.
     */
    private ObjectsAdapterFactory() {}

    /**
     * Creates and returns an instance of {@link ObjectsAdapter}.
     * The implementation choice may vary.
     *
     * @return a new {@link ObjectsAdapter} instance.
     */
    public static ObjectsAdapter createObjectsAdapter(Function<Integer, Set<GameObject>> levelGenerator) {
        return new ObjectsAdapterWithCache(levelGenerator);
    }
}
