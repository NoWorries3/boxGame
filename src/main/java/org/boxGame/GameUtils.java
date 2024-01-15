package org.boxGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameUtils {
    private static final Random random = new Random();

    // Method to generate a box with random items
    public static Box generateRandomBox(String boxName, double boxPrice) {
        List<Item> randomItems = new ArrayList<>();
        // Example logic: generate a number of items based on the box type
        int itemCount = boxName.equals("Basic Box") ? 2 : 3; // Basic Box has 2 items, others have 3
        for (int i = 0; i < itemCount; i++) {
            randomItems.add(generateRandomItem());
        }
        return new Box(boxName, boxPrice, randomItems);
    }

    private static Item generateRandomItem() {
        // Implement logic to generate an item with a name and rarity
        String[] rarities = {"Common", "Uncommon", "Rare", "Legendary"};
        double[] basePrices = {5.0, 10.0, 20.0, 50.0}; // Example base prices for each rarity

        int rarityIndex = random.nextInt(rarities.length);
        String rarity = rarities[rarityIndex];
        double price = basePrices[rarityIndex];

        String itemName = "Item " + (random.nextInt(100) + 1) + " - " + rarity;
        return new Item(itemName, rarity, price);
    }



}