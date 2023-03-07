package bzh.test.clevertec.cache;

public interface Cacheable<K,V> {
    public V put(K key, V value);
    public V get(Object key);
    public V remove(Object key);
}
