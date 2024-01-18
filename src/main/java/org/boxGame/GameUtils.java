package org.boxGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameUtils {
    private static final Random random = new Random();
    private static Item previousSelectedItem = null;
    private static final Item[] commonItems = {
            new Item("Iron Nail", "Common", 0.30),
            new Item("Glass Marbles", "Common", 0.50),
            new Item("Clay Pot", "Common", 0.75),
            new Item("Old Coin", "Common", 1.00),
            new Item("Brass Ring", "Common", 1.25),
            new Item("Worn-Out Gloves", "Common", 1.50),
            new Item("Basic Compass", "Common", 2.00),
            new Item("Wooden Figurine", "Common", 2.50),
            new Item("Rusty Key", "Common", 3.00),
    };

    private static final Item[] uncommonItems = {
            new Item("Silver Locket", "Uncommon", 12.00),
            new Item("Engraved Pen", "Uncommon", 15.00),
            new Item("Vintage Watch", "Uncommon", 18.00),
            new Item("Decorative Scarf", "Uncommon", 20.00),
            new Item("Leather Bound Journal", "Uncommon", 22.00),
            new Item("Bronze Statuette", "Uncommon", 23.00),
            new Item("Silk Handkerchief", "Uncommon", 17.00),
            new Item("Ivory Comb", "Uncommon", 25.00),
            new Item("Pearl Necklace", "Uncommon", 30.00),
            new Item("Obsidian Knife", "Uncommon", 28.00),
            new Item("Silver Mirror", "Uncommon", 35.00),
            new Item("Gold Plated Cup", "Uncommon", 33.00),
            new Item("Embroidered Cape", "Uncommon", 24.00),
            new Item("Jade Figurine", "Uncommon", 27.00),
            new Item("Crystal Vial", "Uncommon", 22.00)
    };

    private static final Item[] rareItems = {
            new Item("Emerald Brooch", "Rare", 30.00),
            new Item("Antique Telescope", "Rare", 35.00),
            new Item("Golden Cufflinks", "Rare", 40.00),
            new Item("Ornate Dagger", "Rare", 45.00),
            new Item("Handcrafted Mask", "Rare", 50.00),
            new Item("Ancient Vase", "Rare", 55.00),
            new Item("Diamond Brooch", "Rare", 60.00),
            new Item("Ruby Studded Pendant", "Rare", 65.00),
            new Item("Sapphire Ring", "Rare", 70.00),
            new Item("Emerald Earrings", "Rare", 75.00),
            new Item("Golden Chalice", "Rare", 80.00),
            new Item("Platinum Bracelet", "Rare", 85.00),
            new Item("Silk Tapestry", "Rare", 90.00),
            new Item("Opal Cufflinks", "Rare", 95.00),
            new Item("Pearl-Studded Tiara", "Rare", 100.00)
    };

    private static final Item[] legendaryItems = {
            new Item("Ancient Tome", "Legendary", 120.00),
            new Item("Mystic Amulet", "Legendary", 150.00),
            new Item("Dragonbone Necklace", "Legendary", 175.00),
            new Item("Royal Crown Replica", "Legendary", 190.00),
            new Item("Lost Painting of the Ages", "Legendary", 200.00),
            new Item("Crown of Kings", "Legendary", 250.00),
            new Item("Sword of Destiny", "Legendary", 300.00),
            new Item("Orb of Power", "Legendary", 350.00),
            new Item("Scroll of Ancient Wisdom", "Legendary", 400.00),
            new Item("Phoenix Feather", "Legendary", 450.00),
            new Item("Dragon Scale Armor", "Legendary", 500.00),
            new Item("Wizard's Staff", "Legendary", 550.00),
            new Item("Crystal Ball", "Legendary", 600.00),
            new Item("Ring of Eternity", "Legendary", 650.00),
            new Item("Amulet of the Ages", "Legendary", 700.00)
    };

    private static final double BRONZE_BOX_PRICE = 31.15;
    private static final double SILVER_BOX_PRICE = 81.93;
    private static final double GOLD_BOX_PRICE = 183.23;
    private static final double PLATINUM_BOX_PRICE = 740.63;

    private static final double[] BRONZE_BOX_PROBABILITIES = {0.996, 0.998, 0.999, 1.000}; // Common, Uncommon, Rare, Legendary
    private static final double[] SILVER_BOX_PROBABILITIES = {0.995, 0.997, 0.999, 1.000};
    private static final double[] GOLD_BOX_PROBABILITIES = {0.993, 0.996, 0.998, 1.000};
    private static final double[] PLATINUM_BOX_PROBABILITIES = {0.990, 0.995, 1.000};


    public static Box generateBox(String boxType) {
        List<Item> items = new ArrayList<>();
        int itemCount = (boxType.equals("Platinum Box")) ? 3 : 2; // 3 for Platinum, 2 otherwise

        for (int i = 0; i < itemCount; i++) {
            items.add(pickItemForBox(boxType));
        }

        double boxPrice = calculateBoxPrice(boxType);
        return new Box(boxType, boxPrice, items); // Include the items in the Box constructor
    }

    private static Item getRandomItem(Item[] itemPool) {
        return itemPool[random.nextInt(itemPool.length)];
    }

    public static List<Item> generateItemsForBox(String boxType) {
        List<Item> items = new ArrayList<>();
        int itemCount = (boxType.equals("Platinum Box")) ? 3 : 2; // 3 for Platinum, 2 otherwise

        for (int i = 0; i < itemCount; i++) {
            items.add(pickItemForBox(boxType));
        }

        return items;
    }

    private static Item pickItemForBox(String boxType) {
        double chance = random.nextDouble();
        switch (boxType) {
            case "Bronze Box":
                return pickItemWithProbabilities(chance, BRONZE_BOX_PROBABILITIES);
            case "Silver Box":
                return pickItemWithProbabilities(chance, SILVER_BOX_PROBABILITIES);
            case "Gold Box":
                return pickItemWithProbabilities(chance, GOLD_BOX_PROBABILITIES);
            case "Platinum Box":
                return pickItemWithProbabilities(chance, PLATINUM_BOX_PROBABILITIES);
            default:
                return getRandomItem(commonItems);
        }
    }

    // Helper method to pick items based on probabilities
    private static Item pickItemWithProbabilities(double chance, double[] probabilities) {
        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (chance <= cumulativeProbability) {
                switch (i) {
                    case 0:
                        return getRandomItem(commonItems);
                    case 1:
                        return getRandomItem(uncommonItems);
                    case 2:
                        return getRandomItem(rareItems);
                    case 3:
                        return getRandomItem(legendaryItems);
                }
            }
        }
        // Default to common item if probabilities don't match
        return getRandomItem(commonItems);
    }

    // Updated method to calculate box price
    private static double calculateBoxPrice(String boxType) {
        switch (boxType) {
            case "Bronze Box":
                return BRONZE_BOX_PRICE;
            case "Silver Box":
                return SILVER_BOX_PRICE;
            case "Gold Box":
                return GOLD_BOX_PRICE;
            case "Platinum Box":
                return PLATINUM_BOX_PRICE;
            default:
                return 5.0;
        }
    }
}