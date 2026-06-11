package Java.Basic;

import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

public class FutureDemo {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);

        Callable<String> task = () -> {
            Thread.sleep(2000);

            return "Task-01";
        };

        Future<String> f = es.submit(task);

        String res = "No result yet";
        try {
            res = f.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        es.shutdown();

        System.out.println("The task execution result is " + res);
    }

}
