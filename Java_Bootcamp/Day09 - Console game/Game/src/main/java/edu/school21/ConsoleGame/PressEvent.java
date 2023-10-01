package edu.school21.ConsoleGame;

import java.util.Scanner;

public class PressEvent {

    public static KeyPressed onePress(Scanner sc) {
        String input = sc.nextLine();
        if (!input.isEmpty()) {
            char c = input.charAt(0);
            // char c = sc.nextLine().charAt(0);
            switch (c) {
                case 'w':
                    return KeyPressed.UP;
                case 'a':
                    return KeyPressed.RIGHT;
                case 's':
                    return KeyPressed.DOWN;
                case 'd':
                    return KeyPressed.LEFT;
                case '9':
                    return KeyPressed.EXIT;
                case '8':
                    return KeyPressed.CONFIRM;
                default:
                    return KeyPressed.NOT_CORRECT;
            }

        }
        return KeyPressed.NOT_CORRECT;
    }
}
