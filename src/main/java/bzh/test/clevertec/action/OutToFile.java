package bzh.test.clevertec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutToFile implements OutDataInterface {
    private static final Logger logger = LoggerFactory.getLogger(OutToFile.class);
    private String fileName;

    public OutToFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void out(String data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(data);
            bw.flush();
        }catch (IOException ex){
            logger.error("Can't write data to {} file", fileName);
            throw new IOException("File writing error");
        }
    }
}
