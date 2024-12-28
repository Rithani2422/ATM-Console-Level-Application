import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add(new Transaction(username, "Initial Amount", balance));
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }
}


