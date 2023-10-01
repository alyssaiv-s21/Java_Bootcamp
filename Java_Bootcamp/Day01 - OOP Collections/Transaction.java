package ex05;

enum TransactionType {
    INCOME,
    OUTCOME
}

public class Transaction {
    private String transactionID;
    private User recipient;
    private User sender;
    private TransactionType type;
    private int amount;

    public Transaction next = null;
    public Transaction prev = null;

    public Transaction(String id, User first, User second, TransactionType type, int sum) {
        transactionID = id;
        recipient = first;
        sender = second;
        this.type = type;
        setAmount(sum);
    }

    public Transaction(Transaction other) {
        transactionID = other.transactionID;
        recipient = other.recipient;
        sender = other.sender;
        type = other.type;
        amount = other.amount;
    }

    public void setTransactionID(String id) {
        transactionID = id;
    }

    public void setRecipient(User get) {
        recipient = get;
    }

    public void setSender(User send) {
        sender = send;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(int sum) {
        if ((type == TransactionType.INCOME && sum >= 0) || (type == TransactionType.OUTCOME && sum <= 0)) {
            amount = sum;
            changeUserBalance();
        } else {
            System.err.println("Transaction with this sum and type is not correct");
        }
    }

    private void changeUserBalance() {
        if (type == TransactionType.INCOME) {
            recipient.setBalance(recipient.getBalance() + amount);
            sender.setBalance(sender.getBalance() - amount);
        }
    }

    public String getTransactionID() {
        return transactionID;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransactionType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String toString() {
        if (type == TransactionType.INCOME) {
            return "To " + recipient.getUserName() + "(" + recipient.getUserID()
                    + ") " + amount + " with id = " + transactionID;
        } else {
            return "From " + recipient.getUserName() + "("
                    + recipient.getUserID() + ") " + amount
                    + " with id = " + transactionID;
        }
    }
}