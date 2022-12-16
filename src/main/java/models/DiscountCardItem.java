package models;

import static utils.ArithmeticUtils.getPriceWithDiscount;

public class DiscountCardItem implements Item{
    private final Item item;
    private final int discountPercentage;

    public DiscountCardItem(Item item, int discountPercentage) {
        this.item = item;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public int getId() {
        return item.getId();
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public Double getPrice() {
        return getPriceWithDiscount(this.item.getPrice(), discountPercentage);
    }
}