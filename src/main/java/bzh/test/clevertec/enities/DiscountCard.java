package bzh.test.clevertec.enities;

import java.util.Objects;

public class DiscountCard {
    private int number;
    private int discount;

    public DiscountCard() {
    }

    public DiscountCard(int number, int discount) {
        this.number = number;
        this.discount = discount;
    }

    public int getNumber() {
        return number;
    }

    public int getDiscount() {
        return discount;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard card = (DiscountCard) o;
        return number == card.number && discount == card.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, discount);
    }
}
