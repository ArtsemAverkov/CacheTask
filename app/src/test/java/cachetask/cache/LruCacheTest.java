package cachetask.cache;

import cachetask.aop.cache.LruCache;
import cachetask.entity.User;
import cachetask.extension.discount.ValidParameterResolverUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;




import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ValidParameterResolverUser.class)
public class LruCacheTest {

    @Test
    void testPutAndGet(User user) {
        LruCache<Object, Object> objectObjectLfuCache = new LruCache<>(1);
        objectObjectLfuCache.put(user.getId(),user);
        Object o = objectObjectLfuCache.get(user.getId());
        assertEquals(user, o);
    }

    @Test
    void testRemove(User user) {
        LruCache<Object, Object> objectObjectLfuCache = new LruCache<>(1);
        objectObjectLfuCache.put(user.getId(),user);
        objectObjectLfuCache.remove(user.getId());
        Object o = objectObjectLfuCache.get(user.getId());
        assertNull(o);
    }

    @Test
    void testPutValueShouldNotBeAvailableByKeySizeIsGreaterThanCapacity(User user) {
        LruCache<Object, Object> objectObjectLfuCache = new LruCache<>(1);
        objectObjectLfuCache.put(user.getId(),user);
        objectObjectLfuCache.put(2,user);
        Object o = objectObjectLfuCache.get(user.getId());
        assertNull(o);
        Object object = objectObjectLfuCache.get(2);
        assertEquals(user, object);
    }

}



