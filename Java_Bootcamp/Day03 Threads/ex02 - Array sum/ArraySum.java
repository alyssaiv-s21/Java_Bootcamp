package ex02;

import java.util.ArrayList;

public class ArraySum {
    private int arraySize;
    private int threadsCount;
    private int[] randomArray;

    public ArraySum(int arraySize, int threadsCount) {
        this.arraySize = arraySize;
        this.threadsCount = threadsCount;
        randomArray = new int[arraySize];
        fillArray();
    }

    private void fillArray() {
        for (int i = 0; i < arraySize; ++i) {
            randomArray[i] = ((int) (Math.random() * 2001)) - 1000;
        }
    }

    public int SumOneThread() {
        int sumOneTread = 0;
        for (int i = 0; i < arraySize; ++i) {
            sumOneTread += randomArray[i];
        }
        return sumOneTread;
    }

    public int SumManyThreads() throws InterruptedException {
        int threadSize = arraySize / threadsCount;
        ArrayList<SummingTread> listOfThreads = new ArrayList<SummingTread>(threadsCount);
        for (int i = 0; i < threadsCount; ++i) {
            SummingTread newThread;
            if (i == threadsCount - 1) {
                newThread = new SummingTread(randomArray, i * threadSize, arraySize);
            } else {
                newThread = new SummingTread(randomArray, i * threadSize, i * threadSize + threadSize);
            }
            newThread.setName(Integer.toString(i));
            listOfThreads.add(newThread);
            newThread.start();
        }
        for (int i = 0; i < threadsCount; ++i) {
            listOfThreads.get(i).join();
        }
        int sumManyThreads = 0;
        for (int i = 0; i < threadsCount; ++i) {
            sumManyThreads += listOfThreads.get(i).getResult();
        }
        return sumManyThreads;
    }

}