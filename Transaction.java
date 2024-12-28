public class Transaction {
    private String performedBy;
    private String type;
    private double amount;


    public Transaction( String performedBy,String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.performedBy = performedBy;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getPerformedBy() {
        return performedBy;
    }


}
