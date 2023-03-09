package bzh.test.clevertec.utils;

import bzh.test.clevertec.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Вспомогательный класс для проверки наличия входных параметров и чтения их из файла, в случае указания во входных
 * параметрах соответствующего ключа
 */
public class Helpers {
    private static String KEY = "-f";
    private static final Logger logger = LoggerFactory.getLogger(Helpers.class);

    /**
     * Метод проверяет наличие входных данных во входной строке запуска программы или в файле
     * @param args - входные параметры при запуске программы
     * @return - набор параметров для формирования Check
     * @throws DataException - если входные данные с набором параметров отсутствуют
     */
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

    /**
     * Метод производит чтение входных данных из файла
     * @param filename - файл где граняться входные данные
     * @return - входные данные порчитанные из файла
     * @throws DataException - в случае ошибки чтения файла или отсуттвия данных в файле
     */

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
