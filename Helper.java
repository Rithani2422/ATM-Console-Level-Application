import java.util.ArrayList;
import java.util.Scanner;

public class Helper {
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin1", "password123");

        while (true) {
            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            System.out.print("Who is logging in: ");
            int loginChoice = Integer.parseInt(scanner.nextLine());


            if (loginChoice == 1) {
                System.out.print("Enter Admin ID: ");
                String enteredId = scanner.nextLine();
                if (enteredId.equals(admin.getId())) {
                    if (attemptsOfAdmin(scanner, admin)) {
                        AdminActions(scanner);
                    }
                } else {
                    System.out.println("Incorrect Admin ID.");
                }

            } else if (loginChoice == 2) {

                System.out.print("Enter Username: ");
                String enteredUsername = scanner.nextLine();
                User currentUser = findUserByUsername(enteredUsername);
                if (currentUser != null) {
                    if (attemptsOfUser(scanner, currentUser)) {
                        UserActions(scanner, currentUser);
                    }
                } else {
                    System.out.println("User not found.");
                }

            } else if (loginChoice == 3) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }
    private static boolean attemptsOfAdmin(Scanner scanner, Admin admin) {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Password (Attempts left: " + attempts + "): ");
            String enteredPassword = scanner.nextLine();
            if (admin.checkPassword(enteredPassword)) {
                System.out.println("Admin Login Successful!");
                return true;
            }
            attempts--;
        }
        System.out.println("Too many failed attempts. Returning to main menu.");
        return false;
    }


    private static boolean attemptsOfUser(Scanner scanner, User user) {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Password (Attempts left: " + attempts + "): ");
            String enteredPassword = scanner.nextLine();
            if (user.checkPassword(enteredPassword)) {
                System.out.println("User Login Successful!");
                return true;
            }
            attempts--;
        }
        System.out.println("Too many failed attempts. Returning to main menu.");
        return false;
    }

    private static void AdminActions(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add User Account");
            System.out.println("2. Delete User Account");
            System.out.println("3. View All Transaction Histories");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {

                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();
                System.out.print("Enter Initial Balance: ");
                double balance = Double.parseDouble(scanner.nextLine());
                users.add(new User(username, password, balance));
                System.out.println("User account created successfully.");

            } else if (choice == 2) {

                System.out.print("Enter Username to delete: ");
                String username = scanner.nextLine();
                User userToRemove = findUserByUsername(username);
                if (userToRemove != null) {
                    users.remove(userToRemove);
                    System.out.println("User account deleted successfully.");
                } else {
                    System.out.println("User not found.");
                }

            } else if (choice == 3) {

                for (User user : users) {
                    user.viewTransactionHistory();
                    System.out.println();
                }

            } else if (choice == 4) {
                System.out.println("Logging out");
                break;

            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void UserActions(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {

                System.out.println("Current Balance: Rs." + currentUser.getBalance());

            } else if (choice == 2) {

                System.out.print("Enter amount to deposit: ");
                double amount = Double.parseDouble(scanner.nextLine());
                currentUser.deposit(amount);
                System.out.println("Deposit successful.");

            } else if (choice == 3) {

                System.out.print("Enter amount to withdraw: ");
                double amount = Double.parseDouble(scanner.nextLine());
                currentUser.withdraw(amount);

            } else if (choice == 4) {

                currentUser.viewTransactionHistory();

            } else if (choice == 5) {
                System.out.println("Logging out...");
                break;

            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
