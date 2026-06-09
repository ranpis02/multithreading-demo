package Java.Basic;

public class MyThread {
    public static void main(String[] args) {
        // 1. Create a new thread directly by instantiating the Thread class
        Thread t = new Thread();
        // Start the thread
        t.start();

        new MyNewThread01().start();

        // new MyNewThread02().start();

        new Thread(new MyNewTask()).start();

        // 4. Use lambda expression to implement the functional interface Runnable
        new Thread(() -> {
            System.out.println("Start a new thread 03...");
        }).start();
    }

}

// 2. Create a new thread by extending the Thread class
class MyNewThread01 extends Thread {
    @Override
    public void run() {
        System.out.println("Start a new thread 01...");
    }
}

// 3. Create a new task by implementing the Runnable interface, and then pass
// the task to a Thread object
class MyNewTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Start a new thread 02...");
    }
}
