# :coffee: LRU Caches module

### Cache interface:

```java
public interface Cache<K, V> {
    boolean set(K key, V value);

    Optional<V> get(K key);

    int size();

    boolean isEmpty();

    void clear();
}
```

Implementations:

* SimpleLruCache.java
* SimpleConcurrentLruCache.java
