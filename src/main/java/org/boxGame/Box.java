package org.boxGame;

import java.util.ArrayList;
import java.util.List;

public class Box {
    private String name;
    private double price;
    private List<Item> items;

    public Box(String name, double price, List<Item> items) {
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

    public List<Item> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
