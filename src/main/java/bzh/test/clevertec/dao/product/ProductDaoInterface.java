package bzh.test.clevertec.dao.product;

import bzh.test.clevertec.enities.Product;

import java.util.Optional;

public interface ProductDaoInterface {
    public Optional<Product> getById(long id);

    public Product create(Product product);

    public void update(Product product);

    public void deleteById(long id);
}

