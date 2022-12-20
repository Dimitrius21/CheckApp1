package bzh.test.clevertec.service;

import bzh.test.clevertec.dao.card.CardDaoInterface;
import bzh.test.clevertec.dao.card.MemoryCard;
import bzh.test.clevertec.dao.product.MemoryProduct;
import bzh.test.clevertec.dao.product.ProductDaoInterface;
import bzh.test.clevertec.enities.Check;
import bzh.test.clevertec.enities.DiscountCard;
import bzh.test.clevertec.enities.Item;
import bzh.test.clevertec.enities.Product;
import bzh.test.clevertec.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ServiceClass {
    private static final Logger logger = LoggerFactory.getLogger(ServiceClass.class);
    private static final String CARD_INDICATION = "card";
    private final ProductDaoInterface productDao = new MemoryProduct();
    private final CardDaoInterface cardDao = new MemoryCard();
    private final List<Map.Entry<Product, Integer>> products = new ArrayList<>();
    private Optional<DiscountCard> gotCard = Optional.empty();

    public Check getCheck(String[] notes) throws DataException {
        convert(notes);
        Check check = new Check();
        DiscountCard card = gotCard.orElse(null);
        products.stream().forEach(note -> {
            Item item = new Item.ItemBuilder(note.getKey())
                    .setQuantity(note.getValue())
                    .addDiscountByCard(card)
                    .build();
            check.addItem(item);
        });
        return check;
    }

    public void convert(String[] args) throws DataException {
        for (String st : args) {
            String[] field = st.split("-");
            if (CARD_INDICATION.equals(field[0])) {
                int cardNumber;
                try {
                    cardNumber = Integer.parseInt(field[1]);
                } catch (NumberFormatException ex) {
                    throw new DataException("Illegal input data");
                }
                gotCard = cardDao.getDiscountCard(cardNumber);
            } else {
                products.add(getProductNoteById(field));
            }
        }
    }

    public Map.Entry<Product, Integer> getProductNoteById(String[] note) throws DataException {
        try {
            int id = Integer.parseInt(note[0]);
            int quantity = Integer.parseInt(note[1]);
            Optional<Product> product = productDao.getProductById(id);
            return Map.entry(product.orElseThrow(), quantity);
        } catch (NumberFormatException ex) {
            logger.error("Illegal input data {}", Arrays.toString(note));
            throw new DataException("Illegal input data");
        } catch (NoSuchElementException ex) {
            logger.error("Product is absent");
            throw new DataException("Product with indicated ID is absent");
        }

    }
}
