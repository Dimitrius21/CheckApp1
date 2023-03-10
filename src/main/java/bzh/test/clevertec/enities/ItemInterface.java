package bzh.test.clevertec.enities;

/**
 * Итерфейс определяющий Позицию(товар) в чеке
 */
public interface ItemInterface {
    public String getProductName();
    public int getPrice();
    public int getQuantity();
    public int getAmount();
    public int getDiscount();

}
