import java.util.ArrayList;
class User {
    private String username;
    private String password;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Account created with balance: Rs." + balance);
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: Rs." + amount + " | New Balance: Rs." + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: Rs." + amount + " | New Balance: Rs." + balance);
        } else {
            transactionHistory.add("Failed withdrawal attempt: Insufficient funds.");
            System.out.println("Insufficient funds. Withdrawal failed.");
        }
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History for " + username + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
