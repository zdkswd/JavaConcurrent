package zdk.JUC;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author zdkswd
 * @date 2019/10/9 13:47
 */
public class CallableTest {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        FutureTask<Integer> result = new FutureTask<>(threadDemo);
        new Thread(result).start();
        //接收线程运算的结果
        try {
            Integer i=result.get();
            System.out.println("i = " + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
class ThreadDemo implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        int sum=0;
        for (int i = 0; i < 100; i++) {
            sum+=i;
            System.out.println("sum = " + sum);
            Thread.sleep(10);
        }
        return sum;
    }
}