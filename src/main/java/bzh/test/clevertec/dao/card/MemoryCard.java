package bzh.test.clevertec.dao.card;

import bzh.test.clevertec.enities.DiscountCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import static java.util.Map.entry;

public class MemoryCard implements CardDaoInterface {
    private static final Logger logger = LoggerFactory.getLogger(MemoryCard.class);
    private Map<Integer, Integer> discountCards;

    public MemoryCard() {
        discountCards = Map.ofEntries(
                entry(1234, 5),
                entry(1235, 7),
                entry(1236, 5),
                entry(4321, 3));
    }

    @Override
    public Optional<DiscountCard> getDiscountCard(int number){
        Integer discount = discountCards.get(number);
        if (discount==null)  {
            logger.info("Discount cad with number {} hasn't been found", number);
            return Optional.empty();
        }
        return Optional.of(new DiscountCard(number, discount));
    }
}
