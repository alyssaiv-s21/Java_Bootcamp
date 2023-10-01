package ex01;

public class Program {

    public static void main(String[] argv) {
        if (argv.length != 2) {
            System.out.println("Please start Program with two files in command-line arguments");
            System.exit(1);
        }
        Similarity words = new Similarity();
        double res = words.calculateSimilarity(argv[0], argv[1]);
        System.out.printf("Similarity = %.2f\n", res);
    }
}
