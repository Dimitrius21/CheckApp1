package bzh.test.clevertec.action;

import bzh.test.clevertec.enities.Check;
import bzh.test.clevertec.enities.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Класс реализующий вывод сформированного представления объекта Check в указанный ресурс
 * Наследник клааса AbstractPrinter, переопределяет метод getOutputString
 */
public class PrintCheck extends AbstractPrinter {
    private static final Logger logger = LoggerFactory.getLogger(PrintCheck.class);

    public PrintCheck(Check check, OutDataInterface out) {
        super(check, out);
    }

    /**
     * Метод формирующий структуру представления/отображения объекта Check
     * @return сформированную строку представления
     */
    @Override
    public String getOutputString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String datetime = check.getTimeCreating().format(formatter);

        String head = "\n" + check.getName() + "\n" + check.getShop() + "\n" + datetime + "\n\n";
        StringBuilder sb = new StringBuilder(head);
        long totalDiscount = 0;
        long totalAmount = 0;

        for (Item item : check.getItems()) {
            String description = item.getProductName();
            int discount = item.getDiscount();
            int qtity = item.getQuantity();
            int price = item.getPrice();
            int amount = item.getAmount();
            String data = String.format("   %s * %d = %s\n", convertMoneyToString(price), qtity, convertMoneyToString(amount));

            sb.append(description).append("\n").append(data);
            totalAmount += amount;
            if (discount > 0) {
                String discountString = String.format("Discount:  %s\n", convertMoneyToString(discount));
                totalDiscount += discount;
                sb.append(discountString);
            }
            logger.debug("Item descr:{}, price {}, qtity{}, amount {}, discount {}\n", description, price, qtity, amount, discount);
        }
        if (totalDiscount > 0) {
            sb.append(String.format("\nTotal discount: %s", convertMoneyToString(totalDiscount)));
            totalAmount -= totalDiscount;
        }
        sb.append(String.format("\nTotal amount for payment:   %s\n", convertMoneyToString(totalAmount)));

        return sb.toString();
    }
}
