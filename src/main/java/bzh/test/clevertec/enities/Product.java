package bzh.test.clevertec.enities;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product  {
    private long id;
    private String name;
    private int price;
    private int discountType;

    public Product(long id, String name, int price, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountType = discount;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int type) {
        this.discountType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && discountType == product.discountType && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, discountType);
    }

    private boolean checkName(String name){
        return name.matches("\\S+");
    }
}
