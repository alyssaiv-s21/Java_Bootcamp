package ex00;

public class PrintWord implements Runnable {
    private String word;
    private int count;

    public PrintWord(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public void run() {
        for (int i = 0; i < count; ++i) {
            System.out.println(word);
        }
    }
}