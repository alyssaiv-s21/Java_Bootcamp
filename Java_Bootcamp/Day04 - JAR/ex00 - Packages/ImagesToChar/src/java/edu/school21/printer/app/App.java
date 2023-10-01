package edu.school21.printer.app;

import java.io.IOException;

import edu.school21.printer.logic.Logic;

public class App {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("You should run program with three arguments"
                    + "- file path, white dot and black dot");
            System.exit(1);
        }

        Logic convector = new Logic(args[0]);
        try {
            int[][] result = convector.ImageToChar();
            for (int i = 0; i < result[0].length; ++i) {
                for (int j = 0; j < result.length; ++j) {
                    if (result[j][i] == 0) {
                        System.out.print(args[1]);
                    } else {
                        System.out.print(args[2]);
                    }
                }
                System.out.print("\n");
            }
        } catch (IOException ex) {
            System.out.println("The path to file is not correct");
        }
    }
}