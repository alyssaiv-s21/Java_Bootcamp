package edu.school21.app;

import edu.school21.preprocessor.PreProcessor;
import edu.school21.preprocessor.PreProcessorToLowerImpl;
import edu.school21.preprocessor.PreProcessorToUpperImpl;
import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithDateTimeImpl;
import edu.school21.printer.PrinterWithPrefixImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererErrImpl;
import edu.school21.renderer.RendererStandardImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix");
        printer.print("Hello!");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer springPrinter = context.getBean("printerPrefixErrUpper", Printer.class);
        springPrinter.print("Hello!");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n\n\nall other spring beans:");
        springPrinter = context.getBean("printerDateStdUpper", Printer.class);
        springPrinter.print("Hello!");
        springPrinter = context.getBean("printerPrefixStdLower", Printer.class);
        springPrinter.print("Hello!");
        springPrinter = context.getBean("printerDateStdLower", Printer.class);
        springPrinter.print("Hello!");
        springPrinter = context.getBean("printerPrefixStdUpper", Printer.class);
        springPrinter.print("Hello!");
        springPrinter = context.getBean("printerDateErrUpper", Printer.class);
        springPrinter.print("Hello!");
        springPrinter = context.getBean("printerPrefixErrLower", Printer.class);
        springPrinter.print("Hello!");
        springPrinter = context.getBean("printerDateErrLower", Printer.class);
        springPrinter.print("Hello!");
    }
}