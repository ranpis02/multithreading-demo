package Java.Basic;

import java.util.concurrent.*;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        new ThreadLocalExample().use();
    }

}

class ThreadLocalExample {
    private static ThreadLocal<String> name = new ThreadLocal<>();

    public void use() {
        Thread t1 = new Thread(() -> {
            name.set("Alice");

            System.out.println(Thread.currentThread().getName() + ": " + name.get());

        });

        Thread t2 = new Thread(() -> {
            name.set("Bob");

            System.out.println(Thread.currentThread().getName() + ": " + name.get());
        });

        t1.start();
        t2.start();
    }
}
