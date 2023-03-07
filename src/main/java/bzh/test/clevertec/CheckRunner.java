package bzh.test.clevertec;

import bzh.test.clevertec.action.AbstractPrinter;
import bzh.test.clevertec.action.OutToConsole;
import bzh.test.clevertec.action.OutToFile;
import bzh.test.clevertec.action.PrintCheck;
import bzh.test.clevertec.cache.CacheHandler;
import bzh.test.clevertec.dao.card.CardDaoInterface;
import bzh.test.clevertec.dao.card.MemoryCard;
import bzh.test.clevertec.dao.product.MemoryProduct;
import bzh.test.clevertec.dao.product.ProductDaoInterface;
import bzh.test.clevertec.enities.Check;
import bzh.test.clevertec.exceptions.DataException;
import bzh.test.clevertec.service.ServiceClass;
import bzh.test.clevertec.utils.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CheckRunner {
    private static final Logger logger = LoggerFactory.getLogger(CheckRunner.class);
    public static final String MAIN_PROPERTIES_FILE = "application.properties";
    public static final String FILE_NAME_TO_WRITE = "check.txt";
    private static Properties prop = new Properties();
    private static boolean isPropertiesLoaded = false;

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream(File.separator + MAIN_PROPERTIES_FILE);
        try {
            if (is != null) {
                prop.load(is);
                isPropertiesLoaded = true;
            } else throw new IOException();
        } catch (IOException e) {
            logger.error("Can't read init property file");
            isPropertiesLoaded = false;
        }
    }

    public static void main(String[] args) {
        try {
            if (isPropertiesLoaded) {
                String[] data = Helpers.testInputData(args);
                ProductDaoInterface productDao = new MemoryProduct();
                productDao = (ProductDaoInterface) CacheHandler.checkCaching(productDao, ProductDaoInterface.class);
                CardDaoInterface cardDao = new MemoryCard();
                cardDao = (CardDaoInterface) CacheHandler.checkCaching(cardDao, CardDaoInterface.class);
                Check check = new ServiceClass(productDao, cardDao).getCheck(data);
                AbstractPrinter printCheck = new PrintCheck(check, new OutToConsole());
                printCheck.printString();

                printCheck.setOutSource(new OutToFile(FILE_NAME_TO_WRITE));
                printCheck.printString();
            }else {
                throw new DataException("Init property file hasn't loaded");
            }
        } catch (Exception ex) {
            logger.error("Application has terminated with error - {}", ex.getMessage());
        }
    }

    public static String getAppProperty(String property) {
        return prop.getProperty(property);
    }


}

