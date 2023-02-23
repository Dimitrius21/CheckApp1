package bzh.test.clevertec.utils;

import bzh.test.clevertec.utils.Helpers;
import bzh.test.clevertec.exceptions.DataException;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;



public class HelpersTest {

    @Test
    public void testInputDataWithoutArgs() {
        String[] args = new String[] {};
        assertThatThrownBy(()->Helpers.testInputData(args)).isInstanceOf(DataException.class);
    }

    @Test
    public void testInputDataWithArgs() throws DataException {
        String[] args = new String[] {"1-5", "2-6"};
        String[] res = Helpers.testInputData(args);
        assertThat(res).isEqualTo(args);
    }

    @Test
    public void testReadDataFromFileWithIncorrectName() {
        assertThatThrownBy(()->Helpers.readDataFromFile("")).isInstanceOf(DataException.class).hasMessage("Error of file reading");
    }

    @Test
    public void testReadDataFromFileWithSuccess() throws DataException, IOException {
        String fileName = "testedData.txt";
        File f = new File(fileName);
        BufferedWriter br = new BufferedWriter(new FileWriter(f));
        String[] data = {"1-5"};
        br.write(data[0]);
        br.flush();
        br.close();
        String[] res = Helpers.readDataFromFile(fileName);
        assertThat(res).isEqualTo(data);
        f.delete();
    }



}