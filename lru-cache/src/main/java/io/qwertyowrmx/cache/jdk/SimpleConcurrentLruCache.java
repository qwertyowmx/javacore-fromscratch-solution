/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowrmx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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

