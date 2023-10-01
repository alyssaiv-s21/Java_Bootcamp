package ex02;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("You should run program with two arguments");
            System.exit(1);
        } else if (!args[0].startsWith("--arraySize=")) {
            System.out.println("The first argument should be '--arraySize='");
            System.exit(1);
        } else if (!args[1].startsWith("--threadsCount=")) {
            System.out.println("The first argument should be '--threadsCount='");
            System.exit(1);
        }

        int arraySize = Integer.parseInt(args[0].substring(12));
        int threadsCount = Integer.parseInt(args[1].substring(15));
        if (arraySize < 1 || threadsCount < 1) {
            System.out.println("The size and treads count should be more than zero");
            System.exit(1);
        } else if (arraySize > 2000000) {
            arraySize = 2000000;
        } else if (arraySize < threadsCount) {
            threadsCount = arraySize;
        }

        ArraySum newArray = new ArraySum(arraySize, threadsCount);
        int oneThread = newArray.SumOneThread();
        System.out.println("Sum: " + oneThread);
        try {
            int sumManyThreads = newArray.SumManyThreads();
            System.out.println("Sum by threads: " + sumManyThreads);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}