package zdk.javaConcurrent2.guardedSuspension;

import java.util.Random;

/**
 * @author zdkswd
 * @date 2019/10/8 20:46
 */
public class ServerThread extends Thread{
    private final RequestQueue queue;

    private final Random random;

    private volatile boolean flag=true;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        random=new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (flag){
            Request request = queue.getRequest();
            if (request == null) {
                System.out.println("receive empty request");
                continue;
            }
            System.out.println("request.getValue = " + request.getValue());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void close(){
        flag=false;
        this.interrupt();
    }
}
