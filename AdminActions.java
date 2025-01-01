import java.util.Scanner;
// Imports the Scanner class to read user input

public class AdminActions {
    // Defines the 'AdminActions' class, which handles admin-specific functionalities

    public static void adminActions(Scanner scanner, Admin admin) {
        // Method to display the admin menu and handle admin actions

        while (true) {
             // Infinite loop for the admin menu

            System.out.println("\nAdmin Menu:");
            // Displays the admin menu header

            System.out.println("1. Add User Account");
            System.out.println("2. Delete User Account");
            System.out.println("3. View All Transaction Histories");
            System.out.println("4. Deposit to ATM");
            System.out.println("5. View Admin Transactions");
            System.out.println("6. Logout");
            // Displays the available admin actions

            System.out.print("Choose an option: ");
            // the admin to select an option

            int choice = Integer.parseInt(scanner.nextLine());
            // Reads and parses the admin's choice

            if (choice == 1) {
                addUserAccount(scanner, admin);
                // Calls the method to add a user account
            } else if (choice == 2) {
                deleteUserAccount(scanner);
                // Calls the method to delete a user account
            } else if (choice == 3) {
                viewTransactionHistory();
                // Calls the method to view all user transaction histories
            } else if (choice == 4) {
                depositToATM(scanner, admin);
                // Calls the method to deposit money into the ATM
            } else if (choice == 5) {
                viewAdminTransactionHistory(admin);
                // Calls the method to view the admin's transaction history
            } else if (choice == 6) {
                System.out.println("Logging out");
                // Displays a logout message

                break;
                // Breaks the loop to exit the admin menu
            } else {
                System.out.println("Invalid choice. Please try again.");
                // Handles invalid menu choices
            }
        }
    }

    private static void addUserAccount(Scanner scanner, Admin admin) {
        // Method to add a new user account

        System.out.print("Enter Username: ");
        // the admin to enter a username for the new user

        String username = scanner.nextLine();
        // Reads the username input

        User userToAdd = ATM.findUserByUsername(username);
        // Checks if a user with the same username already exists

        if (userToAdd == null) {
            // If the username is not found, proceed to create a new account

            System.out.print("Enter Password: ");
            // the admin to enter a password for the new user

            String password = scanner.nextLine();
            // Reads the password input

            System.out.print("Enter Initial Balance: ");
            // the admin to enter the initial balance for the new user

            double balance = Double.parseDouble(scanner.nextLine());
            // Reads and parses the initial balance

            admin.addTransaction(new Transaction("admin", "Initial Amount", balance));
            // Logs the transaction for the admin

            ATM.getUsers().add(new User(username, password, balance));
            // Adds the new user to the list of users in the ATM system

            System.out.println("User account created successfully.");
            // Displays a success message
        } else {
            System.out.println("User account already exists.");
            // Displays an error message if the username is already taken
        }
    }

    private static void deleteUserAccount(Scanner scanner) {
        // Method to delete an existing user account

        System.out.print("Enter Username to delete: ");
        // the admin to enter the username of the account to delete

        String username = scanner.nextLine();
        // Reads the username input

        User userToRemove = ATM.findUserByUsername(username);
        // Finds the user with the specified username

        if (userToRemove != null) {
            // If the user is found, proceed to delete the account

            ATM.getUsers().remove(userToRemove);
            // Removes the user from the list of users in the ATM system
            System.out.println("User account deleted successfully.");
            // Displays a success message
        } else {
            System.out.println("User not found.");
            // Displays an error message if the user is not found
        }
    }

    private static void viewTransactionHistory() {
        // Method to view the transaction history of all users

        for (User user : ATM.users) {
            // Loops through the list of users

            System.out.println("Transaction History for user: " + user.getUsername());
            // Displays the username

            if (user.getTransactionHistory().isEmpty()) {
                System.out.println("No transactions found for this user.");
                // Displays a message if the user has no transactions
            } else {
                for (Transaction transaction : user.getTransactionHistory()) {
                    // Loops through the transaction history of the user

                    System.out.println("Performed By: " + transaction.getPerformedBy());
                    System.out.println("Type: " + transaction.getType());
                    System.out.println("Amount: " + transaction.getAmount());
                    // Displays the details of each transaction
                }
            }
            System.out.println();
        }
        System.out.println("End of transaction history. Returning to Admin Menu.");
        // Displays a message indicating the end of the transaction history
    }

    private static void depositToATM(Scanner scanner, Admin admin) {
        // Method to deposit money into the ATM

        System.out.print("Enter the total deposit amount: Rs. ");
        // the admin to enter the total deposit amount

        double totalDepositAmount = Double.parseDouble(scanner.nextLine());
        // Reads and parses the deposit amount

        System.out.println("Enter the number of notes for each denomination:");
        // the admin to enter the count of notes for each denomination

        System.out.print("2000: ");
        int notes2000Count = Integer.parseInt(scanner.nextLine());
        // Reads the count of Rs. 2000 notes

        System.out.print("500: ");
        int notes500Count = Integer.parseInt(scanner.nextLine());
        // Reads the count of Rs. 500 notes

        System.out.print("200: ");
        int notes200Count = Integer.parseInt(scanner.nextLine());
        // Reads the count of Rs. 200 notes

        System.out.print("100: ");
        int notes100Count = Integer.parseInt(scanner.nextLine());
        // Reads the count of Rs. 100 notes

        double calculatedTotal = (notes2000Count * 2000) + (notes500Count * 500) +
                (notes200Count * 200) + (notes100Count * 100);
        // Calculates the total value of the notes entered

        if (calculatedTotal == totalDepositAmount) {
            // Checks if the calculated total matches the entered deposit amount

            updateATMNotes(2000, notes2000Count);
            updateATMNotes(500, notes500Count);
            updateATMNotes(200, notes200Count);
            updateATMNotes(100, notes100Count);
            // Updates the count of notes in the ATM for each denomination

            System.out.println("Successfully deposited Rs." + totalDepositAmount);
            // Displays a success message

            System.out.println("New ATM Balance: Rs." + ATM.getTotalATMBalance());
            // Displays the updated ATM balance

            Transaction adminTransaction = new Transaction(admin.getId(), "ATM Deposit", totalDepositAmount);
            // Creates a transaction record for the deposit

            admin.addTransaction(adminTransaction);
            // Adds the transaction to the admin's transaction history
        } else {
            System.out.println("Error: The denominations entered do not match the total deposit amount.");
            // Displays an error message if the amounts do not match
        }
    }

    private static void updateATMNotes(int denomination, int count) {
        // Method to update the count of a specific denomination in the ATM

        for (Notes note : ATM.notes) {
            // Loops through the list of notes

            if (note.getNote() == denomination) {
                // Checks if the denomination matches

                note.setCount(note.getCount() + count);
                // Updates the count of the matching denomination

                return;
            }
        }

        ATM.notes.add(new Notes(denomination, count));
        // Adds a new denomination if it does not already exist
    }

    private static void viewAdminTransactionHistory(Admin admin) {
        // Method to view the admin's transaction history

        System.out.println("Admin Transaction History:");
        // Displays a header for the admin's transaction history

        if (admin.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found.");
            // Displays a message if no transactions are found
        } else {
            for (Transaction transaction : admin.getTransactionHistory()) {
                // Loops through the admin's transaction history

                System.out.println("Performed By: " + transaction.getPerformedBy());
                System.out.println("Type: " + transaction.getType());
                System.out.println("Amount: " + transaction.getAmount());
                // Displays the details of each transaction
            }
        }
        System.out.println("Viewed Admin Transaction History.");
        // Displays a message indicating the history has been viewed
    }
}


