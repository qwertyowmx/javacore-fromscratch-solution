package io.qwertyowrmx.cache.jdk;


import io.qwertyowrmx.cache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimpleConcurrentLruCache<K, V> implements Cache<K, V> {
    private final ReadWriteLock readWriteLock;

    private final Map<K, V> internalMap;

    public SimpleConcurrentLruCache(final int limit, boolean fair) {
        this.internalMap = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                return internalMap.size() > limit;
            }
        };
        readWriteLock = new ReentrantReadWriteLock(fair);
    }

    @Override
    public Optional<V> get(K key) {
        readWriteLock.writeLock().lock();
        V value;
        try {
            value = internalMap.get(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return Optional.of(value);
    }

    @Override
    public boolean put(K key, V value) {
        readWriteLock.writeLock().lock();
        V previousValue;
        try {
            previousValue = internalMap.put(key, value);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return previousValue != null;
    }

    public int size() {
        readWriteLock.readLock().lock();
        int size;
        try {
            size = internalMap.size();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    @Override
    public void clear() {
        internalMap.clear();
    }

    public String toString() {
        readWriteLock.readLock().lock();
        try {
            return internalMap.toString();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

