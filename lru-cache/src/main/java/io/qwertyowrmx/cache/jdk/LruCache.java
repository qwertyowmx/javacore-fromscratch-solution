package io.qwertyowrmx.cache.jdk;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxEntries;

    private LruCache(final int maxEntries) {
        super(maxEntries + 1, 1.0f, true);
        this.maxEntries = maxEntries;
    }

    public static <K, V> LruCache<K, V> createCache(int maxEntries) {
        return new LruCache<K, V>(maxEntries);
    }

    public static <K, V> LruCache<K, V> createSynchronizedCache(int maxEntries) {
        return (LruCache<K, V>) Collections.synchronizedMap(new LruCache<K, V>(maxEntries));
    }

    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
        return super.size() > maxEntries;
    }
}

