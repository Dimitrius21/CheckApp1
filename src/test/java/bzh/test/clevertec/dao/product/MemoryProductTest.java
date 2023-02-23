package bzh.test.clevertec.dao.product;

import bzh.test.clevertec.builders.ProductBuilder;
import bzh.test.clevertec.enities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MemoryProductTest {

    @Test
    public void getProductById() {
        Product product = ProductBuilder.aProduct().id(2).name("Butter").price(185).build();
        Product gotProduct = new MemoryProduct().getProductById(2).get();
        Assertions.assertEquals(product, gotProduct);
    }
}