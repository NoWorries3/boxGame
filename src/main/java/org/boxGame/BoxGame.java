package org.boxGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoxGame {

    private String playerName; // Store player's name
    private double balance; // Store player's balance
    private ArrayList<String> inventory;
    private ArrayList<Box> availableBoxes; // List of available boxes

    // Constructor
    public BoxGame() {
        inventory = new ArrayList<>(); // Initialize inventory as an empty list
        balance = 0; // Set initial balance to 0
        availableBoxes = new ArrayList<>(); // Initialize availableBoxes as an empty list

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
        for (Box box : availableBoxes) {
            System.out.println(box.getName() + " - $" + box.getPrice());
        }
        System.out.print("Enter the name of the box to buy: ");
        String boxName = scanner.nextLine();

        for (Box box : availableBoxes) {
            if (box.getName().equalsIgnoreCase(boxName) && balance >= box.getPrice()) {
                balance -= box.getPrice();
                inventory.addAll(box.getItems());
                System.out.println("You bought a " + box.getName() + "!");
                return;
            }
        }
        System.out.println("Either the box is not available or you don't have enough balance.");
    }

    // Method to display player information
    public void displayPlayerInfo() {
        System.out.println("Player: " + playerName);
        System.out.println("Balance: $" + balance);
        System.out.println("Inventory: " + inventory);
    }

    // Start game method
    public void startGame() {
        Scanner scanner = new Scanner(System.in); // Scanner for input
        System.out.println("Enter your name: ");
        playerName = scanner.nextLine(); // Read player's name from input

        boolean isPlaying = true;
        while (isPlaying) {
            System.out.println("1. Deposit Money");
            System.out.println("2. Buy Box");
            System.out.println("3. Display Info");
            System.out.println("4. Exit");
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
