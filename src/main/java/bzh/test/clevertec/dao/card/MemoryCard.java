package bzh.test.clevertec.dao.card;

import bzh.test.clevertec.cache.CachedEntity;
import bzh.test.clevertec.enities.DiscountCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 *  Класс эмулирующий хранилище в оперативной памяти для сущности DiscountCard
 */
@CachedEntity
public class MemoryCard implements CardDaoInterface {
    private static final Logger logger = LoggerFactory.getLogger(MemoryCard.class);
    private Map<Long, Integer> discountCards;
    private long number;

    public MemoryCard() {
        discountCards = new HashMap<>();
        discountCards.putAll(
                Map.ofEntries(entry(1234L, 5),
                        entry(1235L, 7),
                        entry(1236L, 5),
                        entry(1237L, 3)));
    }
    /**
     * Метод получения объекта DiscountCard по его номеру
     * @param number - номер DiscountCard для поиска записи
     * @return - Объект Optional с найденным значением или empty
     */
    @Override
    public Optional<DiscountCard> getById(long number) {
        Integer discount = discountCards.get(number);
        if (discount == null) {
            logger.info("Discount cad with number {} hasn't been found", number);
            return Optional.empty();
        }
        return Optional.of(new DiscountCard((int) number, discount));
    }
    /**
     * Метод сохранения объекта DiscountCard в хранимлище
     * @param card - сохраняемый объект DiscountCard
     * @return - сохраненный объект с присвоенным номером
     */
    @Override
    public DiscountCard create(DiscountCard card) {
        int discount = card.getDiscount();
        number++;
        discountCards.put(number, discount);
        card.setNumber(number);
        return card;
    }
    /**
     * Метод обновления объекта DiscountCard в хранимлище
     * @param card - обновляемый объект DiscountCard
     */
    @Override
    public void update(DiscountCard card) {
        int discount = card.getDiscount();
        discountCards.put(card.getNumber(), discount);
    }
    /**
     * Метод удаления объекта DiscountCard в хранимлище
     * @param id - номер удаляемого объекта DiscountCard
     */
    @Override
    public void deleteById(long id) {
        discountCards.remove(id);
    }
}
