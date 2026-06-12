package Java.SyntaxTest;

public class StringBuilderAndBuffer {
    private static final int LOOP_COUNT = 10000;

    private static final int THREAD_COUNT = 10;

    // @formatter:off
    /**
     * Append is not a thread-safe operation, it can be decomsed into four steps:
     * 1. Calculate the current length of the string:
     *    ```   
     *      int oldCount = count;
     *    ```
     * 2. Check if the current capacity is enough to hold the new string, if not then expand the capacity:
     *    ```
     *      ensureCapcity(oldCount + 1);
     * 3. Append the new string to the end of the current string:
     *   ```
     *    value[oldCount] = c;
     *   ```
     * 4. Update the length of the string;
     *   ```
     *      count = oldCount + 1;
     *   ```
     */
    // @formatter:on
    public static void main(String[] args) {
        testStringBuilder();
    }

    public static void testStringBuilder() {
        Thread[] threads = new Thread[THREAD_COUNT];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < LOOP_COUNT; j++) {
                    sb.append("a");
                }
            });

            threads[i].start();
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("StringBuilder length: " + sb.length());

        System.out.println("The expected length should be " + LOOP_COUNT * THREAD_COUNT);
    }
}
