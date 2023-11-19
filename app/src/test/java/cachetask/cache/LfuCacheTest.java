package cachetask.cache;

import cachetask.aop.cache.LfuCache;
import cachetask.entity.User;
import cachetask.extension.discount.ValidParameterResolverUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(ValidParameterResolverUser.class)
public class LfuCacheTest {

    @Test
    void testPutAndGet(User user) {
        LfuCache<Object, Object> objectObjectLfuCache = new LfuCache<>(1);
        objectObjectLfuCache.put(user.getId(),user);
        Object o = objectObjectLfuCache.get(user.getId());
        assertEquals(user, o);
    }

    @Test
    void testRemove(User user) {
        LfuCache<Object, Object> objectObjectLfuCache = new LfuCache<>(1);
        objectObjectLfuCache.put(user.getId(),user);
        objectObjectLfuCache.remove(user.getId());
        Object o = objectObjectLfuCache.get(user.getId());
        assertNull(o);
    }

    @Test
    void testPutValueShouldNotBeAvailableByKeySizeIsGreaterThanCapacity(User user) {
        LfuCache<Object, Object> objectObjectLfuCache = new LfuCache<>(1);
        objectObjectLfuCache.put(user.getId(),user);
        objectObjectLfuCache.put(2,user);
        Object o = objectObjectLfuCache.get(user.getId());
        assertNull(o);
        Object object = objectObjectLfuCache.get(2);
        assertEquals(user, object);
    }



}
