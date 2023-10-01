package ex05;

public class TransactionsLinkedList implements TransactionsList {

    private int size = 0;
    private Transaction first = null;
    private Transaction last = null;

    public TransactionsLinkedList() {
    }

    @Override
    public void addTransaction(String id, User first, User second, int amount) {
        if (amount >= 0) {
            Transaction tr1 = new Transaction(id, first, second, TransactionType.INCOME, amount);
            insertTransaction(tr1);
        } else {
            Transaction tr2 = new Transaction(id, first, second, TransactionType.OUTCOME, amount);
            insertTransaction(tr2);
        }
    }

    public void addTransaction(Transaction tr) {
        insertTransaction(tr);
    }

    @Override
    public void removeTransaction(String id) {
        Transaction toDelete = null;
        Transaction iterator = first;
        for (int i = 0; i < size; ++i) {
            if (iterator.getTransactionID().equals(id)) {
                toDelete = iterator;
                break;
            }
            iterator = iterator.next;
        }
        if (toDelete != null) {
            eraseTransation(toDelete);
        } else {
            throw new TransactionNotFoundException("There is no transaction with id " + id);
        }
    }

    public void removeTransaction(Transaction tr) {
        eraseTransation(tr);
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] result = new Transaction[size];
        Transaction iterator = first;
        for (int i = 0; i < size; ++i) {
            result[i] = iterator;
            iterator = iterator.next;
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    public int getUserTransactionsSize(int userID) {
        int count = 0;
        Transaction iterator = first;
        for (int i = 0; i < size; ++i) {
            if (iterator.getRecipient().getUserID() == userID) {
                ++count;
            }
            iterator = iterator.next;
        }
        if (count > 0) {
            return count;
        } else {
            throw new UserNotFoundException("There is no transaction with user with id " + userID);
        }

    }

    public Transaction[] checkUnvalidityTransaction() {
        TransactionsLinkedList result = new TransactionsLinkedList();
        Transaction iterator = first;
        for (int i = 0; i < size - 1; ++i) {
            if (iterator.getTransactionID().equals(iterator.next.getTransactionID())) {
                ++i;
                iterator = iterator.next.next;
            } else {
                result.addTransaction(new Transaction(iterator));
                iterator = iterator.next;
            }
        }
        if (iterator != null) {
            result.addTransaction(new Transaction(iterator));
        }
        return result.toArray();
    }

    public void printTransactionList() {
        System.out.println("The size of the transaction list is " + size);
        Transaction iterator = first;
        for (int i = 0; i < size; ++i) {
            System.out.println(iterator);
            iterator = iterator.next;
        }
    }

    private void insertTransaction(Transaction data) {
        if (size == 0) {
            first = last = data;
        } else {
            last.next = data;
            data.prev = last;
            last = data;
        }
        ++size;
    }

    private void eraseTransation(Transaction data) {
        if (size == 1) {
            first = last = null;
        } else if (data == first) {
            first = first.next;
            first.prev = null;
        } else if (data == last) {
            last = last.prev;
            last.next = null;
        } else {
            data.next.prev = data.prev;
            data.prev.next = data.next;
        }
        --size;
    }

}