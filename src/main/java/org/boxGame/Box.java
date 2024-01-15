package org.boxGame;

import java.util.ArrayList;

public class Box {
    private String name;
    private double price;
    private ArrayList<String> items;

    public Box(String name, double price, ArrayList<String> items) {
        this.name = name;
        this.price = price;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getItems() {
        return new ArrayList<>(items); // Return a copy to avoid direct modification
    }

}
