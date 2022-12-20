package bzh.test.clevertec.dao.card;

import bzh.test.clevertec.enities.DiscountCard;

import java.util.Optional;

public interface CardDaoInterface {
    public Optional<DiscountCard> getDiscountCard(int number);
}
