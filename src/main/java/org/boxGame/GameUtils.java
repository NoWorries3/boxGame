package org.boxGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameUtils {
    private static final Random random = new Random();
    private static final Item[] commonItems = {
            new Item("Old Coin", "Common", 1.00),
            new Item("Worn-Out Gloves", "Common", 1.50),
            new Item("Basic Compass", "Common", 2.00),
            new Item("Wooden Figurine", "Common", 2.50),
            new Item("Rusty Key", "Common", 3.00)
    };

    private static final Item[] uncommonItems = {
            new Item("Silver Locket", "Uncommon", 12.00),
            new Item("Engraved Pen", "Uncommon", 15.00),
            new Item("Vintage Watch", "Uncommon", 18.00),
            new Item("Decorative Scarf", "Uncommon", 20.00),
            new Item("Leather Bound Journal", "Uncommon", 22.00)
    };

    private static final Item[] rareItems = {
            new Item("Emerald Brooch", "Rare", 30.00),
            new Item("Antique Telescope", "Rare", 35.00),
            new Item("Golden Cufflinks", "Rare", 40.00),
            new Item("Ornate Dagger", "Rare", 45.00),
            new Item("Handcrafted Mask", "Rare", 50.00)
    };

    private static final Item[] legendaryItems = {
            new Item("Ancient Tome", "Legendary", 120.00),
            new Item("Mystic Amulet", "Legendary", 150.00),
            new Item("Dragonbone Necklace", "Legendary", 175.00),
            new Item("Royal Crown Replica", "Legendary", 190.00),
            new Item("Lost Painting of the Ages", "Legendary", 200.00)
    };

    public static Box generateBox(String boxType) {
        List<Item> items = new ArrayList<>();
        int itemCount = (boxType.equals("Extreme-Risk Box")) ? 3 : 2; // 3 for Extreme, 2 otherwise

        for (int i = 0; i < itemCount; i++) {
            items.add(pickItemForBox(boxType));
        }

        double boxPrice = calculateBoxPrice(boxType);
        return new Box(boxType, boxPrice, items);
    }

    private static Item getRandomItem(Item[] itemPool) {
        return itemPool[random.nextInt(itemPool.length)];
    }


    private static Item pickItemForBox(String boxType) {
        double chance = random.nextDouble();
        switch (boxType) {
            case "Low-Risk Box":
                if (chance < 0.70) return getRandomItem(commonItems);
                else if (chance < 0.99) return getRandomItem(uncommonItems);
                else return getRandomItem(rareItems);

            case "Mid-Risk Box":
                if (chance < 0.40) return getRandomItem(commonItems);
                else if (chance < 0.90) return getRandomItem(uncommonItems);
                else if (chance < 0.99) return getRandomItem(rareItems);
                else return getRandomItem(legendaryItems);

            case "High-Risk Box":
                if (chance < 0.20) return getRandomItem(commonItems);
                else if (chance < 0.50) return getRandomItem(uncommonItems);
                else if (chance < 0.90) return getRandomItem(rareItems);
                else return getRandomItem(legendaryItems);

            case "Extreme-Risk Box":
                if (chance < 0.20) return getRandomItem(uncommonItems);
                else if (chance < 0.50) return getRandomItem(rareItems);
                else return getRandomItem(legendaryItems);

            default:
                return getRandomItem(commonItems);
        }
    }


    private static double calculateBoxPrice(String boxType) {
// Assign prices for each box type
        switch (boxType) {
            case "Low-Risk Box":
                return 10.0; // Example price
            case "Mid-Risk Box":
                return 20.0;
            case "High-Risk Box":
                return 50.0;
            case "Extreme-Risk Box":
                return 100.0;
            default:
                return 5.0; // Default price
        }
    }
}
