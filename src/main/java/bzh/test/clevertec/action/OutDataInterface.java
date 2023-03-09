package bzh.test.clevertec.action;

import java.io.IOException;

/**
 * Интерфейс определяющий метод вывода строки в необходимый ресурс
 */
public interface OutDataInterface {
    public void out(String data) throws IOException;
}
