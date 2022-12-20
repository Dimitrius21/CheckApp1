package bzh.test.clevertec.dao.card;

import bzh.test.clevertec.enities.DiscountCard;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryCardTest {

    @Test
    public void getDiscountCard() {
        DiscountCard card = new DiscountCard(1234, 5);
        DiscountCard gotCard = new MemoryCard().getDiscountCard(1234).get();
        Assert.assertEquals(card, gotCard);
    }
}