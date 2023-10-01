package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] argv) {
        if (argv.length != 1 || !argv[0].startsWith("--current-folder=")) {
            System.out.println("You should specify folder '--current-folder='");
            System.exit(1);
        }
        String startFolder = argv[0].substring(17);
        Bash bash = new Bash(startFolder);
        System.out.println(startFolder);
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        while (!userInput.equals("exit")) {
            try {
                String[] command = userInput.split(" ");
                switch (command[0]) {
                    case ("mv"):
                        bash.mvCommand(command[1], command[2]);
                        break;
                    case ("ls"):
                        bash.lsCommand();
                        break;
                    case ("cd"):
                        bash.cdCommand(command[1]);
                        break;
                    default:
                        System.out.println(
                                "You can enter only four command: \n\tmv What Where\n\tls\n\tcd FolderName\n\texit");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            userInput = sc.nextLine();
        }
        sc.close();
    }
}
