package bzh.test.clevertec.action;

import bzh.test.clevertec.enities.Check;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * Абстрактный класс реализующий вывод объекта Check в указанный ресурс
 */

abstract public class AbstractPrinter {
    private static NumberFormat numberFormat = NumberFormat.getInstance();
    protected Check check;
    private OutDataInterface out;

    /**
     * Конструктор объекта
     * @param check - выводимый объект класса Check
     * @param out - ресурс осуществляющий вывод
     */
    public AbstractPrinter(Check check, OutDataInterface out) {
        this.check = check;
        this.out = out;
    }

    public void setOutSource(OutDataInterface out) {
        this.out = out;
    }

    /**
     * Метод формирующий выводимый формат представления Check
     * @return - строка в требуемом формате вывода
     */
    abstract public String getOutputString();

    /**
     * Метод осуществляющий вывод предсавления Check в указанный ресурс
     * @throws IOException - в случае ошибки в ресурсе вывода
     */
    public void printString() throws IOException {
        out.out(getOutputString());
    }

    /**
     * Метод формирования строки денежноого представления значения в соответствии с Local компьютера в формате
     * с разделителем
     * @param val - целочисленное значение в минимальной денежной единице
     * @return строка в формате с разделителем
     */
    public static String convertMoneyToString(long val) {
        return numberFormat.format(val / 100.00);
    }
}
