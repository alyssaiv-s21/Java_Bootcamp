package ex05;

import java.util.UUID;

public class TransactionsService {
    private UsersArrayList userList = new UsersArrayList();
    private TransactionsLinkedList transactionList = new TransactionsLinkedList();

    public void addUser(String name, int balance) {
        User user = new User(name, balance);
        userList.addUser(user);
    }

    public int getUserBalance(int userID) {
        for (int i = 0; i < userList.getUserNumber(); ++i) {
            if (userList.getUserByIndex(i).getUserID() == userID) {
                return userList.getUserByIndex(i).getBalance();
            }
        }
        throw new UserNotFoundException("There is no user with id " + userID);
    }

    public String getUserName(int userID) {
        for (int i = 0; i < userList.getUserNumber(); ++i) {
            if (userList.getUserByIndex(i).getUserID() == userID) {
                return userList.getUserByIndex(i).getUserName();
            }
        }
        throw new UserNotFoundException("There is no user with id " + userID);
    }

    public void addTransaction(int send, int get, int sum) {
        String trID = UUID.randomUUID().toString();
        User first = userList.getUserById(send);
        User second = userList.getUserById(get);
        if (sum >= 0) {
            transactionList.addTransaction(trID, first, second, -sum);
            transactionList.addTransaction(trID, second, first, sum);
        } else {
            transactionList.addTransaction(trID, first, second, sum);
            transactionList.addTransaction(trID, second, first, -sum);
        }
    }

    public Transaction[] getUserTransaction(int userID) {
        int size = transactionList.getUserTransactionsSize(userID);
        Transaction[] userTr = new Transaction[size];
        Transaction[] allTr = transactionList.toArray();
        int count = 0;
        for (Transaction tr : allTr) {
            if (tr.getRecipient().getUserID() == userID) {
                userTr[count] = tr;
                ++count;
            }
        }
        return userTr;
    }

    public void removeTransaction(String transactionID, int userID) {
        Transaction[] allArray = transactionList.toArray();
        for (Transaction tr : allArray) {
            if (tr.getTransactionID().equals(transactionID) && tr.getRecipient().getUserID() == userID) {
                transactionList.removeTransaction(tr);
            }
        }
    }

    public Transaction[] checkUnvalidityTransaction() {
        return transactionList.checkUnvalidityTransaction();
    }

    public void printTransactionList() {
        transactionList.printTransactionList();
    }

    public void printUsersArray() {
        userList.printUsersArray();
    }
}