package zdk.JUC;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author zdkswd
 * @date 2019/10/9 19:06
 */
public class ForkJoinPoolTest {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSum(0L, 10000000L);
        Long sum = forkJoinPool.invoke(task);
        System.out.println("sum = " + sum);
    }
}
class ForkJoinSum extends RecursiveTask<Long> {
    private long start;
    private long end;

    private static final long GATE=8L;

    public ForkJoinSum(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length=end-start;
        if (length<= GATE){
            long sum=0L;
            for (long i = start; i <=end ; i++) {
                sum+=i;
            }
            return sum;
        }else {
            long middle=(start+end)/2;

            ForkJoinSum left = new ForkJoinSum(start, middle);
            left.fork();

            ForkJoinSum right=new ForkJoinSum(middle+1,end);
            right.fork();

            return left.join()+right.join();
        }


    }
}
