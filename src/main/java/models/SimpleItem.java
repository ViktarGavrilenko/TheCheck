package models;

public class SimpleItem implements Item {
    private int id;
    private String name;
    private Double price;

    public SimpleItem(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPrice() {
        return price;
    }
}