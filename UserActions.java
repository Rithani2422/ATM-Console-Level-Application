
import java.util.Scanner;

public class UserActions {
    public static boolean userLogin(Scanner scanner, User user) {
        System.out.print("Enter Password: ");
        String enteredPassword = scanner.nextLine();

        if (user.getPassword().equals(enteredPassword)) {
            System.out.println("User Login Successful.");
            return true;
        } else {
            System.out.println("Incorrect Password. Returning to main menu.");
            return false;
        }
    }

    public static void userActions(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Change PIN");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                System.out.println("Your balance is: Rs." + currentUser.getBalance());
            } else if (choice == 2) {
                depositAmount(scanner, currentUser);
            } else if (choice == 3) {
                withdrawAmount(scanner, currentUser);
            } else if (choice == 4) {
                viewTransactionHistory(currentUser);
            } else if (choice == 5) {
                changePassword(scanner, currentUser);
            } else if (choice == 6) {
                System.out.println("Logging out");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void depositAmount(Scanner scanner, User currentUser) {
        System.out.print("Enter the total deposit amount: ");
        double depositAmount = Double.parseDouble(scanner.nextLine());

        if (depositAmount % 100 != 0) {
            System.out.println("Deposit amount must be a multiple of 100.");
            return;
        }

        System.out.println("Enter the number of notes for each denomination:");
        System.out.print("2000: ");
        int notes2000Count = Integer.parseInt(scanner.nextLine());
        System.out.print("500: ");
        int notes500Count = Integer.parseInt(scanner.nextLine());
        System.out.print("200: ");
        int notes200Count = Integer.parseInt(scanner.nextLine());
        System.out.print("100: ");
        int notes100Count = Integer.parseInt(scanner.nextLine());

        double calculatedDeposit = (2000 * notes2000Count) +
                (500 * notes500Count) +
                (200 * notes200Count) +
                (100 * notes100Count);

        if (calculatedDeposit != depositAmount) {
            System.out.println("The total of denominations does not match the deposit amount. Please try again.");
            return;
        }
        for (Notes note : ATM.notes) {
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
        Transaction transaction = new Transaction(currentUser.getUsername(), "Deposit", depositAmount);
        currentUser.addTransaction(transaction);

        System.out.println("Successfully Deposited Rs." + depositAmount);
        System.out.println("New Balance: Rs." + currentUser.getBalance());
    }

    private static void withdrawAmount(Scanner scanner, User currentUser) {
        System.out.print("Enter the amount to withdraw: ");
        int amount = Integer.parseInt(scanner.nextLine());

        if (amount > currentUser.getBalance()) {
            System.out.println("Insufficient user balance.");
            return;
        }

        if (!ATM.withdrawFromATM(amount)) {
            System.out.println("ATM cannot provide the requested amount due to insufficient denominations.");
        } else {
            double newBalance = currentUser.getBalance() - amount;
            currentUser.setBalance(newBalance);
            Transaction transaction = new Transaction(currentUser.getUsername(), "Withdraw", amount);
            currentUser.addTransaction(transaction);

            System.out.println("Withdrawal successful.");
            System.out.println("New balance: Rs." + newBalance);
        }
    }

    private static void viewTransactionHistory(User currentUser) {
        System.out.println("Transaction History for " + currentUser.getUsername() + ":");
        if (currentUser.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : currentUser.getTransactionHistory()) {
                System.out.println("Type: " + transaction.getType());
                System.out.println("Performed By: " + transaction.getPerformedBy());
                System.out.println("Amount: " + transaction.getAmount());
            }
        }
    }

    private static void changePassword(Scanner scanner, User currentUser) {
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
        if (currentPassword.equals(currentUser.getPassword())) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            if (newPassword.equals(currentPassword)) {
                System.out.println("New password cannot be the same as the current password. Try again.");
            } else {
                currentUser.setPassword(newPassword);
                System.out.println("Password changed successfully.");
            }
        } else {
            System.out.println("Incorrect current password. Password change failed.");
        }
    }
}
