package zdk.javaConcurrent2.guardedSuspension;

/**
 * @author zdkswd
 * @date 2019/10/8 21:17
 */
public class Client {
    public static void main(String[] args) {
        final RequestQueue queue = new RequestQueue();
        new ClientThread(queue,"alex").start();
        new ServerThread(queue).start();

    }
}
