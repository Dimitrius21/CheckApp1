package bzh.test.clevertec.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  * Класс реализует кэш, работающий по алгоритму LRU, в качестве ключа выступает целое значение типа long
 *  Используются стандартные методы интерфейса Map.
 * @param <V> -  тип кэшируемых объектов
 */
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

