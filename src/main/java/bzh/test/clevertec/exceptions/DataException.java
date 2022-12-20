package bzh.test.clevertec.exceptions;

public class DataException extends Exception{
    public DataException(String message) {
        super(message);
    }
    public DataException(String message, Exception ex) {
        super(message, ex);
    }
}
