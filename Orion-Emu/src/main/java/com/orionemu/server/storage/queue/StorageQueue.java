package com.orionemu.server.storage.queue;

import com.orionemu.server.tasks.OrionTask;
import com.orionemu.server.utilities.Initialisable;

public interface StorageQueue<T> extends Initialisable, OrionTask {

    void queueSave(T object);

    void unqueue(T object);

    boolean isQueued(T object);

    void shutdown();
}
