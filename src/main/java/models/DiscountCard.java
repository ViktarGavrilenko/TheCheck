package models;

public class DiscountCard {
    private String number;
    private byte percent;

    public DiscountCard() {
        this.percent = 0;
    }

    public String getNumber() {
        return number;
    }

    public byte getPercent() {
        return percent;
    }
}