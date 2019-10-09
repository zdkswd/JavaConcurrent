package zdk.JUC;

import java.util.concurrent.*;

/**
 * @author zdkswd
 * @date 2019/10/9 18:54
 */
public class ScheduledThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        ScheduledFuture<Integer> future = pool.schedule(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        }, 3, TimeUnit.SECONDS);
        System.out.println(future.get());
        pool.shutdown();
    }
}
