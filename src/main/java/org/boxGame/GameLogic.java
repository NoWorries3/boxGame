package org.boxGame;

import java.util.ArrayList;
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

        // Initialize available boxes with items
        List<Item> basicBoxItems = List.of(new Item("Book", "Common", 5.0), new Item("Pen", "Common", 2.0));
        List<Item> advancedBoxItems = List.of(new Item("Watch", "Uncommon", 15.0), new Item("Sunglasses", "Uncommon", 10.0));
        availableBoxes.add(GameUtils.generateRandomBox("Basic Box", 10));
        availableBoxes.add(GameUtils.generateRandomBox("Advanced Box", 20));
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name:");
        playerName = scanner.nextLine();

        boolean isPlaying = true;
        while (isPlaying) {
            displayMenuOptions();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        depositMoney(scanner);
                        break;
                    case 2:
                        buyBox(scanner);
                        break;
                    case 3:
                        displayPlayerInfo();
                        break;
                    case 4:
                        manageInventory(scanner);
                        break;
                    case 5:
                        sellItem(scanner);
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
        System.out.println("\n1. Deposit Money");
        System.out.println("2. Buy Box");
        System.out.println("3. Display Info");
        System.out.println("4. Manage Inventory");
        System.out.println("5. Sell an Item");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    // Method to display player information
    public void displayPlayerInfo() {
        System.out.println("Player: " + playerName);
        System.out.println("Balance: $" + balance);
        System.out.println("Inventory: " + inventory);
    }

    // Method for player to deposit money
    public void depositMoney(Scanner scanner){
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        balance += amount;
        System.out.println("$" + amount + " added to your balance");
    }

    // Method for buying a box
    public void buyBox(Scanner scanner) {
        System.out.println("Available Boxes:");
        for (int i = 0; i < availableBoxes.size(); i++) {
            Box box = availableBoxes.get(i);
            System.out.println((i + 1) + ". " + box.getName() + " - $" + box.getPrice());
        }
        System.out.print("Enter the number of the box to buy: ");
        int boxIndex = scanner.nextInt() -1;
        System.out.print("Enter the quantity of boxes to buy: ");
        int quantity = scanner.nextInt();

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


    // New method to handle inventory interaction
    public void manageInventory(Scanner scanner) {
        System.out.println("Inventory Management");
        System.out.println("1. View Purchased Boxes");
        System.out.println("2. Open a Box");
        System.out.println("3. Go Back");
        System.out.println("Choose an Option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                viewPurchasedBoxes();
                break;
            case 2:
                openBox(scanner);
                break;
            case 3:
                // Just break to go back to the main menu
                break;
            default:
                System.out.println("Invalid option, please try again.");
                break;
        }
    }

    // Method to view purchased but unopened boxes
    private void viewPurchasedBoxes() {
        if (purchasedBoxes.isEmpty()) {
            System.out.println("No Purchased Boxes.");
            return;
        }

        System.out.println("Purchased Boxes:");
        for (int i = 0; i< purchasedBoxes.size(); i++) {
            Box box = purchasedBoxes.get(i);
            System.out.println((i + 1) + ". " + box.getName());
        }
    }

    // Method to open a purchased box
    private void openBox(Scanner scanner) {
        System.out.println("Enter the number of the box to open: ");
        int boxIndex = scanner.nextInt() - 1;

        if (boxIndex >= 0 && boxIndex < purchasedBoxes.size()) {
            Box boxToOpen = purchasedBoxes.remove(boxIndex);
            inventory.addAll(boxToOpen.getItems());
            System.out.println("You opened a " + boxToOpen.getName() + " and found: " + boxToOpen.getItems());
        } else {
            System.out.println("Invalid box selection.");
        }
    }

    // Helper method to determine item sale price
    private double getItemSalePrice(Item item) {
        // Implement logic based on item's rarity and base price
        switch (item.getRarity()) {
            case "Common":
                return item.getBasePrice(); // or some calculation based on base price
            case "Uncommon":
                return item.getBasePrice() * 2.3; // example modifier
            case "Rare":
                return item.getBasePrice() * 4; // example modifier
            case "Legendary":
                return item.getBasePrice() * 13; // example modifier
            default:
                return item.getBasePrice();
        }
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
        System.out.println("Your Inventory: " + inventory);
        System.out.println("Enter the name of the item to sell: ");
        scanner.nextLine(); // Consume leftover newline
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

}
