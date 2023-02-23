package bzh.test.clevertec.builders;

import bzh.test.clevertec.enities.Check;
import bzh.test.clevertec.enities.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckBuilder implements TestBuilder<Check> {
    private List<Item> itemList = new ArrayList<>();


    public static CheckBuilder aCheck(){
        return new CheckBuilder();
    }

    public CheckBuilder items(Item ... items){
        if (items.length>0){
                itemList.addAll(Arrays.asList(items));
            }
        return this;
    }

    @Override
    public Check build() {
        Check check = new Check();
        itemList.stream().forEach(item -> check.addItem(item));
        return check;
    }
}
