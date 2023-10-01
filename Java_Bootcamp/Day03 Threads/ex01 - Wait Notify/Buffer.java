package ex01;

class Buffer {
    private boolean isHenPrinted;
    private int count;

    public Buffer(int number) {
        isHenPrinted = true;
        count = number;
    }

    public synchronized void produce() throws InterruptedException {
        for (int i = 0; i < count; ++i) {
            if (!isHenPrinted) {
                wait();
            }
            System.out.println("Egg");
            isHenPrinted = false;
            notify();
        }
    }

    public synchronized void consume() throws InterruptedException {
        for (int i = 0; i < count; ++i) {
            if (isHenPrinted) {
                wait();
            }
            System.out.println("Hen");
            isHenPrinted = true;
            notify();
        }
    }
}