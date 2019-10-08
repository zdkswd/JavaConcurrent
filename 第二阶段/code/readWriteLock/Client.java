package zdk.javaConcurrent2.readWriteLock;

/**
 * @author zdkswd
 * @date 2019/9/30 21:43
 */
public class Client {
    public static void main(String[] args) {
        final SharedData sharedData=new SharedData(10);
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();

        new WriteWorker(sharedData,"sdasdad").start();
        new WriteWorker(sharedData,"dasdasd").start();
    }
}
