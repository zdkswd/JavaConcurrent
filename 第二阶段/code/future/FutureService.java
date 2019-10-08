package zdk.javaConcurrent2.future;

import java.util.function.Consumer;

/**
 * @author zdkswd
 * @date 2019/10/8 15:14
 */
public class FutureService {
    public <T> Future<T> submit(final FutureTask<T> task){
        AyncFuture<T> ayncFuture=new AyncFuture<>();
        new Thread(()->{
            T result=task.call();
            ayncFuture.done(result);
        }).start();
        return ayncFuture;
    }

    public <T> Future<T> submit(final FutureTask<T> task, Consumer<T> consumer){
        AyncFuture<T> ayncFuture=new AyncFuture<>();
        new Thread(()->{
            T result=task.call();
            ayncFuture.done(result);
            consumer.accept(result);
        }).start();
        return ayncFuture;
    }
}
