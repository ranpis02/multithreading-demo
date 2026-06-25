package Java.SyntaxTest;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.*;

public class ConsumerUsage {
    public static void main(String[] args) {
        printTest();
    }

    private static void consumerTest() {
        Consumer<Integer> con = x -> System.out.println(x * 2);

        con.accept(5);

        IntConsumer printNumber = x -> System.out.println(x * 2);

        printNumber.accept(2);
    }

    private static void printTest() {
        Consumer<Integer> con = x -> System.out.println(x * 2);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.forEach(con);
    }
}
