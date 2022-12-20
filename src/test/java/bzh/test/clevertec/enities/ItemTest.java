package bzh.test.clevertec.enities;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ItemTest {
    int id = 2;
    String name = "Butter";
    int price = 185;
    int quantity = 8;
    int discountType;
    int cardNum;
    int discountVal;
    int amount;
    int discountResult;
    Item item;

    public ItemTest(int quantity, int discountType, int cardNum, int discountVal, int amount, int discountResult){
        this.quantity = quantity;
        this.discountType = discountType;
        this.cardNum = cardNum;
        this.discountVal = discountVal;
        this.amount = amount;
        this.discountResult = discountResult;
        Product product = new Product(id, name, price, discountType);
        DiscountCard card = cardNum>0 ? new DiscountCard(cardNum, discountVal) : null;
        item = new Item.ItemBuilder(product).setQuantity(quantity).addDiscountByCard(card).build();
    }

    @Parameterized.Parameters
    public static Collection numbers(){
        return Arrays.asList(new Object[][]{
            {8, 0, -1, 0, 1480, 0}, {9, 1, 1234, 5, 1665, 167}, {7, 0, 1234, 5, 1295, 65}
        });
    }

    @Test
    public void getAmount() {
        int exp = amount;
        int res = item.getAmount();
        Assert.assertEquals(exp, res);
    }

    @Test
    public void getDiscount() {
        int exp = discountResult;
        int res = item.getDiscount();
        Assert.assertEquals(exp, res);
    }

    @Test
    public void getProductName() {
        String exp = name;
        String res = item.getProductName();
        Assert.assertEquals(exp, res);
    }
    @Test
    public void getQuantity() {
        int exp = quantity;
        int res = item.getQuantity();
        Assert.assertEquals(exp, res);
    }

    @Test
    public void getPrice() {
        int exp = price;
        int res = item.getPrice();
        Assert.assertEquals(exp, res);
    }
}