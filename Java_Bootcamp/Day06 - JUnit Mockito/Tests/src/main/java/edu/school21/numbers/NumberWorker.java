package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;

public class NumberWorker {

    public boolean isPrime(int number) {
        if(number < 2) {
            throw new IllegalNumberException("The number cant be less than 2");
        }
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

    public int sumOfDigit(int number) {
        if(number < 0) {
            number = -number;
        }
        int sumOfDigit = 0;
        while (number != 0) {
            sumOfDigit += number % 10;
            number = number / 10;
        }
        return sumOfDigit;
    }
}