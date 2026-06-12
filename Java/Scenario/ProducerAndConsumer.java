package Java.Scenario;

public class ProducerAndConsumer {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sharedResource.produce(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sharedResource.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

class SharedResource {
    private int data;
    private boolean hasData = false;

    public synchronized void produce(int value) throws InterruptedException {
        while (hasData) {
            wait(); // The data is already produced, wait for the consumer to consume it
        }

        data = value;
        hasData = true;
        System.out.println("Produced: " + data);
        notify(); // wake up the consumer
    }

    public synchronized int consume() throws InterruptedException {
        while (!hasData) {
            wait();
        }

        hasData = false;
        System.out.println("Consumed: " + data);
        notify(); // wake up the producer

        return data;
    }

}
