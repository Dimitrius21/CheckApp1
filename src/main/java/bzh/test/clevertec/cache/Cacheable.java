package bzh.test.clevertec.cache;

/**
 * Интрерфейс определяющий основые методы для кэширования
 * @param <K> - ключ для доступа к данным
 * @param <V> - тип кэшируемых объектов
 */
public interface Cacheable<K,V> {
    /**
     * Метод помещает данные в кэш
     * @param key ключ
     * @param value кэшируемые данные
     * @return если в кэше содержались данные соответствующие ключу то они возвращаются, иначе null
     */
    public V put(K key, V value);
    /**
     * Метод возвращает данные из кэша по заданному ключу
     * @param key - ключ
     * @return  значение в кэше по данному ключу
     */
    public V get(Object key);

    public V remove(Object key);
}
