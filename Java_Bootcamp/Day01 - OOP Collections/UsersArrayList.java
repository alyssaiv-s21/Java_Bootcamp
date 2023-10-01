package ex05;

public class UsersArrayList implements UsersList {
    private int size;
    private int capacity = 10;
    private User[] usersArray = new User[capacity];

    public void addUser(User user) {
        if (capacity <= size) {
            User[] temp = new User[capacity * 2];
            for (int i = 0; i < size; ++i) {
                temp[i] = usersArray[i];
            }
            usersArray = temp;
            capacity *= 2;
        }
        usersArray[size] = user;
        size++;
    }

    public User getUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < size; ++i) {
            if (usersArray[i].getUserID() == id) {
                return usersArray[i];
            }
        }
        throw new UserNotFoundException("There is no user with id " + id);
    }

    public User getUserByIndex(int index) throws UserNotFoundException {
        if (index > size || index < 0) {
            throw new UserNotFoundException("There is no user with index " + index);
        }
        return usersArray[index];
    }

    public int getUserNumber() {
        return size;
    }

    public void printUsersArray() {
        for (int i = 0; i < size; ++i) {
            System.out.println(usersArray[i]);
        }
    }
}