import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Notes> notes = new ArrayList<>();
    static ArrayList<Admin> admins = new ArrayList<>();

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        admins = new ArrayList<>();
        admins.add(new Admin("admin1", "password123"));
        initializeNotes();

        while (true) {
            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            System.out.print("Who is logging in: ");
            int login = Integer.parseInt(scanner.nextLine());

            if (login == 1) {
                Admin loggedInAdmin = adminLogin(scanner, admins);
                if (loggedInAdmin != null) {
                    AdminActions.adminActions(scanner, loggedInAdmin);
                } else {
                    System.out.println("Returning to main menu...");
                }
            } else if (login == 2) {
                System.out.print("Enter UserName: ");
                String username = scanner.nextLine();
                User user = findUserByUsername(username);

                if (user != null) {
                    if (UserActions.userLogin(scanner, user)) {
                        UserActions.userActions(scanner, user);
                    } else {
                        System.out.println("Login failed. Returning to main menu.");
                    }
                } else {
                    System.out.println("User not found. Please check the username and try again.");
                }
            } else if (login == 3) {
                System.out.println("Exiting the program.");
                break;
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }

        }
    public static Admin adminLogin(Scanner scanner, ArrayList<Admin> admins) {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Admin Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Admin Password: ");
            String password = scanner.nextLine();

            for (Admin admin : admins) {
                if (admin.getId().equals(username) && admin.getPassword().equals(password)) {
                    System.out.println("Admin login successful!");
                    return admin;
                }
            }
            attempts--;
            if (attempts > 0) {
                System.out.println("Invalid username or password. Attempts remaining: " + attempts);
            } else {
                System.out.println("Too many failed attempts. Returning to the main menu.");
            }
        }
        return null;
    }
    static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private static void initializeNotes() {
        notes.add(new Notes(2000, 10));
        notes.add(new Notes(500, 20));
        notes.add(new Notes(200, 30));
        notes.add(new Notes(100, 50));
    }

    public static int getTotalATMBalance() {
        int totalBalance = 0;
        for (Notes note : notes) {
            totalBalance += note.getNote() * note.getCount();
        }
        return totalBalance;
    }

    public static boolean withdrawFromATM(int amount) {
        int totalAvailableAmount = getTotalATMBalance();
        if (amount > totalAvailableAmount) {
            System.out.println("Insufficient funds in the ATM.");
            return false;
        }

        int remainingAmount = amount;
        for (Notes note : notes) {
            int numNotesToWithdraw = Math.min(remainingAmount / note.getNote(), note.getCount());
            remainingAmount -= numNotesToWithdraw * note.getNote();
            note.setCount(note.getCount() - numNotesToWithdraw);
            if (numNotesToWithdraw > 0) {
                System.out.println("Rs. " + note.getNote() + " x " + numNotesToWithdraw);
            }
        }

        if (remainingAmount > 0) {
            System.out.println("Cannot dispense the exact amount requested. Please try a different amount.");
            return false;
        }
        return true;
    }
}



