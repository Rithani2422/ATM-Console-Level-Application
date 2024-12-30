import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    static ArrayList<User> users = new ArrayList<>();
    public static int notes2000 = 0;
    public static int notes500 = 0;
    public static int notes200 = 0;
    public static int notes100 = 0;


    public static void start() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin1", "password123");

        while (true) {
            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            System.out.print("Who is logging in: ");
            int login = Integer.parseInt(scanner.nextLine());
            if (login == 1) {
                if (AdminActions.adminLogin(scanner, admin)) {
                    AdminActions.adminActions(scanner, admin);
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

    static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static int getTotalATMBalance() {
        return (notes2000 * 2000) + (notes500 * 500) + (notes200 * 200) + (notes100 * 100);
    }


    public static boolean withdrawFromATM(int amount, Notes2000 notes2000, Notes500 notes500, Notes200 notes200, Notes100 notes100) {

        int totalAvailableAmount = notes2000.getNote() * notes2000.getCount() +
                notes500.getNote() * notes500.getCount() +
                notes200.getNote() * notes200.getCount() +
                notes100.getNote() * notes100.getCount();


        if (amount > totalAvailableAmount) {
            return false;
        }
        int remainingAmount = amount;
        int num2000NotesToWithdraw = Math.min(remainingAmount / notes2000.getNote(), notes2000.getCount());
        remainingAmount -= num2000NotesToWithdraw * notes2000.getNote();
        int num500NotesToWithdraw = Math.min(remainingAmount / notes500.getNote(), notes500.getCount());
        remainingAmount -= num500NotesToWithdraw * notes500.getNote();
        int num200NotesToWithdraw = Math.min(remainingAmount / notes200.getNote(), notes200.getCount());
        remainingAmount -= num200NotesToWithdraw * notes200.getNote();
        int num100NotesToWithdraw = Math.min(remainingAmount / notes100.getNote(), notes100.getCount());
        remainingAmount -= num100NotesToWithdraw * notes100.getNote();
        if (remainingAmount == 0) {
            System.out.println("Withdrawal successful. Denomination");
            if (num2000NotesToWithdraw > 0) {
                System.out.println("Rs. 2000 x " + num2000NotesToWithdraw);
            }
            if (num500NotesToWithdraw > 0) {
                System.out.println("Rs. 500 x " + num500NotesToWithdraw);
            }
            if (num200NotesToWithdraw > 0) {
                System.out.println("Rs. 200 x " + num200NotesToWithdraw);
            }
            if (num100NotesToWithdraw > 0) {
                System.out.println("Rs. 100 x " + num100NotesToWithdraw);
            }
            return true;
        }
        return false;
    }



    public static double depositAmountToATM(Notes2000 notes2000, Notes500 notes500, Notes200 notes200, Notes100 notes100) {
        return notes2000.getNote() * notes2000.getCount() +
                notes500.getNote() * notes500.getCount() +
                notes200.getNote() * notes200.getCount() +
                notes100.getNote() * notes100.getCount();
    }
}


