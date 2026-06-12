package Java.SyntaxTest;

import java.util.*;

public class QueueUsage {
    public static void main(String[] args) {
        // testLinkedList();
        testArrayDeque();
    }

    public static void testLinkedList() {
        Queue<String> q = new LinkedList<>();
        q.add("a");
        q.add("b");
        q.add("c");

        // Remove the head of the queue
        var head = q.remove();

        System.out.println("Head: " + head);

        // Peak the head of the queue
        System.out.println("Next head: " + q.peek()); // Empty queue returns null

        System.out.println("Next head: " + q.element()); // Empty queue throws NoSuchElementException
        System.out.println("Queue size: " + q.size());
    }

    public static void testArrayDeque() {
        Deque<Integer> dq = new ArrayDeque<>();

        dq.addLast(1);
        dq.addFirst(2);

        System.out.println("The head of deque is " + dq.getFirst());
        System.out.println("The tail of deque is " + dq.getLast());

        dq.pollFirst();
        System.out.println("The head of deque is " + dq.getFirst());

        System.out.println("The size of deque is " + dq.size());
    }

}
