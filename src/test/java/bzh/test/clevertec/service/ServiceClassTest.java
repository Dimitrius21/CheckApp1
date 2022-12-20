package bzh.test.clevertec.service;

import bzh.test.clevertec.enities.Product;
import bzh.test.clevertec.exceptions.DataException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ServiceClassTest {

    @Test
    public void getProductNoteById() throws DataException {
        String[] note = new String[]{"1","5"};
        Product product = new Product(1, "Bread", 115, 0);
        Map.Entry<Product, Integer> exp = Map.entry(product, 5);
        Map.Entry<Product, Integer> res = new ServiceClass().getProductNoteById(note);
        Assert.assertEquals(exp, res);
    }

    @Test(expected = DataException.class)
    public void getProductNoteByIdWithError() throws DataException {
        String[] note = new String[]{"a","5"};
        Product product = new Product(1, "Bread", 115, 0);
        Map.Entry<Product, Integer> exp = Map.entry(product, 5);
        Map.Entry<Product, Integer> res = new ServiceClass().getProductNoteById(note);
        Assert.assertEquals(exp, res);
    }

    @Test
    public void getCheck() {
    }
}