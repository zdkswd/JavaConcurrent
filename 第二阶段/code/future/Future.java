package zdk.javaConcurrent2.future;

/**
 * @author zdkswd
 * @date 2019/10/8 15:04
 */
public interface Future<T> {
    T get() throws InterruptedException;
}
