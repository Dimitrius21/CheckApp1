package bzh.test.clevertec.builders;

import bzh.test.clevertec.enities.Product;

import java.util.function.Consumer;

public class ProductBuilder implements TestBuilder<Product>{
    private long id=0;
    private String name="";
    private int price = 0;
    private int discountType = 0;

    public static ProductBuilder aProduct(){
        return new ProductBuilder();
    }
    private ProductBuilder(){}

    private ProductBuilder(ProductBuilder productBuilder){
        this.id = productBuilder.id;
        this.name = productBuilder.name;
        this.price = productBuilder.price;
        this.discountType = productBuilder.discountType;
    }

    public ProductBuilder id (long id){
        return copyWith(pb-> pb.id = id);
    }

    public ProductBuilder name (String name){
        return copyWith(pb-> pb.name = name);
    }

    public ProductBuilder price (int price){
        return copyWith(pb-> pb.price = price);
    }

    public ProductBuilder discountType(int type){
        return copyWith(pb-> pb.discountType = type);
    }


    private ProductBuilder copyWith(Consumer<ProductBuilder> consumer){
        ProductBuilder pb = new ProductBuilder(this);
        consumer.accept(pb);
        return pb;
    }

    @Override
    public Product build() {
        Product product = new Product(id, name, price, discountType);
        return product;
    }
}
