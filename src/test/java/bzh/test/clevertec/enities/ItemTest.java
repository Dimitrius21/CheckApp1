package bzh.test.clevertec.enities;


import bzh.test.clevertec.builders.ProductBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class ItemTest {
    static int id;
    static String name;
    static int price;
    static List<Map.Entry<Item, Integer[]>> dataList = new ArrayList<>();

    @BeforeAll
    public static void Init() {
        int testQuantity = 3;
        int[] quantity = {8, 9, 7};
        int[] discountType = {0, 1, 0};
        int[] cardNum = {-1, 1234, 1234};
        int[] discountVal = {0, 5, 5};
        int[] amount = {1480, 1665, 1295};
        int[] discountResult = {0, 167, 65};
        id = 2;
        name = "Butter";
        price = 185;
        for (int i = 0; i < testQuantity; i++) {
            Product product = ProductBuilder.aProduct().id(id).name(name).price(price).discountType(discountType[i]).build();            
            DiscountCard card = cardNum[i] > 0 ? new DiscountCard(cardNum[i], discountVal[i]) : null;
            Integer[] results = {amount[i], discountResult[i], quantity[i]};
            dataList.add(Map.entry(new Item.ItemBuilder(product).setQuantity(quantity[i]).addDiscountByCard(card).build(),
                    results));
        }
    }

    @ParameterizedTest
    @MethodSource("getValidationAmount")
    public void checkAmount(Item item, int expectedAmount) {
        assertThat(item.getAmount()).isEqualTo(expectedAmount);
    }

    @ParameterizedTest
    @MethodSource("getValidationDiscount")
    public void checkDiscount(Item item, int expectedDiscount) {
        assertThat(item.getDiscount()).isEqualTo(expectedDiscount);
    }


    @ParameterizedTest
    @MethodSource("getValidationQuantity")
    public void checkQuantity(Item item, int qtity) {
        assertThat(item.getQuantity()).isEqualTo(qtity);
    }

    @Test
    public void getProductName() {
        String resName = dataList.stream().findFirst().get().getKey().getProductName();
        assertThat(resName).isEqualTo(name);
    }

    @Test
    public void getPrice() {
        int resPrice = dataList.stream().findFirst().get().getKey().getPrice();
        assertThat(resPrice).isEqualTo(price);
    }

    static Stream<Arguments> getValidationAmount() {
        return dataList.stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()[0])).toList().stream();
    }

    static Stream<Arguments> getValidationDiscount() {
        return dataList.stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()[1])).toList().stream();
    }

    static Stream<Arguments> getValidationQuantity() {
        return dataList.stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()[2])).toList().stream();
    }
}
