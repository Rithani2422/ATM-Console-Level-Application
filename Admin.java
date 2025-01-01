import java.util.ArrayList;
// Imports the ArrayList class to use arrays for storing data

public class Admin {
    // Defines the 'Admin' class, which represents an admin user in the system

    private String id;
    // Declares a private field to store the admin's ID

    private String password;
    // Declares a private field to store the admin's password

    private ArrayList<Transaction> transactionHistory;
    // Declares a private field to store the admin's transaction history as an ArrayList

    public Admin(String id, String password) {
        // Constructor to initialize an Admin object with an ID and password

        this.id = id;
        // Assigns the provided ID to the 'id' field of the Admin object

        this.password = password;
        // Assigns the provided password to the 'password' field of the Admin object

        this.transactionHistory = new ArrayList<>();
        // Initializes the transaction history as an empty ArrayList
    }

    public String getId() {
        // Getter method to retrieve the admin's ID

        return id;
        // Returns the admin's ID
    }

    public String getPassword() {
        // Getter method to retrieve the admin's password

        return password;
        // Returns the admin's password
    }

    public ArrayList<Transaction> getTransactionHistory() {
        // Getter method to retrieve the admin's transaction history

        return transactionHistory;
        // Returns the transaction history as an ArrayList
    }

    public void addTransaction(Transaction transaction) {
        // Method to add a transaction to the admin's transaction history

        transactionHistory.add(transaction);
        // Adds the provided transaction to the ArrayList
    }
}

