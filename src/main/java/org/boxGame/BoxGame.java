package org.boxGame;

import java.util.ArrayList;
import java.util.Scanner;

public class BoxGame {


    // Define basic attributes
    private String playerName; // Store player's name
    private double balance; // Store player's balance
    private ArrayList<String> inventory;
    private ArrayList<String> availableBoxes; // List of available boxes

    // Constructor
    public BoxGame() {
        inventory = new ArrayList<>(); // Initialize inventory as an empty list
        balance = 0; // Set initial balance to 0

        // Initialize available boxes for demo purpose
        availableBoxes.add("Basic Box - $10");
        availableBoxes.add("Advanced Box - $20");
    }

    // Method for player to deposit money
    public void depositMoney(){
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();
        balance += amount;
        System.out.println("$" + amount + " added to your balance");
    }

    // Method for buying a box
    public void buyBox() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Boxes:");
        for (String box : availableBoxes) {
            System.out.println(box);
        }
        System.out.println("Enter the name of the box to buy: ");
        String boxName = scanner.nextLine();
        // Implement the logic to check box availability and balance ->
        // Then add to inventory
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

        // Further game logic can go here...
        boolean isPlaying = true;
        while (isPlaying) {
            System.out.println("1. Deposit Money");
            System.out.println("1. Deposit Money");
            System.out.println("2. Buy Box");
            System.out.println("3. Display Info");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    depositMoney();
                    break;
                case 2:
                    buyBox();
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
    }

    // Main method - Entry point of the application
    public static void main(String[] args) {
        BoxGame game = new BoxGame();
        game.startGame();
    }


}
