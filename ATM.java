import java.util.ArrayList;
// Imports the ArrayList class for storing collections of elements

import java.util.Scanner;
// Imports the Scanner class to read input from the user

public class ATM {
    // Declares the 'ATM' class, which contains the logic for the ATM system

    static ArrayList<User> users = new ArrayList<>();
    // Static list to store all registered users in the ATM system

    static ArrayList<Notes> notes = new ArrayList<>();
    // Static list to store all the denominations of notes and their counts

    static ArrayList<Admin> admins = new ArrayList<>();
    // Static list to store all the admins of the ATM system

    private static void initializeNotes() {
        // Method to initialize the list of notes with denominations and default counts

        notes.add(new Notes2000(2000, 0));
        // Adds Rs. 2000 notes with a count of 0 to the notes list

        notes.add(new Notes500(500, 0));
        // Adds Rs. 500 notes with a count of 0 to the notes list

        notes.add(new Notes200(200, 0));
        // Adds Rs. 200 notes with a count of 0 to the notes list

        notes.add(new Notes100(100, 0));
        // Adds Rs. 100 notes with a count of 0 to the notes list
    }

    public static void start() {
        // Entry point for the ATM functionality

        Scanner scanner = new Scanner(System.in);
        // Creates a Scanner object to read user input

        admins = new ArrayList<>();
        // Initializes the admins list

        admins.add(new Admin("admin1", "password123"));
        // Adds a default admin with username "admin1" and password "password123"

        initializeNotes();
        // Calls the method to initialize the notes

        while (true) {
            // Infinite loop for the main menu

            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            // Displays the main menu options

            System.out.print("Who is logging in: ");
            // the user to choose a login option

            int login = Integer.parseInt(scanner.nextLine());
            // Reads the user's choice and converts it to an integer

            if (login == 1) {
                // Checks if the user chose Admin Login

                Admin loggedInAdmin = adminLogin(scanner, admins);
                // Calls the adminLogin method to authenticate the admin

                if (loggedInAdmin != null) {
                    // If admin login is successful, proceed to admin actions

                    AdminActions.adminActions(scanner, loggedInAdmin);
                    // Calls the admin actions
                } else {
                    System.out.println("Returning to main menu...");
                    // If login fails, returns to the main menu
                }
            } else if (login == 2) {
                // Checks if the user chose User Login

                System.out.print("Enter UserName: ");
                // the user to enter their username

                String username = scanner.nextLine();
                // Reads the username input

                User user = findUserByUsername(username);
                // Searches for the user by their username

                if (user != null) {
                    // If the user is found, attempt login

                    if (UserActions.userLogin(scanner, user)) {
                        // Calls user login functionality

                        UserActions.userActions(scanner, user);
                        // If login succeeds, proceeds to user actions
                    } else {
                        System.out.println("Login failed. Returning to main menu.");
                        // If login fails, returns to the main menu
                    }
                } else {
                    System.out.println("User not found. Please check the username and try again.");
                    // If user is not found, displays an error message
                }
            } else if (login == 3) {
                // Checks if the user chose to exit

                System.out.println("Exiting the program.");
                // Displays a message program is exiting

                break;
                // Breaks out of the infinite loop to terminate the program
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                // Handles invalid menu choices
            }
        }
    }

    public static Admin adminLogin(Scanner scanner, ArrayList<Admin> admins) {
        // Method to handle admin login functionality

        System.out.print("Enter Admin Username: ");
        // the admin to enter their username

        String username = scanner.nextLine();
        // Reads the username input

        Admin matchedAdmin = null;
        // Initializes a variable to store the matched admin

        for (Admin admin : admins) {
            // Loops through the list of admins

            if (admin.getId().equals(username)) {
                // Checks if the username matches

                matchedAdmin = admin;
                // Assigns the matched admin to the variable

                break;
                // Exits the loop once a match is found
            }
        }

        if (matchedAdmin == null) {
            System.out.println("Invalid username. Returning to login choice.");
            // If no matching admin is found, returns null
            return null;
        }

        int attempts = 3;
        // Sets the number of login attempts

        while (attempts > 0) {
            System.out.print("Enter Admin Password: ");
            // the admin to enter their password

            String password = scanner.nextLine();
            // Reads the password input

            if (matchedAdmin.getPassword().equals(password)) {
                // Checks if the password matches

                System.out.println("Admin login successful!");
                // Displays a success message

                return matchedAdmin;
                // Returns the logged-in admin
            }

            attempts--;
            // Decrements the attempts counter

            if (attempts > 0) {
                System.out.println("Incorrect password. Attempts remaining: " + attempts);
                // Displays the number of remaining attempts
            } else {
                System.out.println("Too many failed attempts. Returning to login choice.");
                // Displays a failure message after the three attempts
            }
        }

        return null;
        // Returns null if login fails after all attempts
    }

    static User findUserByUsername(String username) {
        // Method to find a user by their username

        for (User user : users) {
            // Loops through the list of users

            if (user.getUsername().equals(username)) {
                // Checks if the username matches

                return user;
                // Returns the matched user
            }
        }

        return null;
        // Returns null if no matching user is found
    }

    public static int getTotalATMBalance() {
        // Method to calculate the total balance available in the ATM

        int totalBalance = 0;
        // Initializes the total balance to 0

        for (Notes note : notes) {
            // Loops through the list of notes

            totalBalance += note.getNote() * note.getCount();
            // Adds the value of each note multiplied by its count to the total balance
        }

        return totalBalance;
        // Returns the total balance
    }

    public static boolean withdrawFromATM(int amount) {
        // Method to handle ATM withdrawal functionality

        int totalAvailableAmount = getTotalATMBalance();
        // Retrieves the total balance in the ATM

        if (amount > totalAvailableAmount) {
            System.out.println("Insufficient funds in the ATM.");
            // Displays an error message if the requested amount exceeds the available balance

            return false;
        }

        int remainingAmount = amount;
        // Initializes the remaining amount to the requested amount

        for (Notes note : notes) {
            // Loops through the notes list to dispense the required amount

            int numNotesToWithdraw = Math.min(remainingAmount / note.getNote(), note.getCount());
            // Determines the maximum number of notes to withdraw for the current denomination

            remainingAmount -= numNotesToWithdraw * note.getNote();
            // Reduces the remaining amount by the value of the withdrawn notes

            note.setCount(note.getCount() - numNotesToWithdraw);
            // Updates the count of notes in the ATM

            if (numNotesToWithdraw > 0) {
                System.out.println("Rs. " + note.getNote() + " x " + numNotesToWithdraw);
                // Displays the withdrawn notes
            }
        }

        if (remainingAmount > 0) {
            System.out.println("Cannot dispense the exact amount requested. Please try a different amount.");
            // Displays an error if the exact amount cannot be dispensed

            return false;
        }

        return true;
        // Returns true if the withdrawal is successful
    }

    public static ArrayList<User> getUsers() {
        // Method to retrieve the list of users

        return users;
        // Returns the list of users
    }

}



