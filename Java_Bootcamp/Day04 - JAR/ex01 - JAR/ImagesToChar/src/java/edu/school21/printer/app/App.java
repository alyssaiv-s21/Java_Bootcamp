package edu.school21.printer.app;

import java.io.IOException;

import edu.school21.printer.logic.Logic;

public class App {
    public static final String IMAGE_PATH = "target/resources/it.bmp";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("You should run program with two arguments"
                    + "- white dot and black dot");
            System.exit(1);
        }

        Logic convector = new Logic(IMAGE_PATH);
        try {
            int[][] result = convector.ImageToChar();
            for (int i = 0; i < result[0].length; ++i) {
                for (int j = 0; j < result.length; ++j) {
                    if (result[j][i] == 0) {
                        System.out.print(args[0]);
                    } else {
                        System.out.print(args[1]);
                    }
                }
                System.out.print("\n");
            }
        } catch (IOException ex) {
            System.out.println("The path to file is not correct");
        }
    }
}