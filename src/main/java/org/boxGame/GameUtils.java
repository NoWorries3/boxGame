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
            new Item("Rusty Key", "Common", 3.00),
            new Item("Clay Pot", "Common", 0.75),
            new Item("Brass Ring", "Common", 1.25),
            new Item("Glass Marbles", "Common", 0.50),
            new Item("Iron Nail", "Common", 0.30),
            new Item("Cotton Scarf", "Common", 1.80),
            new Item("Leather Strap", "Common", 1.20),
            new Item("Wool Socks", "Common", 1.00),
            new Item("Copper Coin", "Common", 0.60),
            new Item("Ceramic Bowl", "Common", 2.00),
            new Item("Wooden Spoon", "Common", 0.90)
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

    public static Box generateBox(String boxType) {
        List<Item> items = new ArrayList<>();
        int itemCount = (boxType.equals("Platinum Box")) ? 3 : 2; // 3 for Platinum, 2 otherwise

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
            case "Bronze Box":
                // Adjusted probabilities for Bronze Box
                if (chance < 0.70) return getRandomItem(commonItems);
                else if (chance < 0.95) return getRandomItem(uncommonItems);
                else if (chance < 0.99) return getRandomItem(rareItems);
                else return getRandomItem(legendaryItems);

            case "Silver Box":
                // Adjusted probabilities for Silver Box
                if (chance < 0.50) return getRandomItem(commonItems);
                else if (chance < 0.80) return getRandomItem(uncommonItems);
                else if (chance < 0.95) return getRandomItem(rareItems);
                else return getRandomItem(legendaryItems);

            case "Gold Box":
                // Adjusted probabilities for Gold Box
                if (chance < 0.30) return getRandomItem(commonItems);
                else if (chance < 0.60) return getRandomItem(uncommonItems);
                else if (chance < 0.85) return getRandomItem(rareItems);
                else return getRandomItem(legendaryItems);

            case "Platinum Box":
                // Adjusted probabilities for Platinum Box
                if (chance < 0.20) return getRandomItem(uncommonItems);
                else if (chance < 0.50) return getRandomItem(rareItems);
                else return getRandomItem(legendaryItems);

            default:
                return getRandomItem(commonItems);
        }
    }

    private static double calculateBoxPrice(String boxType) {
        // Adjusted box pricing
        switch (boxType) {
            case "Bronze Box":
                return 31.15;
            case "Silver Box":
                return 81.93;
            case "Gold Box":
                return 183.23;
            case "Platinum Box":
                return 740.63;
            default:
                return 5.0;
        }
    }
}





/*
EV CALCULATION


# Calculating the Expected Value (EV) for each Box Type
# Average values for each item category
avg_value_common = 1.50
avg_value_uncommon = 25.00
avg_value_rare = 65.00
avg_value_legendary = 425.00

# Probabilities for each box type
probabilities = {
    "Bronze Box": {"Common": 0.70, "Uncommon": 0.25, "Rare": 0.04, "Legendary": 0.01},
    "Silver Box": {"Common": 0.50, "Uncommon": 0.30, "Rare": 0.15, "Legendary": 0.05},
    "Gold Box": {"Common": 0.30, "Uncommon": 0.30, "Rare": 0.25, "Legendary": 0.15},
    "Platinum Box": {"Common": 0, "Uncommon": 0.20, "Rare": 0.30, "Legendary": 0.50}
}

# Calculating EV for each box type
ev = {}
for box, probs in probabilities.items():
    ev[box] = (probs["Common"] * avg_value_common +
               probs["Uncommon"] * avg_value_uncommon +
               probs["Rare"] * avg_value_rare +
               probs["Legendary"] * avg_value_legendary)

    # Multiplying by 2 for Bronze, Silver, Gold and by 3 for Platinum boxes
    ev[box] *= 3 if box == "Platinum Box" else 2

ev







 */