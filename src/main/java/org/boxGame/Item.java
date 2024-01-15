package org.boxGame;

public class Item {

    private String name;
    private String rarity;
    private double basePrice;

    public Item(String name, String rarity, double basePrice) {
        this.name = name;
        this.rarity = rarity;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public String getRarity() {
        return rarity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return name + " ( "  + rarity + ")";
    }
}
