package Java.Basic;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            new Thread(() -> {
                System.out.println("The task " + taskId + " is completed");
                latch.countDown(); // When count reaches zero, the waiting thread will be released
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All tasks are completed");

    }
}
