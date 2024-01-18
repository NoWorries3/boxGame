package org.boxGame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.boxGame.GameUtils.generateItemsForBox;

public class GameLogic {
    private String playerName;
    private double balance;
    private double totalEarnedFromSelling = 0;
    private double totalDeposited = 0;
    private List<Item> inventory;
    private List<Box> purchasedBoxes;
    private List<Box> availableBoxes = new ArrayList<>(); // Declare and initialize the available boxes list
    private List<Box> playerInventory = new ArrayList<>();

    // Constructor
    public GameLogic() {
        inventory = new ArrayList<>(); // Initialize inventory as an empty list
        balance = 0; // Set initial balance to 0
        purchasedBoxes = new ArrayList<>();
        availableBoxes = new ArrayList<>(); // Initialize availble boxes list in the constructor
        initializeAvailableBoxes();
    }

    public void startGame() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name:");
        playerName = scanner.nextLine();

        /*
        displayMessageOneLetterAtATime("Wake up, " + playerName, 100);
        simulationAfterLogin();
        Thread.sleep(1000);
        displayMessageOneLetterAtATime("Follow the white rabbit", 100);
        followTheWhiteRabbit();
        Thread.sleep(1000);
*/
        followTheWhiteRabbit();
        boolean isPlaying = true;

        int depositCount = 0; // To track the number of deposits
        double totalDeposited = 0; // To track the total amount deposited
        double totalSpent = 0; // To track the total amount spent on box purchases
        double totalWon = 0; // To track the total amount won from opening boxes
        double totalRTP = 0; // To calculate the rate of Return to Player (RTP)

        while (isPlaying) {
            displayMenuOptions();
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:

                        depositMoney(scanner);
                        depositCount++;
                        totalDeposited += balance;
                        break;
                    case 2:
                        buyBox(scanner);
                        totalSpent += calculateTotalCost();
                        break;
                    case 3:
                        openBoxFromMainMenu(scanner);
                        totalWon += calculateTotalItemValue();
                        break;
                    case 4:
                        sellItem(scanner);
                        break;
                    case 5:
                        sellBackBoxes(scanner);
                        break;
                    case 6:
                        displayTotalStats(depositCount, totalDeposited, totalSpent);
                        break;
                    case 7:
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

    // Updated displayMenuOptions method with a decorative frame
    private void displayMenuOptions() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy - HH:mm");
        String dateString = formatter.format(new Date());

        String playerNameBar = playerName + " | ";
        String balanceBar = "$" + String.format("%.2f", balance) + " | ";
        String boxesBar = "[" +
                purchasedBoxes.stream().filter(b -> b.getName().contains("Bronze")).count() + "] Bronze | " +
                "[" + purchasedBoxes.stream().filter(b -> b.getName().contains("Silver")).count() + "] Silver | " +
                "[" + purchasedBoxes.stream().filter(b -> b.getName().contains("Gold")).count() + "] Gold | " +
                "[" + purchasedBoxes.stream().filter(b -> b.getName().contains("Platinum")).count() + "] Platinum";

        // Calculate the total length of the top bar
        int totalLength = playerNameBar.length() + balanceBar.length() + dateString.length() + boxesBar.length();

        // Create the decorative frame
        StringBuilder frame = new StringBuilder();
        frame.append("=");
        for (int i = 0; i < totalLength; i++) {
            frame.append("=");
        }
        frame.append("=");

        // Display the top bar with the frame
        System.out.println(frame.toString());
        System.out.println(playerNameBar + balanceBar + dateString);
        System.out.println(boxesBar);
        System.out.println(frame.toString());

        // Display menu options
        System.out.println("\n1. Deposit Money");
        System.out.println("2. Buy Box");
        System.out.println("3. Open Box");
        System.out.println("4. Sell Item");
        System.out.println("5. Sell Boxes Back");
        System.out.println("6. Total Stats");
        System.out.println("7. Exit");
    }


    // Method for player to deposit money
    public void depositMoney(Scanner scanner) {
        System.out.print("Enter amount to deposit (Max $1,000): ");
        double amount = safeNextDouble(scanner);
        scanner.nextLine(); // Consume the leftover newline character

        final double MAX_DEPOSIT = 1000.00;
        if (amount > 0 && amount <= MAX_DEPOSIT) {
            balance += amount;
            System.out.println("\n$" + String.format("%.2f", amount) + " added to your balance\n");
        } else {
            System.out.println("\nInvalid deposit amount. Please enter a value up to $10,000.\n");
        }
    }

    private void initializeAvailableBoxes() {
        availableBoxes.add(new Box("Bronze Box", 31.15, generateItemsForBox("Bronze Box")));
        availableBoxes.add(new Box("Silver Box", 81.93, generateItemsForBox("Silver Box")));
        availableBoxes.add(new Box("Gold Box", 183.23, generateItemsForBox("Gold Box")));
        availableBoxes.add(new Box("Platinum Box", 740.63, generateItemsForBox("Platinum Box")));
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

        if (boxIndex < 0 || boxIndex >= availableBoxes.size()) {
            System.out.println("\nInvalid box selection.\n");
            return;
        }

        final int MAX_BOXES = 1000;
        System.out.print("Enter the quantity of boxes to buy (Max 1000): ");
        int quantity = safeNextInt(scanner);
        scanner.nextLine(); // Consume the newline character

        if (quantity <= 0 || quantity > MAX_BOXES) {
            System.out.println("\nInvalid quantity. Please enter a number up to 1000.\n");
            return;
        }

        Box selectedBox = availableBoxes.get(boxIndex);
        double totalCost = selectedBox.getPrice() * quantity;

        if (balance >= totalCost) {
            balance -= totalCost;
            for (int i = 0; i < quantity; i++) {
                purchasedBoxes.add(selectedBox);

                // Generate a new box and add it to available boxes
                availableBoxes.add(GameUtils.generateBox(selectedBox.getName()));
            }
            System.out.println("You bought " + quantity + " " + selectedBox.getName() + "(s)!");
        } else {
            System.out.println("Insufficient balance to complete this purchase.");
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
                double sellBackPrice = calculateSellBackPrice(box); // Calculate the current sell-back price
                System.out.println((i + 1) + ". " + box.getName() + " - Price: $" + box.getPrice() + " (Sell Back: $" + sellBackPrice + ")");
            }
        }
    }

    private void openBoxFromMainMenu(Scanner scanner) {
        if (purchasedBoxes.isEmpty()) {
            System.out.println("You don't have any boxes to open.");
            return;
        }
        viewPurchasedBoxes();
        System.out.print("Choose a box to open: ");
        int boxIndex = safeNextInt(scanner) - 1;
        scanner.nextLine(); // Consume the newline character

        if (boxIndex >= 0 && boxIndex < purchasedBoxes.size()) {
            simulateSuspensefulOpening();
            Box boxToOpen = purchasedBoxes.remove(boxIndex);
            System.out.println("You opened " + boxToOpen.getName() + " and found:");

            // Generate random items for the box when it is opened
            boxToOpen.setItems(generateItemsForBox(boxToOpen.getName()));

            // Display the items won
            for (Item item : boxToOpen.getItems()) {
                System.out.println("- " + item.getName() + " - $" + item.getBasePrice());
            }

            inventory.addAll(boxToOpen.getItems());
        } else {
            System.out.println("Invalid box selection.");
        }
    }


    // Helper method to determine item sale price
    private double getItemSalePrice(Item item) {
                return item.getBasePrice();
    }

    // Updated sellItem method to use item IDs and display item prices
    public void sellItem(Scanner scanner) {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("Your Inventory:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getBasePrice());
        }
        System.out.print("Enter the number of the item to sell: ");
        int itemIndex = safeNextInt(scanner) - 1;
        scanner.nextLine(); // Consume the newline character

        if (itemIndex >= 0 && itemIndex < inventory.size()) {
            Item itemToSell = inventory.remove(itemIndex);
            double salePrice = getItemSalePrice(itemToSell);
            balance += salePrice;
            totalEarnedFromSelling += salePrice; // Update total earned from selling
            System.out.println(itemToSell.getName() + " sold for $" + salePrice);
        } else {
            System.out.println("Invalid item selection.");
        }
    }

    // Implement the sellBackBoxes method
    private void sellBackBoxes(Scanner scanner) {
        if (purchasedBoxes.isEmpty()) {
            System.out.println("\nYou don't have any boxes to sell back.\n");
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
            System.out.println("\nInvalid box selection.\n");
        }
    }

    // New method to display total statistics
    private void displayTotalStats(int depositCount, double totalDeposited, double totalSpent) {
        double profit = totalEarnedFromSelling - totalSpent;
        double totalRTP = (totalSpent > 0) ? (totalEarnedFromSelling / totalSpent) * 100 : 0; // Calculate RTP

        System.out.println("\nTotal Stats:");
        System.out.println("Deposits Made: " + depositCount);
        System.out.println("Total Deposited: $" + String.format("%.2f", totalDeposited));
        System.out.println("Total Spent on Boxes: $" + String.format("%.2f", totalSpent));
        System.out.println("Total Earned from Selling Items: $" + String.format("%.2f", totalEarnedFromSelling));
        System.out.println("Profit: $" + String.format("%.2f", profit));
        System.out.println("Rate of Return to Player (RTP): " + String.format("%.2f", totalRTP) + "%");
    }



    // Calculate the sell-back price (e.g., 50% of the original price)
    private double calculateSellBackPrice(Box box) {
        double sellBackPercentage = 0.5; // Adjust this as needed (e.g., 50% sell-back price)
        return box.getPrice() * sellBackPercentage;
    }

    // New method to calculate the total cost of box purchases
    private double calculateTotalCost() {
        double totalCost = 0;
        for (Box box : purchasedBoxes) {
            totalCost += box.getPrice();
        }
        return totalCost;
    }

    // New method to calculate the total value of items won from boxes
    private double calculateTotalItemValue() {
        double totalValue = 0;
        for (Box box : purchasedBoxes) {
            for (Item item : box.getItems()) {
                totalValue += item.getBasePrice();
            }
        }
        return totalValue;
    }


    private void simulateSuspensefulOpening() {
        try {
            System.out.print("Opening");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500); // Wait for 1 second
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void simulationAfterLogin() {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500); // Wait for 0.5 second
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Updated displayWinAnimation method with simplified animation and item prices
    private void displayWinAnimation(Item item) {
        if (item.getRarity().equals("Rare") || item.getRarity().equals("Legendary")) {
            String rarity = item.getRarity();
            System.out.println("Opening...");
            System.out.println("You opened " + purchasedBoxes.get(purchasedBoxes.size() - 1).getName() + " and found:");
            System.out.println("Congratulations!");
            System.out.println("======================================");
            System.out.println("You found a " + rarity + " item!");
            System.out.println("======================================");
            try {
                Thread.sleep(5000); // Wait for 5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("- " + item.getName() + " (Legendary) - $" + item.getBasePrice());
        }
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

    // Method to display a message one letter at a time
    private void displayMessageOneLetterAtATime(String message, int delay) {
        for (char letter : message.toCharArray()) {
            System.out.print(letter);
            try {
                Thread.sleep(delay); // Wait for the specified delay between letters
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // Move to the next line after typing the message
    }

    public void followTheWhiteRabbit() throws InterruptedException {
        final String[] rabbitLines = {
                "  /\\_/\\",
                " ( o.o )",
                "  > ^ <"
        };
        final int steps = 3; // Number of steps to move the rabbit
        final int delay = 1000; // Delay in milliseconds for each step

        for (int i = 0; i < steps; i++) {
            // Clear the console (optional, if supported)
            clearConsole();

            // Print each line of the rabbit, shifted by 'i' spaces
            for (String line : rabbitLines) {
                System.out.print(String.format("%" + (i + line.length()) + "s", line) + "\n");
            }


            Thread.sleep(delay); // Wait for a specified delay between steps
        }
    }

    private void clearConsole() {
        // Clears the console. This method works in some environments but not all.
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }



}
