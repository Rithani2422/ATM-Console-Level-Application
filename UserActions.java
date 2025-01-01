
import java.util.Scanner;
// Imports the Scanner class to read user input

public class UserActions {
    // Defines the 'UserActions' class, which contains methods for user operations

    public static boolean userLogin(Scanner scanner, User user) {
        // Method to handle user login

        System.out.print("Enter Password: ");
        // the user to enter their password

        String enteredPassword = scanner.nextLine();
        // Reads the entered password

        if (user.getPassword().equals(enteredPassword)) {
            // Checks if the entered password matches the user's password

            System.out.println("User Login Successful.");
            // Displays a success message if the password is correct

            return true;
            // Returns true indicating successful login
        } else {
            System.out.println("Incorrect Password. Returning to main menu.");
            // Displays an error message for incorrect password

            return false;
            // Returns false indicating failed login
        }
    }

    public static void userActions(Scanner scanner, User currentUser) {
        // Method to display the user menu and handle user actions

        while (true) {
            // Infinite loop for the user menu

            System.out.println("\nUser Menu:");
            // Displays the user menu header

            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Change PIN");
            System.out.println("6. Logout");
            // Displays the available user actions

            System.out.print("Choose an option: ");
            // the user to select an option
            int choice = Integer.parseInt(scanner.nextLine());
            // Reads and parses the user's choice

            if (choice == 1) {
                // Option to check balance

                System.out.println("Your balance is: Rs." + currentUser.getBalance());
                // Displays the user's current balance
            } else if (choice == 2) {
                depositAmount(scanner, currentUser);
                // Calls the method to deposit money
            } else if (choice == 3) {
                withdrawAmount(scanner, currentUser);
                // Calls the method to withdraw money
            } else if (choice == 4) {
                viewTransactionHistory(currentUser);
                // Calls the method to view transaction history
            } else if (choice == 5) {
                changePassword(scanner, currentUser);
                // Calls the method to change the user's password
            } else if (choice == 6) {
                System.out.println("Logging out");
                // Displays a logout message

                break;
                // Breaks the loop to exit the user menu
            } else {
                System.out.println("Invalid choice. Please try again.");
                // Handles invalid menu choices
            }
        }
    }

    private static void depositAmount(Scanner scanner, User currentUser) {
        // Method to deposit money into the user's account

        System.out.print("Enter the total deposit amount: ");
        // the user to enter the total deposit amount

        double depositAmount = Double.parseDouble(scanner.nextLine());
        // Reads and parses the deposit amount

        if (depositAmount % 100 != 0) {
            // Checks if the deposit amount is a multiple of 100

            System.out.println("Deposit amount must be a multiple of 100.");
            // Displays an error message if the condition is not met

            return;
        }

        System.out.println("Enter the number of notes for each denomination:");
        // the user to enter the count of notes for each denomination

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

        double calculatedDeposit = (2000 * notes2000Count) +
                (500 * notes500Count) +
                (200 * notes200Count) +
                (100 * notes100Count);
        // Calculates the total deposit amount based on the notes entered

        if (calculatedDeposit != depositAmount) {
            // Checks if the calculated deposit matches the entered deposit amount

            System.out.println("The total of denominations does not match the deposit amount. Please try again.");
            // Displays an error message if the amounts do not match

            return;
        }

        for (Notes note : ATM.notes) {
            // Loops through the list of ATM notes to update their counts

            if (note.getNote() == 2000) {
                note.setCount(note.getCount() + notes2000Count);
            } else if (note.getNote() == 500) {
                note.setCount(note.getCount() + notes500Count);
            } else if (note.getNote() == 200) {
                note.setCount(note.getCount() + notes200Count);
            } else if (note.getNote() == 100) {
                note.setCount(note.getCount() + notes100Count);
            }
        }

        currentUser.setBalance(currentUser.getBalance() + depositAmount);
        // Updates the user's balance by adding the deposit amount

        Transaction transaction = new Transaction(currentUser.getUsername(), "Deposit", depositAmount);
        // Creates a new transaction record for the deposit

        currentUser.addTransaction(transaction);
        // Adds the transaction to the user's transaction history

        System.out.println("Successfully Deposited Rs." + depositAmount);
        // Displays a success message for the deposit

        System.out.println("New Balance: Rs." + currentUser.getBalance());
        // Displays the updated balance
    }

    private static void withdrawAmount(Scanner scanner, User currentUser) {
        // Method to withdraw money from the user's account

        System.out.print("Enter the amount to withdraw: ");
        // the user to enter the withdrawal amount

        int amount = Integer.parseInt(scanner.nextLine());
        // Reads and parses the withdrawal amount

        if (amount > currentUser.getBalance()) {
            // Checks if the user's balance is sufficient for the withdrawal

            System.out.println("Insufficient user balance.");
            // Displays an error message if the balance is insufficient

            return;
        }

        if (!ATM.withdrawFromATM(amount)) {
            // Attempts to withdraw the requested amount from the ATM

            System.out.println("ATM cannot provide the requested amount due to insufficient denominations.");
            // Displays an error message if the ATM cannot dispense the amount
        } else {
            double newBalance = currentUser.getBalance() - amount;
            // Calculates the new balance after withdrawal

            currentUser.setBalance(newBalance);
            // Updates the user's balance

            Transaction transaction = new Transaction(currentUser.getUsername(), "Withdraw", amount);
            // Creates a new transaction record for the withdrawal

            currentUser.addTransaction(transaction);
            // Adds the transaction to the user's transaction history

            System.out.println("Withdrawal successful.");
            // Displays a success message for the withdrawal

            System.out.println("New balance: Rs." + newBalance);
            // Displays the updated balance
        }
    }

    private static void viewTransactionHistory(User currentUser) {
        // Method to view the user's transaction history

        System.out.println("Transaction History for " + currentUser.getUsername() + ":");
        // Displays the user's transaction history header

        if (currentUser.getTransactionHistory().isEmpty()) {
            // Checks if the user has any transactions

            System.out.println("No transactions found.");
            // Displays a message if there are no transactions
        } else {
            for (Transaction transaction : currentUser.getTransactionHistory()) {
                // Loops through the user's transaction history

                System.out.println("Type: " + transaction.getType());
                System.out.println("Performed By: " + transaction.getPerformedBy());
                System.out.println("Amount: " + transaction.getAmount());
                // Displays the details of each transaction
            }
        }
    }

    private static void changePassword(Scanner scanner, User currentUser) {
        // Method to change the user's password

        System.out.print("Enter your current password: ");
        // Prompts the user to enter their current password

        String currentPassword = scanner.nextLine();
        // Reads the current password

        if (currentPassword.equals(currentUser.getPassword())) {
            // Checks if the entered password matches the current password

            System.out.print("Enter your new password: ");
            // Prompts the user to enter a new password

            String newPassword = scanner.nextLine();
            // Reads the new password

            if (newPassword.equals(currentPassword)) {
                // Checks if the new password is the same as the current password

                System.out.println("New password cannot be the same as the current password. Try again.");
                // Displays an error message if the passwords match
            } else {
                currentUser.setPassword(newPassword);
                // Updates the user's password

                System.out.println("Password changed successfully.");
                // Displays a success message for the password change
            }
        } else {
            System.out.println("Incorrect current password. Password change failed.");
            // Displays an error message if the current password is incorrect
        }
    }
}
