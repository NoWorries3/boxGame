package org.boxGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameUtils {
    private static final Random random = new Random();
    private static Item previousSelectedItem = null;
    private static final Item[] commonItems = {
            new Item("Old Coin", "Common", 1.00),
            new Item("Worn-Out Gloves", "Common", 1.50),
            new Item("Basic Compass", "Common", 2.00),
            new Item("Wooden Figurine", "Common", 2.50),
            new Item("Rusty Key", "Common", 3.00),
            new Item("Worn-Out Gloves", "Common", 1.50),
            new Item("Basic Compass", "Common", 2.00),
            new Item("Wooden Figurine", "Common", 2.50),
            new Item("Ceramic Mug", "Common", 4.00),
            new Item("Cotton Scarf", "Common", 5.50),
            new Item("Flint Stone", "Common", 6.00),
            new Item("Braided Belt", "Common", 6.50),
            new Item("Terracotta Vase", "Common", 7.00),
            new Item("Copper Earrings", "Common", 8.00),
            new Item("Stone Pendant", "Common", 9.00),
            new Item("Linen Handkerchief ", "Common", 10.00),
            new Item("Iron Bracelet", "Common", 15.00),
            new Item("Carved Wooden Box", "Common", 18.00),
            new Item("Glass Beads", "Common", 20.00),
            new Item("Small Tapestry", "Common", 22.00),
            new Item("Serdar's Joint", "Common", 25.00),
            new Item("Embroidered Pouch", "Common", 28.00),
            new Item("Handheld Mirror", "Common", 32.00)
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

    // Updated probabilities to increase the chance of getting higher-value items
    private static final double[] BRONZE_BOX_PROBABILITIES = {0.40, 0.75, 0.85, 0.95}; // Increased chances for better items
    private static final double[] SILVER_BOX_PROBABILITIES = {0.60, 0.75, 0.87, 0.97};
    private static final double[] GOLD_BOX_PROBABILITIES = {0.65, 0.80, 0.90, 0.98};
    private static final double[] PLATINUM_BOX_PROBABILITIES = {0.70, 0.83, 0.92, 0.99};



    private static int lowerValueItemCount = 0; // Counts consecutive lower-value item picks
    private static final int LOWER_VALUE_THRESHOLD = 3; // Threshold after which chances for better items increase
    private static final int HISTORY_SIZE = 5; // Size of the recent items history
    private static final LinkedList<Item> recentItemHistory = new LinkedList<>();

    public static Box generateBox(String boxType) {
        List<Item> items = new ArrayList<>();
        int itemCount = boxType.equals("Platinum Box") ? 3 : 2;

        for (int i = 0; i < itemCount; i++) {
            items.add(pickItemForBox(boxType));
        }

        return new Box(boxType, calculateBoxPrice(boxType), items);
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

    private static double averageValue(Item[] items) {
        double totalValue = 0;
        for (Item item : items) {
            totalValue += item.getBasePrice();
        }
        return totalValue / items.length;
    }
    private static double calculateEV(double[] probabilities, double[] prices, double bronzeBoxPrice) {
        double ev = 0;
        for (int i = 0; i < probabilities.length; i++) {
            ev += probabilities[i] * prices[i];
        }
        return ev - bronzeBoxPrice;
    }

    private static void calculateAndAdjustEV() {
        double evBronzeBox = calculateEV(BRONZE_BOX_PROBABILITIES, new double[]{
                averageValue(commonItems),
                averageValue(uncommonItems),
                averageValue(rareItems),
                averageValue(legendaryItems)
        }, BRONZE_BOX_PRICE);

        // Repeat the same for Silver, Gold, and Platinum boxes
        // ...

        // Check and adjust the EV values
        // ...
    }


    private static Item pickItemForBox(String boxType) {
        double chance = random.nextDouble();
        if (lowerValueItemCount >= LOWER_VALUE_THRESHOLD) {
            chance *= 0.5; // Increase chance for better items more significantly
        }

        Item selectedItem = pickItemWithAdjustedProbability(chance, boxType);

        if (selectedItem.getBasePrice() < 15) {
            lowerValueItemCount++;
        } else {
            lowerValueItemCount = 0;
        }

        updateRecentItemHistory(selectedItem);
        return selectedItem;
    }



    private static Item pickItemWithAdjustedProbability(double chance, String boxType) {
        Item selectedItem;
        int attempt = 0;
        do {
            selectedItem = pickItemWithProbabilities(chance, getProbabilitiesForBoxType(boxType));
            attempt++;
        } while (recentItemHistory.contains(selectedItem) && attempt < 5);

        return selectedItem;
    }

    private static void updateRecentItemHistory(Item item) {
        if (recentItemHistory.size() >= HISTORY_SIZE) {
            recentItemHistory.poll();
        }
        recentItemHistory.offer(item);
    }

        private static double[] getProbabilitiesForBoxType(String boxType) {
            switch (boxType) {
                case "Bronze Box": return BRONZE_BOX_PROBABILITIES;
                case "Silver Box": return SILVER_BOX_PROBABILITIES;
                case "Gold Box": return GOLD_BOX_PROBABILITIES;
                case "Platinum Box": return PLATINUM_BOX_PROBABILITIES;
                default: return new double[] {1.0}; // Default to common
            }
        }

    static void adjustBoxProbabilities() {
        // Calculate the EV for each box and adjust probabilities accordingly
        adjustEVForBoxType("Bronze Box", BRONZE_BOX_PRICE, BRONZE_BOX_PROBABILITIES);
        adjustEVForBoxType("Silver Box", SILVER_BOX_PRICE, SILVER_BOX_PROBABILITIES);
        adjustEVForBoxType("Gold Box", GOLD_BOX_PRICE, GOLD_BOX_PROBABILITIES);
        adjustEVForBoxType("Platinum Box", PLATINUM_BOX_PRICE, PLATINUM_BOX_PROBABILITIES);
    }

    private static void adjustEVForBoxType(String boxType, double boxPrice, double[] probabilities) {
        double currentEV = calculateEV(probabilities, new double[]{
                averageValue(commonItems),
                averageValue(uncommonItems),
                averageValue(rareItems),
                averageValue(legendaryItems)
        }, boxPrice);

        // Adjust the probabilities to meet the EV criteria
        while (currentEV < 1.3 * boxPrice) { // Ensure at least 20% return
            // Increase the probability of higher-value items slightly
            for (int i = 1; i < probabilities.length; i++) {
                probabilities[i] += 0.02; // Increment by 2%
            }

            // Recalculate the EV
            currentEV = calculateEV(probabilities, new double[]{
                    averageValue(commonItems),
                    averageValue(uncommonItems),
                    averageValue(rareItems),
                    averageValue(legendaryItems)
            }, boxPrice);
        }
    }


    // Helper method to pick items based on probabilities
    private static Item pickItemWithProbabilities(double chance, double[] probabilities) {
        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (chance <= cumulativeProbability) {
                switch (i) {
                    case 0: return getRandomItem(commonItems);
                    case 1: return getRandomItem(uncommonItems);
                    case 2: return getRandomItem(rareItems);
                    case 3: return getRandomItem(legendaryItems);
                }
            }
        }
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