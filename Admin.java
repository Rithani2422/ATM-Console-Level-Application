import java.util.ArrayList;

public class Admin {
    private String id;
    private String password;
    private ArrayList<Transaction> transactionHistory;

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
        this.transactionHistory = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

}
