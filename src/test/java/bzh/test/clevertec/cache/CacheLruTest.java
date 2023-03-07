package bzh.test.clevertec.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CacheLruTest {

    @Test
    void put() {
        CacheLru<String> cache = new CacheLru<>(2);
        cache.put(1L, "1");
        String res = cache.get(1L);
        Assertions.assertEquals("1", res);
    }

    @Test
    void get() {
        CacheLru<String> cache = new CacheLru<>(2);
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        String res = cache.get(3L);
        Assertions.assertEquals("3", res);
    }
    @Test
    void getEmpty() {
        CacheLru<String> cache = new CacheLru<>(3);
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.get(1L);
        cache.get(1L);
        cache.put(4L, "4");
        String res = cache.get(2L);
        Assertions.assertNull(res);
    }

    @Test
    void delete() {
        CacheLru<String> cache = new CacheLru<>(2);
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.remove(1L);
        String res = cache.get(1L);
        Assertions.assertNull(res);
    }

}