package models;

import static utils.ArithmeticUtils.getPriceWithDiscount;

public class PromotionalItem implements Item {
    private final Item item;
    private final int promotionalPercentage;

    public PromotionalItem(Item item, int promotionalPercentage) {
        this.item = item;
        this.promotionalPercentage = promotionalPercentage;
    }

    @Override
    public int getId() {
        return this.item.getId();
    }

    @Override
    public String getName() {
        return this.item.getName();
    }

    @Override
    public Double getPrice() {
        return getPriceWithDiscount(this.item.getPrice(), promotionalPercentage);
    }
}