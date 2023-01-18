import io.qwertyowrmx.cache.jdk.SimpleConcurrentLruCache;
import io.qwertyowrmx.cache.jdk.SimpleLruCache;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SimpleCachesArgumentSource implements ArgumentsProvider {

    public static final int MAX_CACHE_SIZE = 5;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                arguments(new SimpleLruCache<String, String>(MAX_CACHE_SIZE)),
                arguments(new SimpleConcurrentLruCache<String, String>(MAX_CACHE_SIZE, true))
        );
    }
}
