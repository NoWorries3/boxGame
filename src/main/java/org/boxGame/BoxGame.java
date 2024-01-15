package org.boxGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoxGame {

    private String playerName; // Store player's name
    private double balance; // Store player's balance
    private ArrayList<String> inventory;
    private ArrayList<Box> availableBoxes; // List of available boxes
    private ArrayList<Box> purchasedBoxes; // To store purchased but unopened boxes

    // Constructor
    public BoxGame() {
        inventory = new ArrayList<>(); // Initialize inventory as an empty list
        balance = 0; // Set initial balance to 0
        availableBoxes = new ArrayList<>(); // Initialize availableBoxes as an empty list
        purchasedBoxes = new ArrayList<>();

        // Initialize available boxes for demo purpose
        availableBoxes.add(new Box("Basic Box", 10, new ArrayList<>(List.of("Book", "Pen"))));
        availableBoxes.add(new Box("Advanced Box", 20, new ArrayList<>(List.of("Watch", "Sunglasses"))));
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

    // Method to display player information
    public void displayPlayerInfo() {
        System.out.println("Player: " + playerName);
        System.out.println("Balance: $" + balance);
        System.out.println("Inventory: " + inventory);
    }

    // Updated Start game method with inventory management option
    public void startGame() {
        Scanner scanner = new Scanner(System.in); // Scanner for input
        System.out.println("Enter your name: ");
        playerName = scanner.nextLine(); // Read player's name from input

        boolean isPlaying = true;
        while (isPlaying) {
            System.out.println("1. Deposit Money");
            System.out.println("2. Buy Box");
            System.out.println("3. Display Info");
            System.out.println("4. Manage Inventory");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline
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
                    isPlaying = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
        scanner.close(); // Close the scanner at the end of the game
    }

    // Main method - Entry point of the application
    public static void main(String[] args) {
        BoxGame game = new BoxGame();
        game.startGame();
    }
}