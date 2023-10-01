public class Program {
    public static void main(String args[]) {
        int number = 479598;
        int sumOfDigit = number % 10 + number / 10 % 10 + number / 100 % 10
               + number / 1000 % 10 + number / 10000 % 10 + number / 100000;
        System.out.println(sumOfDigit);
    }
}