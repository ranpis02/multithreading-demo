package Java.Leetcode;

import java.util.concurrent.*;

public class PrintH2O {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        String water = "OOHHHO";

        H2O h2o = new H2O();

        for (char c : water.toCharArray()) {
            if (c == 'H') {
                pool.execute(() -> {
                    try {
                        h2o.hydrogen(() -> System.out.print("H"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

            if (c == 'O') {
                pool.execute(() -> {
                    try {
                        h2o.oxygen(() -> System.out.print("O"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        pool.shutdown();
        try {
            pool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class H2O {

    private Semaphore hydrogenSema = new Semaphore(2);
    private Semaphore oxygenSema = new Semaphore(0);

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hydrogenSema.acquire();
        releaseHydrogen.run();
        oxygenSema.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygenSema.acquire(2);
        releaseOxygen.run();
        hydrogenSema.release(2);
    }
}