package cachetask.aop.cache;

public class CacheFactory {
    private final int maxSize;
    private final String algorithm;

    public CacheFactory() {
        this.maxSize =10;
        this.algorithm = "LRU";
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





