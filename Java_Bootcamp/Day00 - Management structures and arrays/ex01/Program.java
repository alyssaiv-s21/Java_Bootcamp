import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.err.println("Plese try again and enter an integer number");
            sc.close();
            System.exit(-1);
        }
        int number = sc.nextInt();
        sc.close();
        if (number < 2) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        boolean isPrime = true;
        if (number % 2 == 0 && number != 2) {
            isPrime = false;
        }
        int count = 1;
        for (int i = 3; i * i <= number && isPrime; ++i) {
            ++count;
            if (number % i == 0) {
                isPrime = false;
            }
        }
        System.out.println(isPrime + " " + count);
    }
}
