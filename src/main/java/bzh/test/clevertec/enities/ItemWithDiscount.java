package bzh.test.clevertec.enities;

public abstract class ItemWithDiscount extends Item {
    Item item;


    public ItemWithDiscount(Item item) {
        super();
        this.item = item;
    }

    public String getProductName() {
        return item.getProductName();
    }

    public int getPrice() {
        return item.getPrice();
    }

    public int getQuantity() {
        return item.getQuantity();
    }

    @Override
    public int getAmount() {
        return item.getAmount();
    }

    abstract public int getDiscount();
}
