import io.qwertyowrmx.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Slf4j
public class LruCacheTestCase {

    @ParameterizedTest
    @ArgumentsSource(SimpleCachesArgumentSource.class)
    public void testLruCacheSizeRemainsTheSame(Cache<String, String> cache) {
        cache.put("test1", "value1");
        cache.put("test2", "value2");
        cache.put("test3", "value3");
        cache.put("test4", "value4");
        cache.put("test5", "value5");
        cache.put("test6", "value6");
        cache.put("test7", "value7");
        LOG.info("LRU Cache content: {}", cache);
        Assertions.assertFalse(cache.isEmpty());
        Assertions.assertEquals(5, cache.size());
    }
}
