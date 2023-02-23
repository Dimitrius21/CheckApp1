package bzh.test.clevertec.action;

import bzh.test.clevertec.builders.CheckBuilder;
import bzh.test.clevertec.builders.ProductBuilder;
import bzh.test.clevertec.enities.Check;
import bzh.test.clevertec.enities.Item;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PrintCheckTest {

    @Test
    void getOutputStringWithoutDiscount() {
        Item item = new Item.ItemBuilder(ProductBuilder.aProduct().name("Milk").build()).setQuantity(1).build();
        Check check = CheckBuilder.aCheck().items(item).build();
        OutDataInterface out = new OutToConsole();
        PrintCheck printCheck = new PrintCheck(check, out);
        String res = printCheck.getOutputString();
        assertThat(res).contains(item.getProductName(), Integer.toString(item.getQuantity()),
                AbstractPrinter.convertMoneyToString(item.getPrice()), AbstractPrinter.convertMoneyToString(item.getAmount()),
                "Total amount for payment");
    }

    @Test
    void getOutputStringWithDiscount() {
        Item item = new Item.ItemBuilder(ProductBuilder.aProduct().name("Milk").price(200).discountType(1).build())
                .setQuantity(8).build();
        Check check = CheckBuilder.aCheck().items(item).build();
        OutDataInterface out = new OutToConsole();
        PrintCheck printCheck = new PrintCheck(check, out);
        String res = printCheck.getOutputString();
        assertThat(res).contains(item.getProductName(), Integer.toString(item.getQuantity()),
                AbstractPrinter.convertMoneyToString(item.getPrice()), AbstractPrinter.convertMoneyToString(item.getAmount()),
                "Discount", AbstractPrinter.convertMoneyToString(item.getDiscount()), "Total amount for payment", "Total discount");
    }
}