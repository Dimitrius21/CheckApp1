package bzh.test.clevertec.dao.product;

import bzh.test.clevertec.enities.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MemoryProductTest {

    @Test
    public void getProductById() {
        Product product = new Product(2, "Butter", 185, 0);
        Product gotProduct = new MemoryProduct().getProductById(2).get();
        Assert.assertEquals(product, gotProduct);
    }
}