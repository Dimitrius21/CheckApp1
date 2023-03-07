package bzh.test.clevertec.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLru<V> extends LinkedHashMap<Long, V> implements Cacheable<Long,V>{
    private final int maxCapacity;

    public CacheLru(int maxCapacity) {
        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }



    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > maxCapacity;
    }

}

