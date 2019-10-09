package zdk.javaConcurrent;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * 作者：zdk
 * 描述：3.4 线程异常捕获
 * 时间: 2019/9/10 13:43
*/
public class ThreadException {
    public static void main(String[] args) {
        Thread t=new Thread(()->{
           int result=1/0;
        });
        t.setUncaughtExceptionHandler((thread,e)->{
            System.out.println(e);
            System.out.println(thread);
        });
        t.start();
        /**
         * 作者：zdk
         * 描述：得到当前线程函数堆栈信息
         * 时间: 2019/9/10 13:48
        */
        Arrays.asList(Thread.currentThread().getStackTrace()).stream()
                .filter(e->!e.isNativeMethod())
                .forEach(e-> Optional.of(e.getClassName()+" "+e.getMethodName()+" "+e.getLineNumber())
                .ifPresent(System.out::println)
                );
    }
}
