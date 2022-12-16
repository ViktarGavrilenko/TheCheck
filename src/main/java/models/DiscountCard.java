package models;

public class DiscountCard {
    private String number;
    private byte percent;

    public DiscountCard(String number) {
        this.number = number;
    }

    public DiscountCard() {
        this.percent = 0;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte getPercent() {
        return percent;
    }

    public void setPercent(byte percent) {
        this.percent = percent;
    }
}