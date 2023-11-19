package cachetask.aop.cache;

import cachetask.Configuration.CacheProperties;
import cachetask.Configuration.ConfigurationLoader;

import java.util.Map;

public class CacheFactory {
    private final int maxSize;
    private final String algorithm;

    public CacheFactory() {
        ConfigurationLoader loader = new ConfigurationLoader();
        CacheProperties cacheProperties = loader
                .loadCacheProperties("/Users/artemaverkov/clevertec_task/CacheTask/app/src/main/resources/application.yml"); //TODO проблас с путем

        if (cacheProperties != null) {
            this.maxSize = cacheProperties.getMaxSize();
            this.algorithm = cacheProperties.getAlgorithm();
        } else {
            throw new RuntimeException("Failed to load cache properties from application.yml");
        }
    }

    public <K, V> CacheI<K, V> createCache() {
        switch (algorithm.toUpperCase()) {
            case "LRU":
                return new LruCache<>(maxSize);
            case "LFU":
                return new LfuCache<>(maxSize);
            default:
                throw new IllegalArgumentException("Invalid cache algorithm: " + algorithm);
        }
    }
}





