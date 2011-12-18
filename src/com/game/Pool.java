package com.game;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
    public interface PoolObjectFactory<T> {
        public T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);
    }

    /* It creates if their is no free object */
    public T newObject() {
        T object = null;

        if (freeObjects.size() == 0)
            object = factory.createObject();
        else
            object = freeObjects.remove(freeObjects.size() - 1);

        return object;
    }

    /* It is will keep back only the maxSize number of free copies back.*/
    public void free(T object) {
        if (freeObjects.size() < maxSize)
            freeObjects.add(object);
    }
}
