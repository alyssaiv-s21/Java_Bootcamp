package ex01;

class Producer extends Thread {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            buffer.produce();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
