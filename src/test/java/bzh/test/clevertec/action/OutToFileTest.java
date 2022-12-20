package bzh.test.clevertec.action;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class OutToFileTest {

    @Test
    public void out() throws IOException {
        String fileName = "testWrite.data";
        File f = new File(fileName);
        new OutToFile(fileName).out("test");
        Assert.assertTrue(f.exists());
        f.deleteOnExit();
    }
}