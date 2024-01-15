package org.boxGame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GameLogic {
    private String playerName; // Store player's name
    private double balance; // Store player's balance
    private ArrayList<Item> inventory;
    private ArrayList<Box> availableBoxes; // List of available boxes
    private ArrayList<Box> purchasedBoxes; // To store purchased but unopened boxes

    // Constructor
    public GameLogic() {
        inventory = new ArrayList<>(); // Initialize inventory as an empty list
        balance = 0; // Set initial balance to 0
        availableBoxes = new ArrayList<>(); // Initialize availableBoxes as an empty list
        purchasedBoxes = new ArrayList<>();

        // Initialize available boxes as per the new risk-based system
        availableBoxes.add(GameUtils.generateBox("Low-Risk Box"));
        availableBoxes.add(GameUtils.generateBox("Mid-Risk Box"));
        availableBoxes.add(GameUtils.generateBox("High-Risk Box"));
        availableBoxes.add(GameUtils.generateBox("Extreme-Risk Box"));
        // Repeat or adjust as needed
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name:");
        playerName = scanner.nextLine();

        boolean isPlaying = true;
        while (isPlaying) {
            displayMenuOptions();
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        depositMoney(scanner);
                        break;
                    case 2:
                        buyBox(scanner);
                        break;
                    case 3:
                        openBoxFromMainMenu(scanner);
                        break;
                    case 4:
                        sellItem(scanner);
                        break;
                    case 5:
                        sellBackBoxes(scanner);
                        break;
                    case 6:
                        isPlaying = false;
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
            }
        }
        scanner.close(); // Close the scanner at the end of the game
    }

    // Method to display menu options
    private void displayMenuOptions() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy - HH:mm");
        String dateString = formatter.format(new Date());

        System.out.println(playerName + " | $" + balance + " | " + dateString);
        System.out.print("[");
        System.out.print(purchasedBoxes.stream().filter(b -> b.getName().contains("Low")).count() + "] Low | ");
        System.out.print(purchasedBoxes.stream().filter(b -> b.getName().contains("Mid")).count() + "] Mid | ");
        System.out.print(purchasedBoxes.stream().filter(b -> b.getName().contains("High")).count() + "] High | ");
        System.out.print(purchasedBoxes.stream().filter(b -> b.getName().contains("Extreme")).count() + "] Extreme");
        System.out.println("\n1. Deposit Money");
        System.out.println("1. Deposit Money");
        System.out.println("2. Buy Box");
        System.out.println("3. Display Info");
        System.out.println("4. Manage Inventory");
        System.out.println("5. Sell an Item");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    // Method for player to deposit money
    public void depositMoney(Scanner scanner) {
        System.out.print("Enter amount to deposit: ");
        double amount = safeNextDouble(scanner);
        balance += amount;
        System.out.println("$" + amount + " added to your balance");
        scanner.nextLine(); // Consume the leftover newline character
    }

    // Method for buying a box
    public void buyBox(Scanner scanner) {
        System.out.println("Available Boxes:");
        for (int i = 0; i < availableBoxes.size(); i++) {
            Box box = availableBoxes.get(i);
            System.out.println((i + 1) + ". " + box.getName() + " - $" + box.getPrice());
        }
        System.out.print("Enter the number of the box to buy: ");
        int boxIndex = safeNextInt(scanner) - 1;
        scanner.nextLine(); // Consume the newline character after reading an integer

        System.out.print("Enter the quantity of boxes to buy: ");
        int quantity = safeNextInt(scanner);
        scanner.nextLine(); // Consume the newline character after reading an integer

        if (boxIndex >= 0 && boxIndex < availableBoxes.size()) {
            Box selectedBox = availableBoxes.get(boxIndex);
            double totalCost = selectedBox.getPrice() * quantity;
            if (balance >= totalCost) {
                balance -= totalCost;
                for (int i = 0; i < quantity; i++) {
                    purchasedBoxes.add(selectedBox);
                }
                System.out.println("You bought " + quantity + " " + selectedBox.getName() + "(s)!");
            } else {
                System.out.println("Insufficient balance to complete this purchase.");
            }
        } else {
            System.out.println("Invalid box selection.");
        }
    }

    // Method to view purchased but unopened boxes
    private void viewPurchasedBoxes() {
        if (purchasedBoxes.isEmpty()) {
            System.out.println("No Purchased Boxes.");
        } else {
            System.out.println("Purchased Boxes:");
            for (int i = 0; i < purchasedBoxes.size(); i++) {
                Box box = purchasedBoxes.get(i);
                System.out.println((i + 1) + ". " + box.getName() + " - Price: $" + box.getPrice());
            }
        }
    }

    private void openBoxFromMainMenu(Scanner scanner) {
        if (purchasedBoxes.isEmpty()) {
            System.out.println("You don't have any boxes to open.");
            return;
        }
        viewPurchasedBoxes();
        System.out.println("Choose a box to open:");
        int boxIndex = safeNextInt(scanner) - 1;
        scanner.nextLine(); // Consume the newline character

        if (boxIndex >= 0 && boxIndex < purchasedBoxes.size()) {
            simulateSuspensefulOpening();
            Box boxToOpen = purchasedBoxes.remove(boxIndex);
            System.out.println("You opened " + boxToOpen.getName() + " and found:");
            for (Item item : boxToOpen.getItems()) {
                displayWinAnimation(item);
                System.out.println("- " + item);
            }
            inventory.addAll(boxToOpen.getItems());
        } else {
            System.out.println("Invalid box selection.");
        }
    }

    private void simulateSuspensefulOpening() {
        try {
            System.out.print("Opening");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000); // Wait for 1 second
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Add a method to display special win animations
    private void displayWinAnimation(Item item) {
        if (item.getRarity().equals("Rare") || item.getRarity().equals("Legendary")) {
            System.out.println("Congratulations! You found a " + item.getRarity() + " item!");
            // Add more fancy text or effects as desired
        }
    }

    // Helper method to determine item sale price
    private double getItemSalePrice(Item item) {
                return item.getBasePrice();
    }

    // Helper method to find item in inventory
    private Item findItemInInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    // Method to sell an item
    public void sellItem(Scanner scanner) {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("Your Inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName());
        }
        System.out.println("Enter the name of the item to sell:");
        scanner.nextLine();  // Consume leftover newline
        String itemName = scanner.nextLine();

        Item itemToSell = findItemInInventory(itemName);
        if (itemToSell != null) {
            inventory.remove(itemToSell);
            double salePrice = getItemSalePrice(itemToSell);
            balance += salePrice;
            System.out.println(itemToSell.getName() + " sold for $" + salePrice);
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    // Implement the sellBackBoxes method
    private void sellBackBoxes(Scanner scanner) {
        if (purchasedBoxes.isEmpty()) {
            System.out.println("You don't have any boxes to sell back.");
            return;
        }
        viewPurchasedBoxes();
        System.out.print("Enter the number of the box to sell back: ");
        int boxIndex = safeNextInt(scanner) - 1;
        scanner.nextLine(); // Consume the newline character

        if (boxIndex >= 0 && boxIndex < purchasedBoxes.size()) {
            Box boxToSell = purchasedBoxes.get(boxIndex);
            double sellBackPrice = calculateSellBackPrice(boxToSell);
            balance += sellBackPrice;
            purchasedBoxes.remove(boxIndex);
            System.out.println("You sold back " + boxToSell.getName() + " for $" + sellBackPrice);
        } else {
            System.out.println("Invalid box selection.");
        }
    }

    // Calculate the sell-back price (e.g., 50% of the original price)
    private double calculateSellBackPrice(Box box) {
        double sellBackPercentage = 0.5; // Adjust this as needed (e.g., 50% sell-back price)
        return box.getPrice() * sellBackPercentage;
    }

    private int safeNextInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a number. Please enter a number:");
            scanner.next(); // to discard the non-integer input
        }
        return scanner.nextInt();
    }

    private double safeNextDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("That's not a valid amount. Please enter a number:");
            scanner.next(); // discard the non-double input
        }
        return scanner.nextDouble();
    }

}
