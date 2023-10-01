package ex05;

import java.util.Scanner;

public class Menu {
    private String userChoice = "";
    private TransactionsService service = new TransactionsService();
    private boolean isDevMode = false;
    Scanner sc;

    public Menu(boolean devMode) {
        isDevMode = devMode;
        sc = new Scanner(System.in);
        Loop();
    }

    private void Loop() {
        while (!userChoice.equals("7")) {
            ShowMainMenu();
            UserMakeChoice();
        }
        sc.close();

    }

    private void ShowMainMenu() {
        System.out.println("\n\n");
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. DEV - remove a transfer by ID");
        System.out.println("6. DEV - check transfer validity");
        System.out.println("7. Finish execution");
    }

    private void UserMakeChoice() {
        userChoice = sc.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(userChoice);
        } catch (Exception ex) {
            System.out.println("Please enter only integer number from 1 to 7");
        }
        try {
            StartAction(choice);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void StartAction(int choice) {
        switch (choice) {
            case (1):
                addUser();
                break;
            case (2):
                getUserBalance();
                break;
            case (3):
                perfomTransfer();
                break;
            case (4):
                viewUserTr();
                break;
            case (5):
                if (isDevMode) {
                    removeTr();
                } else {
                    System.out.println("You can do this only in dev mode");
                }
                break;
            case (6):
                if (isDevMode) {
                    checkValidity();
                } else {
                    System.out.println("You can do this only in dev mode");
                }
                break;
            case (7):
                break;
            default:
                System.out.println("The nubmer should be from 1 to 7");
        }
    }

    private void addUser() {
        System.out.println("Please enter a user name and a start balance: ");
        String userInput = sc.nextLine();
        String[] newUser = userInput.split(" ");
        int balance = Integer.parseInt(newUser[1]);
        service.addUser(newUser[0], balance);
    }

    private void getUserBalance() {
        System.out.println("Enter a user ID: ");
        String userInput = sc.nextLine();
        int userID = Integer.parseInt(userInput);
        System.out.println(service.getUserName(userID)
                + " - " + service.getUserBalance(userID));
    }

    private void perfomTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String userInput = sc.nextLine();
        String[] input = userInput.split(" ");
        int firstID = Integer.parseInt(input[0]);
        int secondID = Integer.parseInt(input[1]);
        int sum = Integer.parseInt(input[2]);
        service.addTransaction(firstID, secondID, sum);
        System.out.println("The transfer is completed");
    }

    private void viewUserTr() {
        System.out.println("Enter a user ID");
        String userInput = sc.nextLine();
        int userID = Integer.parseInt(userInput);
        Transaction[] userTr = service.getUserTransaction(userID);
        for (Transaction tr : userTr) {
            System.out.println(tr);
        }
    }

    private void removeTr() {
        System.out.println("Enter a user ID and a transfer ID");
        String userInput = sc.nextLine();
        String[] input = userInput.split(" ");
        int userID = Integer.parseInt(input[0]);
        service.removeTransaction(input[1], userID);
        System.out.println("The transaction was removed: User ID "
                + userID + " transaction ID " + input[1]);
    }

    private void checkValidity() {
        Transaction[] result = service.checkUnvalidityTransaction();
        if (result.length > 0) {
            System.out.println("The next transactions are not valid: ");
            for (Transaction tr : result) {
                System.out.println(tr);
            }
        } else {
            System.out.println("You don't have unvalid transaction");
        }
    }
}