package Java.Basic;

public class ThreadState {
    private static final Object lock = new Object();

    // @formatter:off
    /**
     * Total state of a thread:
     *
     * +-------------+
     * |     NEW     |
     * +-------------+
     *        |
     *        v
     * +-------------------------------------------------+
     * |                      ALIVE                      |
     * |                                                 |
     * |  +-------------+    +-------------+             |
     * |  |  RUNNABLE   |<-->|   BLOCKED   |             |
     * |  +-------------+    +-------------+             |
     * |        ^                                        |
     * |        |                                        |
     * |  +-------------+    +---------------+           |
     * |  |   WAITING   |    | TIMED_WAITING |           |
     * |  +-------------+    +---------------+           |
     * |        ^                    ^                   |
     * |        |                    |                   |
     * +--------+--------------------+-------------------+
     *        |
     *        v
     * +-------------+
     * | TERMINATED  |
     * +-------------+
     */
    // @formatter:on
    public static void main(String[] args) throws InterruptedException {
        // simulateWaitingState();
        simulateTimedWaitingState();
    }

    public static void simulateBlockedState() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 1 acquires the lock, and goes to running... ");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("The thread 1 releases the lock, and goes to terminated...");
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 2 is waiting to acquire the lock, and goes to waiting... ");
            }
        });

        t1.start();
        Thread.sleep(100);
        t2.start();

        Thread.sleep(500);
        System.out.println("The state of thread 2: " + t2.getState());
    }

    public static void simulateWaitingState() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Thread 1 acquires the lock, and goes to running... ");
                    // The thread starts waiting
                    lock.wait();
                    System.out.println("Thread 1 is notified, and goes to running...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start thread 1
        t1.start();
        Thread.sleep(100);

        // Get the state of thread 1
        System.out.println("The state of thread 1: " + t1.getState()); // WAITING

        // Notify thread 1
        synchronized (lock) {
            lock.notify();
        }

        // Wait for acquiring the lock
        System.out.println("The state of thread 1: " + t1.getState()); // BLOCKED

        t1.join();

        System.out.println("The state of thread 1: " + t1.getState()); // TERMINATED
    }

    public static void simulateTimedWaitingState() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 starts running...");

            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread 1 ends...");
        });

        // Make sure thread 1 leaves the NEW state
        t1.start();
        Thread.sleep(100);

        System.out.println("The state of thread 1: " + t1.getState());
    }
}
