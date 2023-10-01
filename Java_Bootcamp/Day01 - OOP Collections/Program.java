package ex05;

public class Program {
    public static void main(String[] argv) {
        if (argv.length != 1) {
            System.out.println("You need to run app with a '--profile=' flag");
        } else if (argv[0].equals("--profile=dev")) {
            Menu menu = new Menu(true);
        } else if (argv[0].equals("--profile=prod")) {
            Menu menu = new Menu(false);
        } else {
            System.out.println("There is only two possible option - --profile=dev or --profile=prod");
        }
    }
}