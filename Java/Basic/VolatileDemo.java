package Java.Basic;

class VolatileDemo {
    static boolean running = true;

    static volatile boolean holding = true;

    public static void main(String[] args) {
        // scene01();
        scene02();
    }

    public static void scene01() {
        Thread t = new Thread(() -> {
            while (running) {

            }
            System.out.println("Thread stopped.");
        });

        t.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        running = false;
    }

    public static void scene02() {
        Thread t = new Thread(() -> {
            while (holding) {

            }
            System.out.println("Thread stopped.");
        });

        t.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holding = false;
    }
}