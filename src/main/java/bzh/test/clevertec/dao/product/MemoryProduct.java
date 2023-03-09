package bzh.test.clevertec.dao.product;

import bzh.test.clevertec.cache.CachedEntity;
import bzh.test.clevertec.enities.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Класс эмулирующий хранилище в оперативной памяти для сущности Product
 */
@CachedEntity
public class MemoryProduct implements ProductDaoInterface {
    private static final Logger logger = LoggerFactory.getLogger(MemoryProduct.class);
    private Map<Long, String[]> products;
    private long counter=6;
    public MemoryProduct() {
        products = new HashMap<>();
                products.putAll(Map.ofEntries(Map.entry(1L, new String[]{"Bread", "115", "0"}),
                Map.entry(2L, new String[]{"Butter", "185", "0"}),
                Map.entry(3L, new String[]{"Milk", "208", "0"}),
                Map.entry(4L, new String[]{"Ice cream", "150", "1"}),
                Map.entry(5L, new String[]{"Chocolate", "210", "1"}),
                Map.entry(6L, new String[]{"Yogurt", "85", "1"})
        ));
    }

    /**
     * Метод получения объекта Product по его id
     * @param id - первичный ключ для поиска записи
     * @return - Объект Optional с найденным значением или empty
     */
    @Override
    public Optional<Product> getById(long id){
        String[] product = products.get(id);
        if (product==null) {
            logger.error("Product with id {} hasn't been found", id);
            return Optional.empty();
        }else
            return Optional.of(new Product(id, product[0], Integer.parseInt(product[1]), Integer.parseInt(product[2])));
    }

    /**
     * Метод сохранения объекта Product в хранимлище
     * @param product - сохраняемый объект Product
     * @return - сохраненный объект с присвоенным первичным ключом хранилища
     */
    @Override
    public Product create(Product product) {
        String[] note = {product.getName(), Integer.toString(product.getPrice()), Integer.toString(product.getDiscountType())};
        counter++;
        products.put(counter, note);
        product.setId(counter);
        return product;
    }

    /**
     * Метод обновления объекта Product в хранимлище
     * @param product - обновляемый объект Product
     */
    @Override
    public void update(Product product) {
        String[] note = {product.getName(), Integer.toString(product.getPrice()), Integer.toString(product.getDiscountType())};
        products.put(product.getId(), note);
    }

    /**
     * Метод удаления объекта Product в хранимлище
     * @param id - первичный ключ удаляемого объекта
     */
    @Override
    public void deleteById(long id) {
        products.remove(id);
    }
}
