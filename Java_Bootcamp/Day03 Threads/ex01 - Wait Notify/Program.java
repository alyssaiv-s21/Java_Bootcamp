package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("You should specify count number '--count='");
            System.exit(1);
        }
        int number = Integer.parseInt(args[0].substring(8));
        Buffer buffer = new Buffer(number);
        Producer p = new Producer(buffer);
        Consumer c = new Consumer(buffer);

        p.start();
        c.start();
    }
}