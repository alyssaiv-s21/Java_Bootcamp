package ex05;

public class User {
    private int userID;
    private String userName;
    private int balance;

    public User(String name, int amount) {
        userID = UserIdsGenerator.getInstance().generateId();
        userName = name;
        setBalance(amount);
        System.out.println("User with id = " + userID + " is added");
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getBalance() {
        return balance;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public void setBalance(int amount) {
        if (amount >= 0) {
            balance = amount;
        } else {
            throw new IllegalTransactionException("The user with ID "
                    + userID + " can't have negative balance");
        }
    }

    public String toString() {
        return userName + " with ID " + userID + " has balance " + balance;
    }

}