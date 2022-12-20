package bzh.test.clevertec.enities;

public class ItemWitDiscountCard extends ItemWithDiscount {
    DiscountCard card;

    public ItemWitDiscountCard(Item item, DiscountCard cart) {
        super(item);
        this.card = cart;
    }

    @Override
    public int getDiscount() {
        int lastDiscount = item.getDiscount();
        if (card == null || lastDiscount>0) {
            return lastDiscount;
        }else {
            int discount= (int) Math.round(getAmount()*((card.getDiscount())/100.00));
            return discount;
        }
    }
}
