package Java.Basic;

import java.util.concurrent.*;

class ThreadPoolDemo {
    public static void main(String[] args) {
        testFixedThreadPool();
        // testCachedThreadPool();
    }

    public static void testFixedThreadPool() {
        ExecutorService es = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            es.execute(new Task("Task-" + i));
        }

        es.shutdown();
    }

    public static void testCachedThreadPool() {
        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            es.execute(new Task("Task-" + i));
        }

        es.shutdown();
    }

    public static void testScheduledThreadPool() {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(3);

        System.out.println("Scheduling tasks to run after a dealy...");

        es.schedule(new Task("Task-1"), 5, TimeUnit.SECONDS);
    }

}

class Task implements Runnable {
    /**
     * Modifier and type of the variable name are final, which means that the value
     * of name cannot be changed after it is initialized in the constructor.
     */
    private final String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Task " + name + " is running...");

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Task " + name + " is completed.");
    }
}