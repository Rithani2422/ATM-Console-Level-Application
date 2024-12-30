import java.util.Scanner;

public class AdminActions {
    public static boolean adminLogin(Scanner scanner, Admin admin) {
        System.out.print("Enter Admin ID: ");
        String enteredId = scanner.nextLine();

        if (enteredId.equals(admin.getId())) {
            return attemptsOfAdmin(scanner, admin);
        } else {
            System.out.println("Incorrect Admin ID. Returning to main menu.");
            return false;
        }
    }

    private static boolean attemptsOfAdmin(Scanner scanner, Admin admin) {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Password (Attempts left: " + attempts + "): ");
            String enteredPassword = scanner.nextLine();
            if (admin.getPassword().equals(enteredPassword)) {
                System.out.println("Admin Login Successful.");
                return true;
            }
            attempts--;
        }
        System.out.println("Too many failed attempts. Returning to main menu.");
        return false;
    }

    public static void adminActions(Scanner scanner, Admin admin) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add User Account");
            System.out.println("2. Delete User Account");
            System.out.println("3. View All Transaction Histories");
            System.out.println("4. Deposit to ATM");
            System.out.println("5.View Admin Transactions");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                addUserAccount(scanner,admin);
            } else if (choice == 2) {
                deleteUserAccount(scanner);
            } else if (choice == 3) {
                viewTransactionHistory();
            } else if (choice == 4) {
                depositToATM(scanner,admin);
            }
            else if (choice == 5) {
                viewAdminTransactionHistory(admin);
            }
            else if (choice == 6) {
                System.out.println("Logging out");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        }

    private static void addUserAccount(Scanner scanner,Admin admin) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        User userToAdd = ATM.findUserByUsername(username);
        if (userToAdd == null) {
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Initial Balance: ");
            double balance = Double.parseDouble(scanner.nextLine());
            admin.addTransaction(new Transaction("admin","Initial Amount",balance));
            ATM.users.add(new User(username, password, balance));
            System.out.println("User account created successfully.");
        } else {
            System.out.println("User account already exists.");
        }


    }



    private static void deleteUserAccount(Scanner scanner) {
        System.out.print("Enter Username to delete: ");
        String username = scanner.nextLine();
        User userToRemove = ATM.findUserByUsername(username);
        if (userToRemove != null) {
            ATM.users.remove(userToRemove);
            System.out.println("User account deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    private static void viewTransactionHistory() {
        for (User user : ATM.users) {
            System.out.println("Transaction History for user: " + user.getUsername());
            if (user.getTransactionHistory().isEmpty()) {
                System.out.println("No transactions found for this user.");
            } else {
                for (Transaction transaction : user.getTransactionHistory()) {
                    System.out.println("Performed By:" + transaction.getPerformedBy());
                    System.out.println("Type:" + transaction.getType());
                    System.out.println("Amount:" + transaction.getAmount());

                }
            }
            System.out.println();
        }
        System.out.println("End of transaction history. Returning to Admin Menu.");
    }

    private static void depositToATM(Scanner scanner, Admin admin) {
        System.out.println("Enter the number of notes for each denomination:");
        System.out.print("2000: ");
        int notes2000Count = Integer.parseInt(scanner.nextLine());
        System.out.print("500: ");
        int notes500Count = Integer.parseInt(scanner.nextLine());
        System.out.print("200: ");
        int notes200Count = Integer.parseInt(scanner.nextLine());
        System.out.print("100: ");
        int notes100Count = Integer.parseInt(scanner.nextLine());
        ATM.notes2000 += notes2000Count;
        ATM.notes500 += notes500Count;
        ATM.notes200 += notes200Count;
        ATM.notes100 += notes100Count;
        double totalDepositAmount = (notes2000Count * 2000) + (notes500Count * 500) +
                (notes200Count * 200) + (notes100Count * 100);

        System.out.println("Successfully deposited Rs." + totalDepositAmount);
        System.out.println("New ATM Balance: Rs." + ATM.getTotalATMBalance());
        Transaction adminTransaction = new Transaction(admin.getId(), "ATM Deposit", totalDepositAmount);
        admin.addTransaction(adminTransaction);
    }
    private static void viewAdminTransactionHistory(Admin admin) {
        System.out.println("Admin Transaction History:");
        if (admin.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : admin.getTransactionHistory()) {
                System.out.println("Performed By: " + transaction.getPerformedBy());
                System.out.println("Type: " + transaction.getType());
                System.out.println("Amount: " + transaction.getAmount());
            }
        }
        Transaction adminTransaction = new Transaction(admin.getId(), "View Admin Transaction History", 0);
        admin.addTransaction(adminTransaction);

        System.out.println("Viewed Admin Transaction History.");
    }
}
