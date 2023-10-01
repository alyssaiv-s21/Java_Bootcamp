package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {
    NumberWorker nw;
    @BeforeEach
    void initNumberWorker() {
        nw = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 149, 163, 101})
    void isPrimeForPrimes(int number) {
        assertTrue(nw.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 15, 100, 125})
    void isPrimeForNotPrimes(int number) {
        assertFalse(nw.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -15, -7})
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> nw.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void sumOfDigitCorrectNumbers(int input, int expected) {
        int actualResult = nw.sumOfDigit(input);
        assertEquals(expected, actualResult);
    }
}
