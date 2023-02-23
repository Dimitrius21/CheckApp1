package bzh.test.clevertec.dao.card;

import bzh.test.clevertec.enities.DiscountCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class MemoryCardTest {

    @Test
    public void getDiscountCard() {
        DiscountCard card = new DiscountCard(1234, 5);
        DiscountCard gotCard = new MemoryCard().getDiscountCard(1234).get();
        Assertions.assertEquals(card, gotCard);
    }
}