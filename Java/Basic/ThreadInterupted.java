package Java.Basic;

public class ThreadInterupted {
    public static void main(String[] args) throws InterruptedException {
        simulateThreadInterruption();
    }

    public static void simulateThreadInterruption() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread is running...");
            }

            System.out.println("Thread is interrupted, and goes to terminated...");
        });

        t1.start();

        Thread.sleep(500);

        t1.interrupt();
    }

}