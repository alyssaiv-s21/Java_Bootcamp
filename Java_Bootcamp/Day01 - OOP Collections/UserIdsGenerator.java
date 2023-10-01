package ex05;

public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private static int userID = 1;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateId() {
        return userID++;
    }
}