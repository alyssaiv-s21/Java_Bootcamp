package ex05;

interface TransactionsList {
    public void addTransaction(String id, User send, User get, int amount);

    public void removeTransaction(String id);

    public Transaction[] toArray();
}