package bzh.test.clevertec.cache;

import java.util.*;

/**
 * Класс реализует кэш, работающий по алгоритму LFU, в качестве ключа выступает целое значение типа long
 * @param <V> - тип кэшируемых объектов
 */
public class CacheLfu<V> implements Cacheable<Long, V>{
    private Map<Long, Node> store = new HashMap<>();
    private List<Node> nodeList;
    private final int maxSize;
    private int qNode;

    private static class Node<V> {
        private long frequency;
        private long key;
        private V value;

        public Node(long key, V value) {
            frequency = 1;
            this.value = value;
            this.key = key;
        }
        public long getKey() {
            return key;
        }
        public long getFrequency() {
            return frequency;
        }
        public V getValue() {
            return value;
        }
        public void incrementFrequency() {
            frequency++;
        }
        public void setValue(V value) {
            this.value = value;
        }
    }

    public CacheLfu(int maxSize) {
        this.maxSize = maxSize;
        nodeList = new ArrayList<>(maxSize);
        qNode = 0;
    }

    /**
     * Метод помещает данные в кэш
     * @param id ключ
     * @param value кэшируемые данные
     * @return если в кэше содержались данные соответствующие ключу то они возвращаются, иначе null
     */
    public V put(Long id, V value) {
        Node<V> node;
        if (store.containsKey(id)) {
            node = store.get(id);
            node.incrementFrequency();
            V val = node.getValue();
            node.setValue(value);
            return val;
        } else {
            node = new Node(id, value);
            store.put(id, node);
            if (qNode < maxSize) {
                qNode++;
                nodeList.add(node);
            } else {
                int i = minFreqNode();
                store.remove(nodeList.get(i).getKey());
                nodeList.set(i, node);
            }
        }
        return null;
    }

    /**
     * Метод возвращает данные из кэша по заданному ключу
     * @param key - ключ
     * @return  значение в кэше по данному ключу
     */
    public V get(Object key) {
        if (store.containsKey(key)) {
            Node<V> node = store.get(key);
            node.incrementFrequency();
            return node.getValue();
        }
        return null;
    }

    /**
     * Удаляет данные из кэша до ключу
     * @param key - ключ поиска
     * @return - если данные соответствующие данному ключу отсутствуют возвращается null, иначе удаленное значение
     */
    public V remove(Object key) {
        if (store.containsKey(key)) {
            Iterator<Node> iterator = nodeList.iterator();
            while (iterator.hasNext()) {
                if ((Long)iterator.next().getKey() == key) {
                    iterator.remove();
                    break;
                }
            }
            qNode--;
            Node<V> value = store.remove(key);
            return value.getValue();
        }
        return null;
    }

    private int minFreqNode() {
        int index = 0;
        long minFrequency = nodeList.get(0).getFrequency();
        for (int i = 0; i < nodeList.size(); i++) {
            long fr = nodeList.get(i).getFrequency();
            if (minFrequency > fr) {
                minFrequency = fr;
                index = i;
            }
        }
        return index;
    }
}
