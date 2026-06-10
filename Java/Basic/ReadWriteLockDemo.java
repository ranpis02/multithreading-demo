package Java.Basic;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();

        Runnable readTask = () -> {
            int val = dataStore.read();

            System.out.println("Reader 1 read: " + val);
        };

        Runnable writeTask = () -> {
            int val = (int) (Math.random() * 100);
            dataStore.write(val);
        };

        Thread reader1 = new Thread(readTask, "reader-1");
        Thread reader2 = new Thread(readTask, "reader-2");

        Thread writer1 = new Thread(writeTask, "writer-1");

        reader1.start();

        writer1.start();

        reader2.start();
    }

}

class DataStore {
    private int data = 0;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final Lock rLock = rwLock.readLock();

    private final Lock wLock = rwLock.writeLock();

    public int read() {
        rLock.lock();

        try {
            return data;
        } finally {
            rLock.unlock();
        }
    }

    public void write(int value) {
        wLock.lock();

        try {
            data = value;
        } finally {
            wLock.unlock();
        }
    }
}