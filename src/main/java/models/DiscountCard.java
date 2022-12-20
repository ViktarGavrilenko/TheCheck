package models;

import java.util.Objects;

public class DiscountCard {
    private String number;
    private byte percent;

    public DiscountCard(String number, byte percent) {
        this.number = number;
        this.percent = percent;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return percent == that.percent && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, percent);
    }
}