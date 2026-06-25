package Java.Leetcode;

import java.util.function.IntConsumer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class PrintZeroEvenOdd {
    public static void main(String[] args) {
        ZeroEvenOddSync zeroEvenOdd = new ZeroEvenOddSync(5);

        Thread t1 = new Thread(() -> {
            try {
                zeroEvenOdd.zero(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                zeroEvenOdd.even(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                zeroEvenOdd.odd(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}

class ZeroEvenOddSync {
    private int n;

    private int state = 0;

    public ZeroEvenOddSync(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            synchronized (this) {
                while (state != 0) {
                    this.wait();
                }

                printNumber.accept(0);

                state = i % 2 == 1 ? 1 : 2;
                this.notifyAll();
            }
        }

    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            synchronized (this) {
                while (state != 1) {
                    this.wait();
                }

                printNumber.accept(i);

                state = 0;

                this.notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            synchronized (this) {
                while (state != 2) {
                    this.wait();
                }

                printNumber.accept(i);

                state = 0;

                this.notifyAll();
            }
        }
    }
}

class ZeroEvenOddSema {
    private int n;

    // private volatile int state = 0;
    private Semaphore zeroSema = new Semaphore(1);

    private Semaphore oddSema = new Semaphore(0);

    private Semaphore evenSema = new Semaphore(0);

    public ZeroEvenOddSema(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            synchronized (this) {
                zeroSema.acquire();

                printNumber.accept(0);

                // state = i % 2 == 1 ? 1 : 2;
                if (i % 2 == 1) {
                    oddSema.release();
                } else {
                    evenSema.release();
                }
            }
        }

    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSema.acquire();

            printNumber.accept(i);

            zeroSema.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSema.acquire();

            printNumber.accept(i);

            zeroSema.release();
        }
    }
}

class ZeroEvenOddReentrance {

    private int n;

    private int state = 0;

    ReentrantLock lock = new ReentrantLock();

    Condition zero = lock.newCondition();

    Condition odd = lock.newCondition();

    Condition even = lock.newCondition();

    public ZeroEvenOddReentrance(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            try {
                lock.lock();

                while (state != 0) {
                    zero.await();
                }

                printNumber.accept(0);

                if (i % 2 == 1) {
                    state = 1;
                    odd.signal();
                } else {
                    state = 2;
                    even.signal();
                }
            } finally {
                lock.unlock();

            }

        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            try {
                lock.lock();

                while (state != 2) {
                    even.await();
                }

                printNumber.accept(i);

                state = 0;

                zero.signal();
            } finally {
                lock.unlock();
            }

        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            try {
                lock.lock();

                while (state != 1) {
                    odd.await();
                }

                printNumber.accept(i);

                state = 0;

                zero.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}