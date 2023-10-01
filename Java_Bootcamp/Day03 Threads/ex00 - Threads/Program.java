package ex00;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("You should specify count number '--count='");
            System.exit(1);
        }
        int count = Integer.parseInt(args[0].substring(8));
        PrintWord egg = new PrintWord("Egg", count);
        PrintWord hen = new PrintWord("Hen", count);

        Thread threadEgg = new Thread(egg);
        Thread threadHen = new Thread(hen);
        threadEgg.start();
        threadHen.start();
        threadEgg.join();
        threadHen.join();
        for (int i = 0; i < count; ++i) {
            System.out.println("Human");
        }
    }
}