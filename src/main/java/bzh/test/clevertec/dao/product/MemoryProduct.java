package bzh.test.clevertec.dao.product;

import bzh.test.clevertec.dao.card.MemoryCard;
import bzh.test.clevertec.enities.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class MemoryProduct implements ProductDaoInterface {
    private static final Logger logger = LoggerFactory.getLogger(MemoryProduct.class);
    Map<Integer, String[]> products;
    public MemoryProduct() {
        products = Map.ofEntries(Map.entry(1, new String[]{"Bread", "115", "0"}),
                Map.entry(2, new String[]{"Butter", "185", "0"}),
                Map.entry(3, new String[]{"Milk", "208", "0"}),
                Map.entry(4, new String[]{"Ice cream", "150", "1"}),
                Map.entry(5, new String[]{"Chocolate", "210", "1"}),
                Map.entry(6, new String[]{"Yogurt", "85", "1"})
        );
    }

    @Override
    public Optional<Product> getProductById(int id){
        String[] product = products.get(id);
        if (product==null) {
            logger.error("Product with id {} hasn't been found", id);
            return Optional.empty();
        }else
            return Optional.of(new Product(id, product[0], Integer.parseInt(product[1]), Integer.parseInt(product[2])));
    }
}
