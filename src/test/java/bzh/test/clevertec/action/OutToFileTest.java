package bzh.test.clevertec.action;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;



public class OutToFileTest {

    @Test
    public void out() throws IOException {
        String fileName = "testWrite.data";
        File f = new File(fileName);
        new OutToFile(fileName).out("test");
        Assertions.assertTrue(f.exists());
        f.deleteOnExit();
    }
}