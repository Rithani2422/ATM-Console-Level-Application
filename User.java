import java.util.ArrayList;
// Imports the ArrayList class to enable the use of arrays for storing data

public class User {
    // Defines the 'User' class, representing a user in the system

    private String username;
    // Declares a private field to store the username of the user

    private String password;
    // Declares a private field to store the user's password

    private double balance;
    // Declares a private field to store the user's account balance

    private ArrayList<Transaction> transactionHistory;
    // Declares a private field to store the user's transaction history as an ArrayList

    public User(String username, String password, double balance) {
        // Constructor to initialize a User object with a username, password, and initial balance

        this.username = username;
        // Assigns the provided username to the 'username' field

        this.password = password;
        // Assigns the provided password to the 'password' field

        this.balance = balance;
        // Assigns the provided balance to the 'balance' field

        this.transactionHistory = new ArrayList<>();
        // Initializes the transaction history as an empty ArrayList
    }

    public String getUsername() {
        // Getter method to retrieve the username of the user

        return username;
        // Returns the user's username
    }

    public String getPassword() {
        // Getter method to retrieve the password of the user

        return password;
        // Returns the user's password
    }

    public void setPassword(String password) {
        // Setter method to update the user's password

        this.password = password;
        // Updates the 'password' field with the provided value
    }

    public double getBalance() {
        // Getter method to retrieve the user's account balance

        return balance;
        // Returns the user's balance
    }

    public void setBalance(double balance) {
        // Setter method to update the user's account balance

        this.balance = balance;
        // Updates the 'balance' field with the provided value
    }

    public ArrayList<Transaction> getTransactionHistory() {
        // Getter method to retrieve the user's transaction history

        return transactionHistory;
        // Returns the transaction history as an ArrayList
    }

    public void addTransaction(Transaction transaction) {
        // Method to add a transaction to the user's transaction history

        this.transactionHistory.add(transaction);
        // Adds the provided transaction object to the ArrayList
    }
}

