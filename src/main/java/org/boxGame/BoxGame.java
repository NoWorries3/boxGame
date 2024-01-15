package org.boxGame;

import java.util.ArrayList;
import java.util.Scanner;

public class BoxGame {


    // Define basic attributes
    private String playerName; // Store player's name
    private double balance; // Store player's balance
    private ArrayList<String> inventory;

    // Constructor
    public BoxGame() {
        inventory = new ArrayList<>(); // Initialize inventory as an empty list
        balance = 0; // Set initial balance to 0
    }

    // Start game method
    public void startGame() {
        Scanner scanner = new Scanner(System.in); // Scanner for input
        System.out.println("Enter your name: ");
        playerName = scanner.nextLine(); // Read player's name from input

        // Further game logic can go here...

    }

    // Main method - Entry point of the application
    public static void main(String[] args) {
        BoxGame game = new BoxGame();
        game.startGame();
    }


}
