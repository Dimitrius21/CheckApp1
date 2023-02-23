package bzh.test.clevertec.service;

import bzh.test.clevertec.builders.ProductBuilder;
import bzh.test.clevertec.dao.card.MemoryCard;
import bzh.test.clevertec.dao.product.MemoryProduct;
import bzh.test.clevertec.enities.DiscountCard;
import bzh.test.clevertec.enities.Item;
import bzh.test.clevertec.enities.ItemWitDiscountCard;
import bzh.test.clevertec.enities.Product;
import bzh.test.clevertec.exceptions.DataException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ServiceClassTest {
    @Mock
    MemoryProduct memoryProduct;
    @Mock
    MemoryCard memoryCard;
    @InjectMocks
    ServiceClass service;

    @Test
    public void getProductNoteByIdWithInvalidData() {
        String[] note = {"1", "5b"};
        assertThatThrownBy(()-> service.getProductNoteById(note)).isInstanceOf(DataException.class);
    }
    @Test
    public void getProductNoteById() throws DataException {
        String[] note = new String[]{"1", "5"};
        Product product = ProductBuilder.aProduct().id(1).name("Bread").price(115).discountType(0).build();
        Map.Entry<Product, Integer> exp = Map.entry(product, 5);
        doReturn(Optional.of(product)).when(memoryProduct).getProductById(1);
        Map.Entry<Product, Integer> res = service.getProductNoteById(note);
        verify(memoryProduct).getProductById(1);
        assertThat(res).isEqualTo(exp);
    }

    @Test
    public void convert() throws DataException {
        String[] args = new String[]{"card-1234"};
        DiscountCard card = new DiscountCard(1234, 5);
        doReturn(Optional.of(card)).when(memoryCard).getDiscountCard(1234);

        ArgumentCaptor<Integer>  captor = ArgumentCaptor.forClass(Integer.class);
        service.convert(args);
        verify(memoryCard).getDiscountCard(captor.capture());
        assertThat(captor.getValue()).isEqualTo(1234);
    }


    @Test
    public void getCheck() throws DataException {
        String[] args = new String[]{"1-5", "card-1234"};
        Product product = ProductBuilder.aProduct().id(1).name("Bread").price(115).discountType(0).build();
        Map.Entry<Product, Integer> exp = Map.entry(product, 5);
        doReturn(Optional.of(product)).when(memoryProduct).getProductById(1);
        DiscountCard card = new DiscountCard(1234, 5);
        doReturn(Optional.of(card)).when(memoryCard).getDiscountCard(1234);

        List<Item> items = service.getCheck(args).getItems();
        assertThat(items).hasSize(1);
        assertThat(items.get(0)).isInstanceOf(ItemWitDiscountCard.class);
    }


}