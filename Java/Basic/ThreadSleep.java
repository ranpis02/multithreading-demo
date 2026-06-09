package Java.Basic;

public class ThreadSleep {
    public static void main(String[] args) {
        System.out.println("The main thread starts...");

        Thread t = new Thread(() -> {
            System.out.println("The new thread starts...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("The new thread ends...");
        });

        t.start();

    }
}
