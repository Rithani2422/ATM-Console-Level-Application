import java.util.Scanner;

public class UserActions {
    public static boolean userLogin(Scanner scanner, User user) {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Password (Attempts left: " + attempts + "): ");
            String enteredPassword = scanner.nextLine();
            if (user.getPassword().equals(enteredPassword)) {
                System.out.println("User Login Successful.");
                return true;
            } else {
                System.out.println("Incorrect Password.");
                attempts--;
            }
        }
        System.out.println("Too many failed attempts. Returning to main menu.");
        return false;
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

                System.out.println("Enter the number of notes for each denomination:");

                System.out.print("2000: ");
                int notes2000Count = Integer.parseInt(scanner.nextLine());
                Notes2000 notes2000 = new Notes2000(2000, notes2000Count);

                System.out.print("500: ");
                int notes500Count = Integer.parseInt(scanner.nextLine());
                Notes500 notes500 = new Notes500(500, notes500Count);

                System.out.print("200: ");
                int notes200Count = Integer.parseInt(scanner.nextLine());
                Notes200 notes200 = new Notes200(200, notes200Count);

                System.out.print("100: ");
                int notes100Count = Integer.parseInt(scanner.nextLine());
                Notes100 notes100 = new Notes100(100, notes100Count);
                double depositAmount = ATM.depositAmountToATM(notes2000, notes500, notes200, notes100);

                currentUser.setBalance(currentUser.getBalance() + depositAmount);
                Transaction transaction = new Transaction(currentUser.getUsername(), "Deposit", depositAmount);
                currentUser.addTransaction(transaction);

                System.out.println("Successfully Deposited Rs." + depositAmount);
                System.out.println("New Balance: Rs." + currentUser.getBalance());
            }

             else if (choice == 3) {
                System.out.print("Enter the amount to withdraw: ");
                int amount = Integer.parseInt(scanner.nextLine());
                System.out.println("Please wait for your transaction to complete.");
                if (amount > currentUser.getBalance()) {
                    System.out.println("Insufficient user balance.");
                } else {

                    Notes2000 notes2000 = new Notes2000(2000, 10);
                    Notes500 notes500 = new Notes500(500, 10);
                    Notes200 notes200 = new Notes200(200, 10);
                    Notes100 notes100 = new Notes100(100, 10);

                    if (!ATM.withdrawFromATM(amount, notes2000, notes500, notes200, notes100)) {
                        System.out.println("ATM cannot provide the requested amount.");
                    } else {
                        double newBalance = currentUser.getBalance() - amount;
                        currentUser.setBalance(newBalance);
                        Transaction transaction = new Transaction(currentUser.getUsername(), "Withdraw", amount);
                        currentUser.addTransaction(transaction);

                        System.out.println("Withdrawal successful.");
                        System.out.println("New balance: Rs." + newBalance);
                    }
                }

            } else if (choice == 4) {
                System.out.println("Transaction History for " + currentUser.getUsername() + ":");
                if (currentUser.getTransactionHistory().isEmpty()) {
                    System.out.println("No transactions found.");
                } else {
                    for (Transaction transaction : currentUser.getTransactionHistory()) {
                        System.out.println("Type:" + transaction.getType());
                        System.out.println("Performed By:" + transaction.getPerformedBy());
                        System.out.println("Amount:" + transaction.getAmount());
                    }
                }
            } else if (choice == 5) {
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
           else if (choice == 6) {
                System.out.println("Logging out");
                break;
            }
          else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

