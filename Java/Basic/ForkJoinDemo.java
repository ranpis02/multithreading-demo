package Java.Basic;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        SumTask sumTask = new SumTask(1, 100000);

        Long res = pool.invoke(sumTask);

        System.out.println("The sum from 1 to 100000 is " + res);

    }

}

class SumTask extends RecursiveTask<Long> {
    private long start;
    private long end;

    // Control the number of task objects created
    private static final long THREADHOLD = 10;

    public SumTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    protected Long compute() {
        if (end - start <= THREADHOLD) {
            long sum = 0;

            for (long i = start; i <= end; i++) {
                sum += i;
            }

            return sum;
        }

        long middle = (start + end) / 2;

        SumTask left = new SumTask(start, middle);
        SumTask right = new SumTask(middle + 1, end);

        left.fork();
        // right.fork();

        long rightRes = right.compute();
        long leftRes = left.join();

        return rightRes + leftRes;
    }
}
