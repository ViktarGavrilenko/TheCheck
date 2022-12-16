package models;

public class Order {
    private Item item;
    private Long quantity;

    public Order() {
    }

    public Order(Item item, Long quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getQuantity() {
        return quantity;
    }
}