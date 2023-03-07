package bzh.test.clevertec.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CacheFabricTest {

    @Test
    void getCacheInstance() {
        Cacheable cache = CacheFabric.getCacheInstance();
        Assertions.assertInstanceOf(CacheLfu.class, cache);
    }
}