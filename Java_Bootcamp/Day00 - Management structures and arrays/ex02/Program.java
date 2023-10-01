import java.util.Scanner;

public class Program {
    public static final int END_SEQUENCE = 42;

    public static void main(String[] args) {
        int number = 0;
        int count = 0;
        Scanner sc = new Scanner(System.in);
        while (number != END_SEQUENCE) {
            number = sc.nextInt();
            if (isPrime(sumOfDigit(number))) {
                ++count;
            }
        }
        sc.close();
        System.out.println("Count of coffee-request - " + count);
    }

    public static boolean isPrime(int number) {
        boolean isPrime = true;
        if (number % 2 == 0 && number != 2) {
            isPrime = false;
        }
        for (int i = 3; i * i < number && isPrime; ++i) {
            if (number % i == 0) {
                isPrime = false;
            }
        }
        return isPrime;
    }

    public static int sumOfDigit(int number) {
        int sumOfDigit = 0;
        while (number != 0) {
            sumOfDigit += number % 10;
            number = number / 10;
        }
        return sumOfDigit;
    }
}