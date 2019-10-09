package zdk.JUC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zdkswd
 * @date 2019/10/9 18:04
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        Task task = new Task();
        List<Future<Integer>> list=new ArrayList<>();

        //2.为线程池中的线程分配任务
        for (int i = 0; i < 10; i++) {
            pool.submit(task);
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return 1;
                }
            });
            list.add(future);
        }

        //3.关闭线程池
        //此种为温柔的关闭，即等待线程任务完毕后关闭
        pool.shutdown();

        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }

    }
}
class Task implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}


