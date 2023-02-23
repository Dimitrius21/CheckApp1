package bzh.test.clevertec.action;

import bzh.test.clevertec.enities.Check;

import java.io.IOException;
import java.text.NumberFormat;



abstract public class AbstractPrinter {
    private static NumberFormat numberFormat = NumberFormat.getInstance();
    protected Check check;
    private OutDataInterface out;

    public AbstractPrinter(Check check, OutDataInterface out) {
        this.check = check;
        this.out = out;
    }

    public void setOutSource(OutDataInterface out) {
        this.out = out;
    }

    abstract public String getOutputString();

    public void printString() throws IOException {
        out.out(getOutputString());
    }

    public static String convertMoneyToString(long val) {
        return numberFormat.format(val / 100.00);
    }
}
