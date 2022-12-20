package bzh.test.clevertec.utils;

import bzh.test.clevertec.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Helpers {
    private static String KEY = "-f";
    private static final Logger logger = LoggerFactory.getLogger(Helpers.class);

    public static String[] testInputData(String[] args) throws DataException {
        if (args.length == 0) {
            logger.error("Data is absent");
            throw new DataException("Data is absent");
        }
        if (KEY.equals(args[0].trim())) {
            String fileName = args[1];
            return readDataFromFile(fileName);
        } else return args;
    }

    public static String[] readDataFromFile(String filename) throws DataException {
        try (BufferedReader rd = new BufferedReader(new FileReader(filename))) {
            String data = rd.readLine().trim();
            if (data.isBlank()) {
                logger.error("Data is absent at file {}", filename);
                throw new DataException("Data is absent at indicated file");
            } else {
                String[] args = data.split("\\s+");
                return args;
            }
        } catch (IOException ex) {
            logger.error("Error of reading {} file", filename);
            throw new DataException("Error of file reading");
        }
    }
}
