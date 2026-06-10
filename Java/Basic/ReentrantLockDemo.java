package Java.Basic;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.*;

public class ReentrantLockDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        lock.lock(); // the first time to acquire the lock
        lock.lock(); // the second time to acquire the lock

        // Get the hold count of the lock
        int holdCount = lock.getHoldCount();
        System.out.println("Hold count: " + holdCount);

        lock.unlock();
        lock.unlock();

        // ReentrantLockTest re = new ReentrantLockTest();
        // re.add(-10);

        // System.out.println("The count is: " + re.count);
    }

}

class ReentrantLockTest {
    public int count = 0;

    public synchronized void add(int n) {
        if (n < 0) {
            dec(n);
        } else {
            count += n;
        }

    }

    public synchronized void dec(int n) {
        count -= n;
    }

}

class ReentrantLockAndCondition {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void produceTask(String task) {
        lock.lock();

        try {
            queue.add(task);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String consumeTask() {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                condition.await();
            }

            return queue.remove();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }
}