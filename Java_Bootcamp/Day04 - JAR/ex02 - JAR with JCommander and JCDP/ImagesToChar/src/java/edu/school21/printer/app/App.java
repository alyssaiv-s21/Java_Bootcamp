package edu.school21.printer.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.ConstructorParameters;

import edu.school21.printer.logic.Logic;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.BColor;

@Parameters(separators = "=")
public class App {
    public static final String IMAGE_PATH = "resources/it.bmp";

    @Parameter(names = "--white", required = true)
    private String white;

    @Parameter(names = "--black", required = true)
    private String black;

    public static void main(String[] args) {
        App param = new App();
        JCommander.newBuilder()
                .addObject(param)
                .build()
                .parse(args);

        ColoredPrinter whiteDot = new ColoredPrinter.Builder(1, false)
                .background(BColor.valueOf(param.white.toUpperCase())).build();

        ColoredPrinter blackDot = new ColoredPrinter.Builder(1, false)
                .background(BColor.valueOf(param.black.toUpperCase())).build();

        Logic convector = new Logic(IMAGE_PATH);
        try {
            int[][] result = convector.ImageToChar();
            for (int i = 0; i < result[0].length; ++i) {
                for (int j = 0; j < result.length; ++j) {
                    if (result[j][i] == 0) {
                        whiteDot.print(" ");
                    } else {
                        blackDot.print(" ");
                    }
                }
                System.out.print("\n");
            }
        } catch (IOException ex) {
            System.out.println("The path to file is not correct");
        }
    }
}