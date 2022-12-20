package bzh.test.clevertec.service;

import bzh.test.clevertec.utils.Helpers;
import bzh.test.clevertec.exceptions.DataException;
import org.junit.Assert;
import org.junit.Test;

public class HelpersTest {

    @Test(expected = DataException.class)
    public void testInputDataWithoutArgs() throws DataException {
        String[] args = new String[] {};
        Helpers.testInputData(args);
    }

    @Test
    public void testInputDataWithArgs() throws DataException {
        String[] args = new String[] {"1-5"};
        String[] res = Helpers.testInputData(args);
        Assert.assertArrayEquals(args, res);
    }

    @Test(expected = DataException.class)
    public void testReadDataFromFileWithIncorrectName() throws DataException {
        Helpers.readDataFromFile("");
    }

}