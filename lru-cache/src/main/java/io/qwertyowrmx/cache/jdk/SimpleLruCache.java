package io.qwertyowrmx.cache.jdk;

import io.qwertyowrmx.cache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleLruCache<K, V> implements Cache<K, V> {
    private final Map<K, V> map;

    public SimpleLruCache(final int limit) {
        this.map = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                return super.size() > limit;
            }
        };
    }

    @Override
    public boolean put(K key, V value) {
        return map.put(key, value) != null;
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.of(map.get(key));
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    public String toString() {
        return map.toString();
    }
}
