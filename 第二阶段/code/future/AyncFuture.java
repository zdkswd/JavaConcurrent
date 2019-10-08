package zdk.javaConcurrent2.future;

/**
 * @author zdkswd
 * @date 2019/10/8 15:26
 */
public class AyncFuture<T> implements Future<T> {
    private volatile boolean done = false;

    private T result;

    public void done(T result) {
        synchronized (this) {
            this.result = result;
            this.done = true;
            this.notifyAll();
        }
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!done) {
                this.wait();
            }
        }
        return result;
    }
}
