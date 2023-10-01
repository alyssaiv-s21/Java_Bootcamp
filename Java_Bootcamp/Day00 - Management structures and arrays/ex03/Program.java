import java.util.Scanner;

public class Program {
    public static final String END_SEQUENCE = "42";
    public static final int MAX_GRADE = 9;
    public static final int MAX_WEEK = 18;
    public static final int NUMBER_OF_TESTS = 5;

    public static void main(String[] args) {
        long number = 0;
        int countWeeks = 1;
        long tenPow = 1;
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        while (!userInput.equals(END_SEQUENCE) && countWeeks <= MAX_WEEK) {
            int weekNumber = Integer.parseInt(userInput.split(" ")[1]);
            if (weekNumber != countWeeks) {
                System.err.println("IllegalArgument");
                sc.close();
                System.exit(-1);
            }
            int lowestGrade = MAX_GRADE;
            for (int i = 0; i < NUMBER_OF_TESTS; ++i) {
                int grade = sc.nextInt();
                if (grade < lowestGrade) {
                    lowestGrade = grade;
                }
            }
            sc.nextLine();
            number += lowestGrade * tenPow;
            ++countWeeks;
            tenPow *= 10;
            userInput = sc.nextLine();
        }
        sc.close();
        System.out.println(number);
        printStatistics(number);
    }

    public static void printStatistics(long number) {
        int count = 1;
        while (number != 0) {
            long minGrade = number % 10;
            number = number / 10;
            System.out.print("Week " + count + " ");
            for (int i = 0; i < minGrade; ++i) {
                System.out.print("=");
            }
            System.out.println(">");
            ++count;
        }
    }
}