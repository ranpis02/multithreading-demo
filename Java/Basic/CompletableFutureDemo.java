package Java.Basic;

import java.util.concurrent.CompletableFuture;

class CompletableFutureDemo {
    public static void main(String[] args) {
        // System.out.println("Hello CompletableFuture");
        // useCompletableFuture01();
        useCompletableFuture02();
    }

    public static void useCompletableFuture01() {

        // The runAsync method applies the given task without retunning a result
        CompletableFuture.runAsync(CompletableFutureDemo::printHello);

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(CompletableFutureDemo::getName);

        System.out.println("The main thread is doing some work...");

        try {
            System.out.println("The result from CompletableFuture is " + cf.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void useCompletableFuture02() {

        // The runAsync method applies the given task without retunning a result
        CompletableFuture.runAsync(CompletableFutureDemo::printHello);

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(CompletableFutureDemo::getName);

        // String res;

        CompletableFuture<Void> res = cf.thenApply(String::toUpperCase).thenAccept(x -> {
            System.out.println("The result from CompletableFuture is " + x);

        });

        System.out.println("The main thread is doing some work...");
        res.join();
    }

    public static void useCompletableFuture03() {
        
    }


    public static void printHello() {
        System.out.println("Hello, CompletableFuture!");
    }

    public static String getName() {
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "CompletableFuture";
    }


}