package bzh.test.clevertec.action;


/**
 * Класс для вывода текста в консоль
 */
public class OutToConsole implements OutDataInterface {
    /**
     * Метод производящий вывод/отображение строки в консоле
     * @param data - строка для вывода
     */
    @Override
    public void out(String data) {
        System.out.println(data);
    }
}
