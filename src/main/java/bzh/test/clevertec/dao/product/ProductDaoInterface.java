package bzh.test.clevertec.dao.product;

import bzh.test.clevertec.enities.Product;

import java.util.Optional;

public interface ProductDaoInterface {
    public Optional<Product> getProductById(int id);
}
