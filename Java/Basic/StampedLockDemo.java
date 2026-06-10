package Java.Basic;

import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    public static void main(String[] args) {
        Point p = new Point();
        p.move(3, 4);

        System.out.println("Distance from origin: " + p.distance());
        System.out.println("Distance from origin: " + p.distance());

    }

}

class Point {
    private double x = 0;

    private double y = 0;
    private final StampedLock lock = new StampedLock();

    public void move(double dx, double dy) {
        long stamp = lock.writeLock();

        try {
            x += dx;
            y += dy;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public double distance() {
        long stamp = lock.tryOptimisticRead();

        double currentX = x;
        double currentY = y;

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();

            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }

        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
