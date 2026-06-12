package Java.Basic;

import java.util.concurrent.*;

/**
 * Semaphore is a synchronization aid that restricts access to a shared resource
 * by multiple threads.
 */
public class SemaphoreDemo {
    static Semaphore toilet = new Semaphore(2);

    public static void main(String[] args) {
        for (int i = 0; i <= 5; i++) {
            int id = i;

            new Thread(() -> {
                System.out.println("Thread-" + id + " is waiting to use the toilet.");

                try {
                    toilet.acquire();

                    System.out.println("Thread-" + id + " is using the toilet.");

                    Thread.sleep(3000);

                    System.out.println("Thread-" + id + " is leaving the toilet.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    toilet.release();
                }

            }).start();
        }
    }

}
