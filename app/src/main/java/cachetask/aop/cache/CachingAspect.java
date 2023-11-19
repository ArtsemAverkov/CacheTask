package cachetask.aop.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
public class CachingAspect  {

    private final Map<String, CacheI<String, Object>> caches = new ConcurrentHashMap<>();

    private final CacheFactory cacheFactory;

    public CachingAspect() {
        this.cacheFactory = new CacheFactory();
    }



    @Around("@annotation(cacheable)")
    public Object cache(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        CacheI<String, Object> cache = caches.computeIfAbsent(cacheable.value(),
                k -> cacheFactory.createCache());
        switch (joinPoint.getSignature().getName()) {
            case "read":
                String cacheKey = generateCacheKey(joinPoint);
                Object cachedValue = cache.get(cacheKey);
                if (cachedValue != null) {
                    return cachedValue;
                } else {
                    Object result = joinPoint.proceed();
                    cache.put(cacheKey, result);
                    return result;
                }

            case "create":
                Object resultCreate = joinPoint.proceed();
                cache.put(resultCreate.toString(), Arrays.stream(joinPoint.getArgs()).findFirst().get());
                return resultCreate;


            case "delete":
                String cacheKey1 = generateCacheKey(joinPoint);
                Object cachedValue1 = cache.get(cacheKey1);
                if (cachedValue1 != null) {
                    cache.remove(cacheKey1);
                    Object result = joinPoint.proceed();
                    return result;
                } else {
                    Object result = joinPoint.proceed();
                    return result;
                }
            case "update":
                for (Object args : joinPoint.getArgs()) {
                    Object cachedValue2 = cache.get(String.valueOf(args));
                    if (cachedValue2 != null) {
                        Object result = joinPoint.proceed();
                        cache.update(String.valueOf(args), Arrays.stream(joinPoint.getArgs()).findFirst().get());
                        return result;
                    }
                }
                Object result = joinPoint.proceed();
                return result;
        }
        return null;
    }

    private String generateCacheKey(ProceedingJoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder(Arrays.stream(joinPoint.getArgs()).findFirst().get().toString());
        return sb.toString();
    }
}
