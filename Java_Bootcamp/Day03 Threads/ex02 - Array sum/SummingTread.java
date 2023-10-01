package ex02;

public class SummingTread extends Thread {
    private int[] randomArray;
    private int start;
    private int end;
    private int result = 0;

    public SummingTread(int[] randomArray, int start, int end) {
        this.randomArray = randomArray;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; ++i) {
            result += randomArray[i];
        }
        System.out.println("Thread " + Thread.currentThread().getName()
                + ": from " + start + " to " + (end - 1) + " sum is " + result);
    }

    public int getResult() {
        return result;
    }

}