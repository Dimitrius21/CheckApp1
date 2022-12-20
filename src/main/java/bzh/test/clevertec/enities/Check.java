package bzh.test.clevertec.enities;

import bzh.test.clevertec.CheckRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Check {
    private String name = "CASH RECEIPT";
    private String shop = "SHOP";
    private LocalDateTime timeCreating;
    private List<Item> items = new ArrayList<>();
    private long checkNumber;

    public Check() {
        timeCreating = LocalDateTime.now();
        shop = CheckRunner.getAppProperty("check.address");
    }

    public String getName() {
        return name;
    }

    public String getShop() {
        return shop;
    }

    public LocalDateTime getTimeCreating() {
        return timeCreating;
    }

    public List<Item> getItems() {
        return items;
    }

    public long getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(long checkNumber) {
        this.checkNumber = checkNumber;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
